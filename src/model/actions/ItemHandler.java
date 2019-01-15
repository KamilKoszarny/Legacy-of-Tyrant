package model.actions;

import model.Battle;
import model.IsoBattleLoop;
import model.character.Character;
import model.character.StatsCalculator;
import model.items.Item;
import model.items.ItemsCalculator;
import model.items.armor.Shield;
import model.items.weapon.Weapon;
import model.map.MapPiece;
import model.map.mapObjects.ItemMapObject;
import viewIso.map.MapDrawCalculator;
import viewIso.mapObjects.MapObjectDrawer;
import viewIso.panel.CharDescriptor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemHandler {

    public static final int INVENTORY_X = 5, INVENTORY_Y = 5;
    public static final int ITEM_SLOT_SIZE = 30;
    private static Item heldItem = null;
    private static Point heldPoint;

    public static void moveItem(Character character, int[] clickedInventorySlot, Point clickedItemPoint) {
        if (heldItem == null) {
            catchItem(character, clickedInventorySlot);
        } else {
            putItem(character, clickedInventorySlot);
        }

        StatsCalculator.calcStats(character);
        setHeldPoint(clickedItemPoint);
    }

    private static void putItem(Character character, int[] clickedInventorySlot) {
        if (equipmentClicked(clickedInventorySlot)) {
            Item underItem = character.getItems().getEquipmentPart(clickedInventorySlot[1]);
            if (canBeDressed(underItem)) {
                character.getItems().setEquipmentPart(heldItem, clickedInventorySlot[1], true);
                if (!underItem.getName().equals("NOTHING"))
                    heldItem = underItem;
                else
                    heldItem = null;
            }
        } else if (inventoryClicked(clickedInventorySlot) && isSpaceInInventory(character, heldItem, clickedInventorySlot)){
            character.getItems().getInventory().put(heldItem, clickedInventorySlot);
            heldItem = null;
            CharDescriptor.refreshInventory(Battle.getChosenCharacter());
        } else {

        }
    }

    private static void catchItem(Character character, int[] clickedInventorySlot) {
        // equipment
        if (clickedInventorySlot[0] == -10) {
            heldItem = character.getItems().getEquipmentPart(clickedInventorySlot[1]);
            if (heldItem.getName().equals("NOTHING") || heldItem.getName().equals("BLOCKED")) {
                heldItem = null;
                return;
            }
            character.getItems().setEquipmentPart(ItemsCalculator.getNothingItemByNo(clickedInventorySlot[1]), clickedInventorySlot[1], true);
        }
        //inventory
        else {
            heldItem = itemByInventorySlot(character, clickedInventorySlot);
            if (heldItem == null)
                return;
            Point itemClickPoint = new Point(
                    (int)(ITEM_SLOT_SIZE * (clickedInventorySlot[0] - character.getItems().getInventory().get(heldItem)[0] + .5)),
                    (int)(ITEM_SLOT_SIZE * (clickedInventorySlot[1] - character.getItems().getInventory().get(heldItem)[1] + .5)));
            IsoBattleLoop.setClickedItemPoint(itemClickPoint);
            character.getItems().getInventory().keySet().remove(heldItem);
            CharDescriptor.refreshInventory(Battle.getChosenCharacter());
        }
    }

    public static void giveItem(Character giver, Character taker, Item item) {
        if (item instanceof Weapon) {
            if (taker.getItems().getWeapon().equals(Weapon.NOTHING) &&
                    (((Weapon) item).getHands() == 1 || taker.getItems().getShield().equals(Shield.NOTHING))) {
                taker.getItems().setWeapon((Weapon) item, true);
                heldItem = null;
            } else if (taker.getItems().getSpareWeapon().equals(Weapon.NOTHING) &&
                    (((Weapon) item).getHands() == 1 || taker.getItems().getSpareShield().equals(Shield.NOTHING))) {
                taker.getItems().setSpareWeapon((Weapon) item, true);
                heldItem = null;
            } else {
                int[] inventorySlot = freeSpaceInInventory(taker, item);
                if (inventorySlot != null) {
                    taker.getItems().getInventory().put(item, inventorySlot);
                    heldItem = null;
                }
            }
        }
        CharDescriptor.refreshInventory(giver);
        StatsCalculator.calcStats(taker);
    }

    public static void dropItem(Item item, Point mapPoint) {
        ItemMapObject itemMapObject = new ItemMapObject(item);
        MapPiece mapPiece = Battle.getMap().getPoints().get(mapPoint);
        mapPiece.setObject(itemMapObject);
        MapObjectDrawer.refreshSpriteMap(mapPoint);
        heldItem = null;
    }

    private static boolean equipmentClicked(int[] clickedInventorySlot) {
        return clickedInventorySlot != null && clickedInventorySlot[0] == -10;
    }

    private static boolean canBeDressed(Item underItem) {
        return heldItem.getClass().equals(underItem.getClass()) && !underItem.equals(Shield.BLOCKED);
    }

    private static boolean inventoryClicked(int[] clickedInventorySlot) {
        return clickedInventorySlot != null && clickedInventorySlot[0] >= 0;
    }

    private static int[] freeSpaceInInventory(Character character, Item itemToPut) {
        for (int x = 0; x < INVENTORY_X; x++) {
            for (int y = 0; y < INVENTORY_Y; y++) {
                int[] slot = {x, y};
                if (isSpaceInInventory(character, itemToPut, slot))
                    return slot;
            }
        }
        return null;
    }

    private static boolean isSpaceInInventory(Character character, Item itemToPut, int[] clickedInventorySlot) {
        List<int[]> itemOccupiedSlots = itemOccupiedSlots(character, itemToPut, clickedInventorySlot);
        List<int[]> allOccupiedSlots = allOccupiedSlots(character);
        for (int[] slot: itemOccupiedSlots) {
            if (slot[0] < 0 || slot[1] < 0 || slot[0] >= INVENTORY_X || slot[1] >= INVENTORY_Y)
                return false;
            for (int[] invSlot: allOccupiedSlots) {
                if (invSlot[0] == slot[0] && invSlot[1] == slot[1])
                    return false;
            }
        }
        return true;
    }

    public static int[] inventorySlotByItemClickPoint(Item item, Character character, Point point){
        int[] firstSlot = character.getItems().getInventory().get(item);
        int[] slot = new int[2];

        slot[0] = firstSlot[0] + point.x / ITEM_SLOT_SIZE;
        slot[1] = firstSlot[1] + point.y / ITEM_SLOT_SIZE;
        return slot;
    }

    private static Item itemByInventorySlot(Character character, int[] inventorySlot) {
        for (Item item: character.getItems().getInventory().keySet()) {
            List<int[]> itemOccupiedSlots = itemOccupiedSlots(character, item, null);
            for (int[] itemSlot: itemOccupiedSlots) {
                if (itemSlot[0] == inventorySlot[0] && itemSlot[1] == inventorySlot[1])
                    return item;
            }
        }
        return null;
    }

    private static List<int[]> allOccupiedSlots(Character character) {
        List<int[]> allOccupiedSlots = new ArrayList<>();
        for (Item item: character.getItems().getInventory().keySet()) {
            allOccupiedSlots.addAll(itemOccupiedSlots(character, item, null));
        }
        return allOccupiedSlots;
    }

    private static List<int[]> itemOccupiedSlots(Character character, Item item, int[] clickedInventorySlot) {
        List<int[]> occupiedSlots = new ArrayList<>();
        int[] slotZero;
        if (clickedInventorySlot == null)
            slotZero = character.getItems().getInventory().get(item);
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
