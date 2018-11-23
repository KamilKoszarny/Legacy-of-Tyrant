package model.actions;

import model.character.Character;
import model.character.StatsCalculator;
import model.items.Item;
import model.items.ItemsCalculator;
import model.items.armor.Shield;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemHandler {

    public static final int INVENTORY_X = 5, INVENTORY_Y = 5;
    public static final int ITEM_SLOT_SIZE = 30;
    private static Item caughtItem = null;

    public static Item moveItem(Character character, int[] clickedInventorySlot, Point clickedItemPoint, boolean itemCatch) {
        if (itemCatch) {
            if (catchItem(character, clickedInventorySlot) == null) return null;
        } else {
            if (equipmentClicked(clickedInventorySlot)) {
                Item underItem = character.getEquipmentPart(clickedInventorySlot[1]);
                if (canBeDressed(underItem)) {
                    character.setEquipmentPart(caughtItem, clickedInventorySlot[1]);
                    if (!underItem.getName().equals("NOTHING"))
                        caughtItem = underItem;
                    else
                        caughtItem = null;
                }
            } else if (inventoryClicked(clickedInventorySlot) && isSpaceInInventory(character, caughtItem, clickedInventorySlot)){
                character.getInventory().put(caughtItem, clickedInventorySlot);
                caughtItem = null;
            } else {
                //drop
            }
        }

        StatsCalculator.calcCharSA(character);
        return caughtItem;
    }

    private static Item catchItem(Character character, int[] clickedInventorySlot) {
        caughtItem = character.getEquipmentPart(clickedInventorySlot[1]);
        if (caughtItem.getName().equals("NOTHING") || caughtItem.getName().equals("BLOCKED")) {
            caughtItem = null;
            return caughtItem;
        }
        character.setEquipmentPart(ItemsCalculator.getNothingItemByNo(clickedInventorySlot[1]), clickedInventorySlot[1]);
        return caughtItem;
    }

    private static boolean equipmentClicked(int[] clickedInventorySlot) {
        if (clickedInventorySlot == null)
            return false;
        return clickedInventorySlot[0] == -1;
    }

    private static boolean canBeDressed(Item underItem) {
        return caughtItem.getClass().equals(underItem.getClass()) && !underItem.equals(Shield.BLOCKED);
    }

    private static boolean inventoryClicked(int[] clickedInventorySlot) {
        if (clickedInventorySlot == null)
            return false;
        return clickedInventorySlot[0] >= -1;
    }

    private static boolean isSpaceInInventory(Character character, Item itemToPut, int[] clickedInventorySlot) {
        List<int[]> itemOccupiedSlots = itemOccupiedSlots(character, itemToPut, clickedInventorySlot);
        List<int[]> allOccupiedSlots = allOccupiedSlots(character);
        for (int[] slot: itemOccupiedSlots) {
            if (slot[0] >= INVENTORY_X || slot[1] >= INVENTORY_Y)
                return false;
            for (int[] invSlot: allOccupiedSlots) {
                if (invSlot[0] == slot[0] && invSlot[1] == slot[1])
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<int[]> a = Arrays.asList(new int[]{0, 0});
        List<int[]> b = Arrays.asList(new int[]{0, 0});
        for (int[] slot: a){
            for (int[] bslot: b){

                System.out.println(bslot[0] == slot[0] && bslot[1] == slot[1]);
            }
        }
    }

    private static List<int[]> allOccupiedSlots(Character character) {
        List<int[]> allOccupiedSlots = new ArrayList<>();
        for (Item item: character.getInventory().keySet()) {
            allOccupiedSlots.addAll(itemOccupiedSlots(character, item, null));
        }
        return allOccupiedSlots;
    }

    private static List<int[]> itemOccupiedSlots(Character character, Item item, int[] clickedInventorySlot) {
        List<int[]> occupiedSlots = new ArrayList<>();
        int[] slotZero;
        if (clickedInventorySlot == null)
            slotZero = character.getInventory().get(item);
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
}
