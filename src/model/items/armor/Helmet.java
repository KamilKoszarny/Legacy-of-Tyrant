package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemsLoader;

public enum Helmet implements Armor{

    NOTHING(0, 0),
    LEATHER_HOOD(1, 5),
    CASQUE(2, 15),
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
    private String name;

    Helmet(int armor, int durability) {
        this.armor = armor;
        this.durability = durability;

        image = ItemsLoader.loadItemImage("/items/helmets/" + this.name() + ".png");
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
}
