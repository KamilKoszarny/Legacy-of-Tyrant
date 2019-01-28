package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Gloves implements Armor, ItemWithSprite{

    NOTHING(0, 0),
    RAG_GLOVES(0, 3, "cloth_gloves"),
    LEATHER_GLOVES(1, 6, "leather_gloves"),
    CHAIN_GLOVES(2, 9),
    STEEL_GLOVES(4, 16),
    PLATE_GLOVES(3, 13);

    private int armor, durability;
    private Image image;
    private String name, spriteName;

    Gloves(int armor, int durability) {
        this.armor = armor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/gloves/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
    }

    Gloves(int armor, int durability, String spriteName) {
        this.armor = armor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/gloves/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
        this.spriteName = spriteName + ".png";
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

    public String getSpriteName() {
        return spriteName;
    }
}
