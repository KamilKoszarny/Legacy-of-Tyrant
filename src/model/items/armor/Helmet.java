package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Helmet implements Armor, ItemWithSprite{

    NOTHING(0, 0),
    LEATHER_HOOD(1, 5, "leather_hood"),
    CASQUE(2, 15, "plate_helm"),
    HELMET(3, 12),
    CLOSED_HELMET(4, 16),
    MASK(1, 10),
    SKULL_HELMET(4, 24),
    WOLF_CAP(2, 15),
    EAGLE_HELMET(3, 18),
    BARBARIAN_HELMET(3, 10),
    HORN_HELMET(4, 12),
    GLADIATOR_HELMET(4, 15),
    TOURNAMENT_HELMET(5, 20),
    CROWN(5, 30);

    private int armor, durability;
    private Image image;
    private String name, spriteName;

    Helmet(int armor, int durability) {
        this.armor = armor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/helmets/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
    }

    Helmet(int armor, int durability, String spriteName) {
        this.armor = armor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/helmets/" + this.name() + ".png");
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

    @Override
    public String getName() {
        return name;
    }

    public String getSpriteName() {
        return spriteName;
    }
}
