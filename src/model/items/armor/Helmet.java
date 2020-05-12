package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Helmet implements Armor, ItemWithSprite{

    NOTHING(            0, 0, 0, ""),
    LEATHER_HOOD(       1, 1, 5, "leather_hood"),
    CASQUE(             2, 4, 15, "plate_helm"),
    HELMET(             3, 5, 12, "chain_coif"),
    CLOSED_HELMET(      4, 6, 16, "chain_coif"),
    MASK(               1, 2, 10, ""),
    SKULL_HELMET(       4, 5, 24, ""),
    WOLF_CAP(           2, 2, 15, ""),
    EAGLE_HELMET(       3, 2, 18, ""),
    BARBARIAN_HELMET(   3, 2, 10, "plate_helm"),
    HORN_HELMET(        4, 6, 12, "plate_helm"),
    GLADIATOR_HELMET(   4, 5, 15, "plate_helm"),
    TOURNAMENT_HELMET(  5, 7, 20, "plate_helm"),
    CROWN(              5, 3, 30, "");

    private int armor, weight, durability;
    private Image image;
    private String name, spriteName;

    Helmet(int armor, int weight, int durability, String spriteName) {
        this.armor = armor;
        this.weight = weight;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/helmets/" + this.name() + ".png");
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

    @Override
    public String getName() {
        return name;
    }

    public String getSpriteName() {
        return spriteName;
    }
}
