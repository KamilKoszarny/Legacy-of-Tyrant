package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Gloves implements Armor, ItemWithSprite{

    NOTHING(        0, 0, 0, ""),
    RAG_GLOVES(     0, 0, 3, "cloth_gloves"),
    LEATHER_GLOVES( 1, 1, 6, "leather_gloves"),
    CHAIN_GLOVES(   2, 2, 9, "chain_gloves"),
    STEEL_GLOVES(   4, 3, 16, "chain_gloves"),
    PLATE_GLOVES(   3, 2, 13, "plate_gauntlets");

    private int armor, weight, durability;
    private Image image;
    private String name, spriteName;

    Gloves(int armor, int weight, int durability, String spriteName) {
        this.armor = armor;
        this.weight = weight;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/gloves/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
        if (!spriteName.isEmpty()) {
            this.spriteName = spriteName + ".png";
        }
    }

    public int getArmor() {
        return armor;
    }

    @Override
    public int getWeight() {
        return weight;
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
