package model.actions;

import isoview.map.objects.MapObjectController;
import isoview.map.objects.MapObjectDrawer;
import isoview.panel.CharPanelViewer;
import isoview.panel.InventoryRectanglesViewer;
import javafx.scene.shape.Rectangle;
import model.Battle;
import model.IsoBattleLoop;
import model.actions.objects.ChestActioner;
import model.character.Character;
import model.character.StatsCalculator;
import model.items.Item;
import model.items.ItemsCalculator;
import model.items.armor.Shield;
import model.items.weapon.Weapon;
import model.map.MapPiece;
import model.map.mapObjects.ItemMapObject;

import java.awt.*;
import java.util.List;
import java.util.*;

public class ItemHandler {

    public static final int INVENTORY_SLOTS_X = 5, INVENTORY_SLOTS_Y = 5;
    public static final int ITEM_SLOT_SIZE = 30;
    private static final double ITEM_DROP_DIST = 10;

    private static Item heldItem = null;
    private static Point heldPoint;

    public static void moveItem(Character character, int[] clickedInventorySlot, Point clickedItemPoint) {
        if (heldItem == null) {
            catchItem(character, clickedInventorySlot);
            setHeldPoint(clickedItemPoint);
        } else {
            putItem(character, clickedInventorySlot, clickedItemPoint);
        }

        StatsCalculator.calcStats(character);
    }

    private static void catchItem(Character character, int[] clickedInventorySlot) {
        if (equipmentClicked(clickedInventorySlot)) {
            heldItem = character.getItems().getEquipmentPart(clickedInventorySlot[1]);
            if (heldItem.getName().equals("NOTHING") || heldItem.getName().equals("BLOCKED")) {
                heldItem = null;
                return;
            }
            character.getItems().setEquipmentPart(ItemsCalculator.getNothingItemByNo(clickedInventorySlot[1]), clickedInventorySlot[1], true);
        }
        else if (chestClicked(clickedInventorySlot)) {
            clickedInventorySlot[0] -= 100;
            heldItem = catchInventoryItem(ChestActioner.getChest().getInventory(), clickedInventorySlot,
                    ChestActioner.getInventoryRect());
        }
        else if (inventoryClicked(clickedInventorySlot)){
            heldItem = catchInventoryItem(character.getItems().getInventory(), clickedInventorySlot,
                    CharPanelViewer.getInventoryRect());
        }
    }

    private static Item catchInventoryItem(Map<Item, int[]> inventory, int[] clickedInventorySlot, Rectangle inventoryRectangle) {
        Item catchItem;
        catchItem = itemByInventorySlot(inventory, clickedInventorySlot);
        if (catchItem == null)
            return null;
        Point itemClickPoint = new Point(
                (int)(ITEM_SLOT_SIZE * (clickedInventorySlot[0] - inventory.get(catchItem)[0] + .5)),
                (int)(ITEM_SLOT_SIZE * (clickedInventorySlot[1] - inventory.get(catchItem)[1] + .5)));
        IsoBattleLoop.setClickedItemPoint(itemClickPoint);
        inventory.keySet().remove(catchItem);
        InventoryRectanglesViewer.refreshInventory(inventory, inventoryRectangle);
        return catchItem;
    }

    private static void putItem(Character character, int[] clickedInventorySlot, Point underItemPoint) {
        if (equipmentClicked(clickedInventorySlot)) {
            Item underItem = character.getItems().getEquipmentPart(clickedInventorySlot[1]);
            if (heldCanBePutHere(character, clickedInventorySlot, underItem)) {
                character.getItems().setEquipmentPart(heldItem, clickedInventorySlot[1], true);
                heldItem = underItem.getName().equals("NOTHING") ? null : underItem;
                setHeldPoint(underItemPoint);
            }
        } else if (chestClicked(clickedInventorySlot)) {
            clickedInventorySlot[0] -= 100;
            putToInventory(ChestActioner.getChest().getInventory(), clickedInventorySlot, ChestActioner.getInventoryRect(), underItemPoint);
        } else if (inventoryClicked(clickedInventorySlot)){
            putToInventory(character.getItems().getInventory(), clickedInventorySlot, CharPanelViewer.getInventoryRect(), underItemPoint);
        }
    }

    private static void putToInventory(Map<Item, int[]> inventory, int[] clickedInventorySlot, Rectangle inventoryRectangle, Point underItemPoint) {
        recalcInventorySlotByHeldPoint(heldPoint, clickedInventorySlot);
        Set<Item> underItems = underInventoryItems(inventory, heldItem, clickedInventorySlot);
        if (underItems == null || underItems.size() > 1) {
            return;
        }
        if (underItems.size() == 0) {
            inventory.put(heldItem, clickedInventorySlot);
            heldItem = null;
            InventoryRectanglesViewer.refreshInventory(inventory, inventoryRectangle);
            return;
        }
        //so: underItems.size() == 1
        inventory.put(heldItem, clickedInventorySlot);
        heldItem = underItems.iterator().next();
        inventory.remove(heldItem);
        InventoryRectanglesViewer.refreshInventory(inventory, inventoryRectangle);
        setHeldPoint(underItemPoint);
    }

    public static void tryGiveItem(Character giver, Character taker, Item item) {
        if (giver.getPosition().distance(taker.getPosition()) <= ITEM_DROP_DIST) {
            giveItem(giver, taker, item);
        }
    }

    private static boolean giveItem(Character giver, Character taker, Item item) {
        boolean given = true;
        if (item instanceof Weapon) {
            if (taker.getItems().getWeapon().equals(Weapon.NOTHING) &&
                    (((Weapon) item).getHands() == 1 || taker.getItems().getShield().equals(Shield.NOTHING))) {
                taker.getItems().setWeapon((Weapon) item, true);
                heldItem = null;
            } else if (taker.getItems().getSpareWeapon().equals(Weapon.NOTHING) &&
                    (((Weapon) item).getHands() == 1 || taker.getItems().getSpareShield().equals(Shield.NOTHING))) {
                taker.getItems().setSpareWeapon((Weapon) item);
                heldItem = null;
            } else
                given = putInFirstInventorySlot(taker.getItems().getInventory(), item);
        } else if (item instanceof Shield) {
            if (taker.getItems().getShield().equals(Shield.NOTHING)) {
                taker.getItems().setShield((Shield) item, true);
                heldItem = null;
            } else if (taker.getItems().getSpareShield().equals(Shield.NOTHING)) {
                taker.getItems().setSpareShield((Shield) item);
                heldItem = null;
            } else
                given = putInFirstInventorySlot(taker.getItems().getInventory(), item);
        } else
            given = putInFirstInventorySlot(taker.getItems().getInventory(), item);

        if (giver != null)
            CharPanelViewer.refreshCharInventory(giver.getItems().getInventory());
        else
            CharPanelViewer.refreshCharInventory(taker.getItems().getInventory());
        StatsCalculator.calcStats(taker);

        return given;
    }

    public static boolean putInFirstInventorySlot(Map<Item, int[]> inventory, Item item) {
        int[] inventorySlot = freeSpaceInInventory(inventory, item);
        if (inventorySlot != null) {
            inventory.put(item, inventorySlot);
            heldItem = null;
            return true;
        }
        return false;
    }

    public static void tryDropItem(Character character, Item item, Point mapPoint) {
        MapPiece mapPiece = Battle.getMap().getPoints().get(mapPoint);
        if (character.getPosition().distance(mapPoint) <= ITEM_DROP_DIST
                && mapPiece.isWalkable()
                && mapPiece.getObject() == null) {
            dropItem(item, mapPoint);
        }
    }

    private static void dropItem(Item item, Point mapPoint) {
        ItemMapObject itemMapObject = new ItemMapObject(item);
        MapPiece mapPiece = Battle.getMap().getPoints().get(mapPoint);
        mapPiece.setObject(itemMapObject);
        MapObjectDrawer.refreshSpriteMap(mapPoint);
        heldItem = null;
    }

    public static void tryPickupItem(Character character, ItemMapObject itemMapObject, Point mapPoint) {
        Item item = itemMapObject.getItem();
        if (character.getPosition().distance(mapPoint) <= ITEM_DROP_DIST && heldItem == null) {
            boolean pickedUp = giveItem(null, character, item);
            if (pickedUp) {
                MapObjectController.removeObject(itemMapObject);
            }
        }
    }

    private static boolean equipmentClicked(int[] clickedInventorySlot) {
        return clickedInventorySlot != null && clickedInventorySlot[0] == -10;
    }

    private static boolean heldCanBePutHere(Character character, int[] clickedInventorySlot, Item underItem) {
        return heldItem.getClass().equals(underItem.getClass()) && !underItem.equals(Shield.BLOCKED)
                && (clickedInventorySlot[1] != 0 || !heldItem.getClass().equals(Weapon.class) || ((Weapon) heldItem).getHands() == 1 || character.getItems().getShield().equals(Shield.NOTHING) || character.getItems().getShield().equals(Shield.BLOCKED))
                && (clickedInventorySlot[1] != 1 || !heldItem.getClass().equals(Weapon.class) || ((Weapon) heldItem).getHands() == 1 || character.getItems().getSpareShield().equals(Shield.NOTHING) || character.getItems().getSpareShield().equals(Shield.BLOCKED));
    }

    private static boolean inventoryClicked(int[] clickedInventorySlot) {
        return clickedInventorySlot != null && clickedInventorySlot[0] >= 0;
    }

    private static boolean chestClicked(int[] clickedInventorySlot) {
        return clickedInventorySlot != null && clickedInventorySlot[0] >= 100;
    }

    private static int[] freeSpaceInInventory(Map<Item, int[]> inventory, Item itemToPut) {
        for (int x = 0; x < INVENTORY_SLOTS_X; x++) {
            for (int y = 0; y < INVENTORY_SLOTS_Y; y++) {
                int[] slot = {x, y};
                if (isSpaceInInventory(inventory, itemToPut, slot))
                    return slot;
            }
        }
        return null;
    }

    private static boolean isSpaceInInventory(Map<Item, int[]> inventory, Item itemToPut, int[] clickedInventorySlot) {
        List<int[]> itemOccupiedSlots = itemOccupiedSlots(inventory, itemToPut, clickedInventorySlot);
        List<int[]> allOccupiedSlots = allOccupiedSlots(inventory);
        for (int[] slot: itemOccupiedSlots) {
            if (outOfInventory(slot))
                return false;
            for (int[] invSlot: allOccupiedSlots) {
                if (sameSlot(slot, invSlot))
                    return false;
            }
        }
        return true;
    }

    private static Set<Item> underInventoryItems(Map<Item, int[]> inventory, Item itemToPut, int[] clickedInventorySlot) {
        List<int[]> itemOccupiedSlots = itemOccupiedSlots(inventory, itemToPut, clickedInventorySlot);
        Set<Item> underItems = new HashSet<>();
        List<int[]> underItemOccupiedSlots;
        for (int[] slot: itemOccupiedSlots) {
            if (outOfInventory(slot))
                return null;
            for (Item item: inventory.keySet()) {
                underItemOccupiedSlots = itemOccupiedSlots(inventory, item, inventory.get(item));
                for (int[] invSlot : underItemOccupiedSlots) {
                    if (sameSlot(slot, invSlot))
                        underItems.add(item);
                }
            }
        }
        return underItems;
    }

    private static boolean sameSlot(int[] slot, int[] invSlot) {
        return invSlot[0] == slot[0] && invSlot[1] == slot[1];
    }

    private static boolean outOfInventory(int[] slot) {
        return slot[0] < 0 || slot[1] < 0 || slot[0] >= INVENTORY_SLOTS_X || slot[1] >= INVENTORY_SLOTS_Y;
    }

    public static int[] inventorySlotByItemClickPoint(Item item, Map<Item, int[]> inventory, Point point){
        int[] firstSlot = inventory.get(item);
        int[] slot = new int[2];

        slot[0] = firstSlot[0] + point.x / ITEM_SLOT_SIZE;
        slot[1] = firstSlot[1] + point.y / ITEM_SLOT_SIZE;
        return slot;
    }

    private static Item itemByInventorySlot(Map<Item, int[]> inventory, int[] inventorySlot) {
        for (Item item: inventory.keySet()) {
            List<int[]> itemOccupiedSlots = itemOccupiedSlots(inventory, item, null);
            for (int[] itemSlot: itemOccupiedSlots) {
                if (sameSlot(inventorySlot, itemSlot))
                    return item;
            }
        }
        return null;
    }

    private static List<int[]> allOccupiedSlots(Map<Item, int[]> inventory) {
        List<int[]> allOccupiedSlots = new ArrayList<>();
        for (Item item: inventory.keySet()) {
            allOccupiedSlots.addAll(itemOccupiedSlots(inventory, item, null));
        }
        return allOccupiedSlots;
    }

    private static List<int[]> itemOccupiedSlots(Map<Item, int[]> inventory, Item item, int[] clickedInventorySlot) {
        List<int[]> occupiedSlots = new ArrayList<>();
        int[] slotZero;
        if (clickedInventorySlot == null)
            slotZero = inventory.get(item);
        else
            slotZero = clickedInventorySlot;
        int xSlotSize = Math.round(Math.round(item.getImage().getWidth() / ITEM_SLOT_SIZE));
        int ySlotSize = Math.round(Math.round(item.getImage().getHeight() / ITEM_SLOT_SIZE));
        for (int y = 0; y < ySlotSize; y++) {
            for (int x = 0; x < xSlotSize; x++) {
                occupiedSlots.add(new int[]{slotZero[0] + x, slotZero[1] + y});
            }
        }
        return occupiedSlots;
    }

    private static void recalcInventorySlotByHeldPoint(Point shapeClickPoint, int[] inventorySlot) {
        if (inventorySlot != null && shapeClickPoint != null) {
            inventorySlot[0] -= (int)(shapeClickPoint.getX() / ItemHandler.ITEM_SLOT_SIZE);
            inventorySlot[1] -= (int)(shapeClickPoint.getY() / ItemHandler.ITEM_SLOT_SIZE);
        }
    }

    private static void setHeldPoint(Point heldPoint) {
        ItemHandler.heldPoint = heldPoint;
        if (heldItem == null) {
            ItemHandler.heldPoint = new Point(0, 0);
            IsoBattleLoop.setClickedItemPoint(new Point(0, 0));
        }
    }

    public static Item getHeldItem() {
        return heldItem;
    }

    public static Point getHeldPoint() {
        return heldPoint;
    }
}
