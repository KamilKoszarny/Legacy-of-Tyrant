package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemImagesLoader;

public enum Belt implements Armor{

    NOTHING(        0, 0, 0),
    SASH(           0, 3, 0),
    LEATHER_BELT(   1, 6, 1),
    WIDE_BELT(      1, 8, 2),
    PLATE_BELT(     2, 14, 3);

    private int armor, weight, durability;
    private Image image;
    private String name;

    Belt(int armor, int durability, int weight) {
        this.armor = armor;
        this.weight = weight;
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

    @Override
    public int getWeight() {
        return weight;
    }
}
