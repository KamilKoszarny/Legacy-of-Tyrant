package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Shield implements Armor, ItemWithSprite{

    NOTHING(            0, 0, 0, 0, ""),
    BLOCKED(            0, 0, 0, 0, ""),
    WOODEN_SHIELD(      10, 0, 3, 5, "buckler"),
    BUCKLER(            15, 0, 3, 12, "buckler"),
    TARGE(              15, 1, 4, 15, "buckler"),
    KITE_SHIELD(        20, 1, 7, 15, "shield"),
    DIAMOND_SHIELD(     20, 1, 10, 16, ""),
    RONDACHE(           20, 2, 5, 24, "iron_buckler"),
    BRONZE_SHIELD(      25, 2, 10, 25, "iron_buckler"),
    SPIKED_SHIELD(      25, 4, 9, 20, "iron_buckler"),
    BONE_SHIELD(        25, 3, 7, 25, ""),
    TOURNAMENT_SHIELD(  30, 2, 12, 32, "shield"),
    HERALD_SHIELD(      30, 3, 12, 40, "shield"),
    DRAGON_SHIELD(      35, 3, 12, 50, "shield");

    private int block, dmg, weight, durability;
    private Image image;
    private String name, spriteName;

    Shield(int block, int dmg, int weight, int durability, String spriteName) {
        this.block = block;
        this.dmg = dmg;
        this.weight = weight;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/shields/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
        if (!spriteName.isEmpty()) {
            this.spriteName = spriteName + ".png";
        }
    }

    public int getBlock() {
        return block;
    }

    public int getDmg() {
        return dmg;
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
