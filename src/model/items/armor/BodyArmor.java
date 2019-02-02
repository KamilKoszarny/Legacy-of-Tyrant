package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum BodyArmor implements Armor, ItemWithSprite{

    NOTHING(0, 0, 0, 0, 0),
    LEATHER_SHIRT(0, 1, 1, 0, 12, "mage_vest"),
    GAMBISON(0, 1, 1, 1, 8, "leather_chest_pants"),
    LEATHER_ARMOR(0, 2, 1, 0, 16, "leather_chest"),
    STUDDED_ARMOR(0, 3, 0, 1, 19, "leather_chest"),
    CHAIN_SHIRT(0, 4, 3, 0, 23, "chain_cuirass"),
    SCALE_SHIRT(0, 5, 2, 0, 27, "chain_cuirass"),
    HAUBERK(0, 4, 3, 2, 25, "chain_cuirass_greaves"),
    LIGHT_PLATE_ARMOR(0, 5, 2, 2, 26, "plate_cuirass"),
    BREASTPLATE(0, 7, 0, 0, 30, "plate_cuirass"),
    TOURNAMENT_ARMOR(0, 5, 4, 2, 35, "chain_cuirass_greaves"),
    GUARDIAN_ARMOR(1, 6, 4, 3, 40, "chain_cuirass_greaves"),
    FULL_PLATE_ARMOR(0, 7, 4, 4, 40, "chain_cuirass_greaves"),
    FORGED_ARMOR(2, 8, 4, 1, 50, "chain_cuirass");

    private int headArmor, bodyArmor, armsArmor, legsArmor, durability;
    private Image image;
    private String name, spriteName;

    BodyArmor(int headArmor, int bodyArmor, int armsArmor, int legsArmor, int durability) {
        this.headArmor = headArmor;
        this.bodyArmor = bodyArmor;
        this.armsArmor = armsArmor;
        this.legsArmor = legsArmor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/armors/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
    }

    BodyArmor(int headArmor, int bodyArmor, int armsArmor, int legsArmor, int durability, String spriteName) {
        this.headArmor = headArmor;
        this.bodyArmor = bodyArmor;
        this.armsArmor = armsArmor;
        this.legsArmor = legsArmor;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/armors/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
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
