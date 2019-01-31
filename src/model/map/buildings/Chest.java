package model.map.buildings;

import model.items.Item;

import java.util.HashMap;
import java.util.Map;

public class Chest extends Furniture {

    private Map<Item, int[]> inventory = new HashMap<>();

    public Chest() {
        super(FurnitureType.CHEST);
        ChestFiller.fillChest(this);
    }

    public Map<Item, int[]> getInventory() {
        return inventory;
    }
}
