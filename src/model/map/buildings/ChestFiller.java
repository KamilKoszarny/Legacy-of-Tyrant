package model.map.buildings;

import model.actions.ItemHandler;
import model.items.Item;
import model.items.ItemsGenerator;
import model.map.Map;

import java.util.Random;

public class ChestFiller {

    public static void fillChests(Map map) {

    }

    public static void fillChest(Chest chest) {
        int count = new Random().nextInt(5);
        for (int i = 0; i < count; i++) {
            Item item = ItemsGenerator.generateItem(1);
            if (item != null)
                ItemHandler.putInFirstInventorySlot(chest.getInventory(), item);
        }
    }
}
