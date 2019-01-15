package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemsLoader;

public enum BodyArmor implements Armor, ItemWithSprite{

    NOTHING(0, 0, 0, 0, 0),
    LEATHER_SHIRT(0, 1, 1, 0, 12),
    GAMBISON(0, 1, 1, 1, 8, "leather_chest_pants"),
    LEATHER_ARMOR(0, 2, 1, 0, 16, "leather_chest"),
    STUDDED_ARMOR(0, 3, 0, 1, 19),
    CHAIN_SHIRT(0, 4, 3, 0, 23),
    SCALE_SHIRT(0, 5, 2, 0, 27),
    HAUBERK(0, 4, 3, 2, 25),
    LIGHT_PLATE_ARMOR(0, 5, 2, 2, 26),
    BREASTPLATE(0, 7, 0, 0, 30),
    TOURNAMENT_ARMOR(0, 5, 4, 2, 35),
    GUARDIAN_ARMOR(1, 6, 4, 3, 40),
    FULL_PLATE_ARMOR(0, 7, 4, 4, 40),
    FORGED_ARMOR(2, 8, 4, 1, 50);

    private int headArmor, bodyArmor, armsArmor, legsArmor, durability;
    private Image image;
    private String name, spriteName;

    BodyArmor(int headArmor, int bodyArmor, int armsArmor, int legsArmor, int durability) {
        this.headArmor = headArmor;
        this.bodyArmor = bodyArmor;
        this.armsArmor = armsArmor;
        this.legsArmor = legsArmor;
        this.durability = durability;

        image = ItemsLoader.loadItemImage("/items/armors/" + this.name() + ".png");
        name = ItemsLoader.setItemName(this.name());
    }

    BodyArmor(int headArmor, int bodyArmor, int armsArmor, int legsArmor, int durability, String spriteName) {
        this.headArmor = headArmor;
        this.bodyArmor = bodyArmor;
        this.armsArmor = armsArmor;
        this.legsArmor = legsArmor;
        this.durability = durability;

        image = ItemsLoader.loadItemImage("/items/armors/" + this.name() + ".png");
        name = ItemsLoader.setItemName(this.name());
        this.spriteName = spriteName + ".png";
    }

    public int getHeadArmor() {
        return headArmor;
    }

    public int getBodyArmor() {
        return bodyArmor;
    }

    public int getArmsArmor() {
        return armsArmor;
    }

    public int getLegsArmor() {
        return legsArmor;
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
