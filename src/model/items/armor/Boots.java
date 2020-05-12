package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Boots implements Armor, ItemWithSprite{

    NOTHING(        0, 0, 0, ""),
    RAG_BOOTS(      0, 1, 3, "cloth_sandals"),
    LEATHER_BOOTS(  1, 2, 6, "leather_boots"),
    CHAIN_BOOTS(    2, 4, 9, "chain_boots"),
    STEEL_BOOTS(    4, 6, 16, "plate_boots"),
    PLATE_BOOTS(    3, 5, 13, "plate_boots");

    private int armor, weight, durability;
    private Image image;
    private String name, spriteName;

    Boots(int armor, int weight, int durability, String spriteName) {
        this.armor = armor;
        this.weight = weight;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/boots/" + this.name() + ".png");
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
