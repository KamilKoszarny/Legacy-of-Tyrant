package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemImagesLoader;

public enum Belt implements Armor{

    NOTHING(0, 0),
    SASH(0, 3),
    LEATHER_BELT(1, 6),
    WIDE_BELT(1, 8),
    PLATE_BELT(2, 14);

    private int armor, durability;
    private Image image;
    private String name;

    Belt(int armor, int durability) {
        this.armor = armor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/belts/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
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
