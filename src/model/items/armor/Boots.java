package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemsLoader;

public enum Boots implements Armor{

    NOTHING(0, 0),
    RAG_BOOTS(0, 3),
    LEATHER_BOOTS(1, 6),
    CHAIN_BOOTS(2, 9),
    STEEL_BOOTS(4, 16),
    PLATE_BOOTS(3, 13);

    private int armor, durability;
    private Image image;
    private String name;

    Boots(int armor, int durability) {
        this.armor = armor;
        this.durability = durability;

        image = ItemsLoader.loadItemImage("/items/boots/" + this.name() + ".png");
        name = ItemsLoader.setItemName(this.name());
    }

    public int getArmor() {
        return armor;
    }

    public int getDurability() {
        return durability;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
