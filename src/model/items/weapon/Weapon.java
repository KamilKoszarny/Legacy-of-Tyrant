package model.items.weapon;

import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemImagesLoader;

public enum Weapon implements ItemWithSprite{
    NOTHING(            1, 1, .6, .4,       1, 0, 0, 10000),

    //swords
    SHORT_SWORD(        2, 4, 1, 1,         1, 0, 15, 12, "shortsword"),
    SABRE(              2, 5, .8, 1.2,      1, 10, 35, 8, "shortsword"),
    CHOPPER(            3, 7, 1.2, 1,       1, -10, 15, 14, "shortsword"),
    GLASS_SWORD(        4, 8, 1, 1.3,       1, 0, 20, 4, "longsword"),
    BROAD_SWORD(        3, 8, 1.1, 1,       1, 0, 25, 18, "longsword"),
    LONG_SWORD(         4, 7, 1, 1.5,       1, 0, 35, 16, "longsword"),
    TWO_HAND_SWORD(     5, 11, 1.9, 2,      2, 0, 30, 18, "longsword"),
    CLAYMORE(           5, 8, 1.3, 1.8,     2, 0, 55, 16, "longsword"),
    HALF_HAND_SWORD_1H( 4, 10, 2.2, 2.2,    1, 0, 30, 19, "greatsword"),
    HALF_HAND_SWORD_2H( 5, 12, 1.7, 1.8,    2, 0, 30, 19, "greatsword"),
    SABER(              3, 16, 2.1, 2.5,    2, 0, 25, 15, "greatsword"),
    GRAND_SWORD(        9, 15, 2.2, 2,      2, 0, 35, 22, "greatsword"),

    //axes
    ADZE(               1, 4, 1.1, 0.7,     1, 0, 15, 9, "hand_axe"),
    AXE_1H(             3, 6, 1.3, 1.1,     1, 0, 15, 15, "infantry_axe"),
    AXE_2H(             5, 8, 1.1, 0.9,     2, 0, 15, 15, "infantry_axe"),
    TWO_SIDE_AXE(       4, 6, 1, 1,         2, 0, 10, 15, "battle_axe"),
    PICK(               1, 4, 2, .9,        1, -10, 10, 12, "war_hammer"),
    BEARD_AXE(          6, 7, 1.2, 1,       1, 0, 25, 22, "hand_axe"),
    BIG_ADZE(           2, 10, 2.3, 1.9,    2, -10, 10, 14, "hand_axe"),
    BATTLE_AXE(         6, 12, 1.4, 1.4,    2, 0, 25, 19, "battle_axe"),
    GRAND_AXE(          5, 19, 2.2, 1.9,    2, 0, 35, 24, "battle_axe"),
    DWARF_AXE(          4, 12, 1.3, 1,      2, 0, 30, 28, "infantry_axe"),

    //maces
    CUDGEL(             1, 2, .8, .9,       1, 0, 20, 15, "club"),
    HAMMER(             2, 6, 1.8, .6,      1, 0, 10, 20, "maul"),
    SPIKE_CLUB(         1, 5, 1, 1.1,       1, 0, 10, 10, "reinforced_club"),
    MACE(               3, 5, .9, .9,       1, 0, 15, 16, "mace"),
    SPIKE_STAR(         4, 6, 1, 1,         1, 0, 10, 13, "mace"),
    SCEPTER(            4, 6, 1.1, 1.2,     1, 0, 25, 12, "rod"),
    KURBASH(            3, 8, 1, 1.6,       1, 0, 15, 14, "mace"),
    COMBAT_STICK(       3, 6, .9, 2,        2, 0, 30, 18, "staff"),
    COMBAT_SCEPTER(     4, 8, 1.1, 1.4,     2, 20, 25, 17, "mace"),
    RAM(                10, 21, 3, 2.8,     2, 0, 25, 24, "smith_hammer"),

    //short
    KNIFE(              2, 3, .6, .5,       1, 0, 0, 7, "dagger"),
    DAGGER(             2, 5, .6, .6,       1, 0, 0, 9, "dagger"),
    CEREMONY_DAGGER(    2, 4, .8, .8,       1, 0, 0, 13, "dagger"),
    SPIKE(              1, 5, 1, .8,        1, 0, 0, 10, "dagger"),
    KNUCKLE_DUSTER(     2, 3, .6, .4,       1, 0, 0, 17),
    HAND_BLADE(         4, 6, 1, 1,         1, 0, 0, 13, "shortsword"),
    TRIBLADE(           3, 8, 1, 1,         1, 0, 0, 12, "shortsword"),
    CLAWS(              2, 11, 1, 1,        1, 0, 0, 11, "shortsword"),

    //long TODO: sprites
    SPEAR(              4, 8, 1.3, 3,       2, 0, 20, 10, "staff"),
    TRIDENT(            7, 9, 1.4, 3,       2, 0, 25, 15, "staff"),
    PIQUE(              9, 13, 1.9, 4,      2, 0, 15, 12, "staff"),
    HARPOON(            5, 10, 1.3, 3,      2, 0, 20, 9, "staff"),
    SCYTHE(             2, 8, 2, 2.5,         2, 0, 10, 7, "staff"),

    //throwing TODO: sprites
    THROWING_KNIFE_THR( 2, 4, 1.1, 10,      1, 0, 0, 10),
    THROWING_KNIFE_DIR( 2, 2, .6, .5,       1, 0, 0, 1),
    THROWING_AXE_THR(   1, 5, 1.2, 10,      1, 0, 0, 10),
    THROWING_AXE_DIR(   2, 2, .7, .7,       1, 0, 0, 1),
    JAVELIN_THR(        5, 8, 1.9, 15,      1, 0, 0, 6),
    JAVELIN_DIR(        1, 4, 1, 1.2,       1, 0, 0, 1),

    //range TODO: sprites
    SHORT_BOW(          2, 5, 1.8, 20,      2, 0, 0, 9, "shortbow"),
    HUNTER_BOW(         3, 6, 1.7, 20,      2, 5, 0, 13, "longbow"),
    LONG_BOW(           3, 8, 2, 25,        2, 5, 0, 10, "greatbow"),
    LIGHT_CROSSBOW(     4, 7, 2.8, 15,      2, 0, 0, 15),
    CROSSBOW(           6, 9, 3, 15,        2, 0, 0, 15),

    //mages
    WAND(               1, 1, .8, .5,       1, 0, 0, 5, "wand"),
    KRIN_WAND(          4, 4, .9, .6,       1, 0, 0, 9, "wand"),
    FEATHERY_WAND(      2, 2, .7, .5,       1, 0, 0, 6, "wand"),
    BONE_WAND(          2, 2, .8, .5,       1, 0, 0, 12, "wand"),
    SPHERE(             1, 1, .8, .6,       1, 0, 10, 4, "rod"),
    HOT_SPHERE(         2, 2, .5, .6,       1, 0, 15, 6, "rod"),
    HOLY_SPHERE(        3, 3, .7, .7,       1, 0, 15, 9, "rod"),
    CLOUD_SPHERE(       2, 2, .5, .6,       1, 0, 15, 6, "rod"),
    COLD_SPHERE(        2, 2, 1, 1.1,       1, 0, 15, 7, "rod"),
    STAFF(              3, 3, 1.2, 1.4,     2, 0, 20, 9, "staff"),
    KNOTTY_STAFF(       4, 4, 1.3, 1.4,     2, 0, 35, 16, "staff"),
    WINGED_STAFF(       4, 4, 1.1, 2.2,     2, 0, 30, 14, "greatstaff"),
    EARTH_STAFF(        3, 3, 1.3, 1.8,     2, 0, 25, 10, "greatstaff"),
    ;

    private static final double DAMAGE_MULTIPLIER = 1.5;

    private double dmgMin, dmgMax, attackDuration, range;
    private int hands, accuracy, parry, durability;
    private Image image;
    private String name, spriteName;

    Weapon(double dmgMin, double dmgMax, double attackDuration, double range, int hands, int accuracy, int parry, int durability) {
        this.dmgMin = dmgMin * DAMAGE_MULTIPLIER;
        this.dmgMax = dmgMax * DAMAGE_MULTIPLIER;
        this.attackDuration = attackDuration;
        this.range = range;
        this.hands = hands;
        this.accuracy = accuracy;
        this.parry = parry;
        this.durability = durability;

        image = ItemImagesLoader.loadItemImage("/items/weapons/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
    }

    Weapon(double dmgMin, double dmgMax, double attackDuration, double range, int hands, int accuracy, int parry, int durability, String spriteName) {
        this(dmgMin, dmgMax, attackDuration, range, hands, accuracy, parry, durability);

        this.spriteName = spriteName + ".png";
    }

    public double getDmgMin() {
        return dmgMin;
    }

    public double getDmgMax() {
        return dmgMax;
    }

    public double getAttackDuration() {
        return attackDuration;
    }

    public double getRange() {
        return range;
    }

    public int getHands() {
        return hands;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getParry() {
        return parry;
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
