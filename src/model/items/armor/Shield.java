package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Shield implements Armor, ItemWithSprite{

    NOTHING(0, 0, 0),
    BLOCKED(0, 0, 0),
    WOODEN_SHIELD(10, 0, 5, "buckler"),
    BUCKLER(15, 0, 12, "buckler"),
    TARGE(15, 1, 15, "buckler"),
    KITE_SHIELD(20, 1, 15),
    DIAMOND_SHIELD(20, 1, 16),
    RONDACHE(20, 2, 24),
    BRONZE_SHIELD(25, 2, 25),
    SPIKED_SHIELD(25, 4, 20),
    BONE_SHIELD(25, 3, 25),
    TOURNAMENT_SHIELD(30, 2, 32),
    HERALD_SHIELD(30, 3, 40),
    DRAGON_SHIELD(35, 3, 50);

    private int block, dmg, durability;
    private Image image;
    private String name, spriteName;

    Shield(int block, int dmg, int durability) {
        this.block = block;
        this.dmg = dmg;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/shields/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
    }

    Shield(int block, int dmg, int durability, String spriteName) {
        this.block = block;
        this.dmg = dmg;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/shields/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
        this.spriteName = spriteName + ".png";
    }

    public int getBlock() {
        return block;
    }

    public int getDmg() {
        return dmg;
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
