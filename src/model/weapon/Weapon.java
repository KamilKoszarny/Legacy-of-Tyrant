package model.weapon;

public enum Weapon {
    NOTHING(            1, 1, .6, .4,       1, 0, 0, 10000),

    //swords
    SHORT_SWORD(        2, 4, 1, 1,         1, 0, 15, 12),
    SABRE(              2, 5, .8, 1.2,      1, 10, 35, 8),
    CHOPPER(            3, 7, 1.2, 1,       1, -10, 15, 14),
    GLASS_SWORD(        4, 8, 1, 1.3,       1, 0, 20, 4),
    BROAD_SWORD(        3, 8, 1.1, 1,       1, 0, 25, 18),
    LONG_SWORD(         4, 7, 1, 1.5,       1, 0, 35, 16),
    TWO_HAND_SWORD(     5, 11, 1.9, 2,      2, 0, 30, 18),
    CLAYMORE(           5, 8, 1.3, 1.8,     2, 0, 55, 16),
    HALF_HAND_SWORD_1H( 4, 10, 2.2, 2.2,    1, 0, 30, 19),
    HALF_HAND_SWORD_2H( 5, 12, 1.7, 1.8,    2, 0, 30, 19),
    SABER(              3, 16, 2.1, 2.5,    2, 0, 25, 15),
    GRAND_SWORD(        9, 15, 2.2, 2,      2, 0, 35, 22),

    //axes
    ADZE(               1, 4, 1.1, 0.7,     1, 0, 15, 9),
    AXE_1H(             3, 6, 1.3, 1.1,     1, 0, 15, 15),
    AXE_2H(             5, 8, 1.1, 0.9,     2, 0, 15, 15),
    TWO_SIDE_AXE(       4, 6, 1, 1,         2, 0, 10, 15),
    PICK(               1, 4, 2, .9,        1, -10, 10, 12),
    BEARD_AXE(          6, 7, 1.2, 1,       1, 0, 25, 22),
    BIG_ADZE(           2, 10, 2.3, 1.9,    2, -10, 10, 14),
    BATTLE_AXE(         6, 12, 1.4, 1.4,    2, 0, 25, 19),
    GRAND_AXE(          5, 19, 2.2, 1.9,    2, 0, 35, 24),
    DWARF_AXE(          4, 12, 1.3, 1,      2, 0, 30, 28),

    //maces
    CUDGEL(             1, 2, .8, .9,       1, 0, 20, 15),
    HAMMER(             2, 6, 1.8, .6,      1, 0, 10, 20),
    SPIKE_CLUB(         1, 5, 1, 1.1,       1, 0, 10, 10),
    MACE(               3, 5, .9, .9,       1, 0, 15, 16),
    SPIKE_STAR(         4, 6, 1, 1,         1, 0, 10, 13),
    SCEPTER(            4, 6, 1.1, 1.2,     1, 0, 25, 12),
    KURBASH(            3, 8, 1, 1.6,       1, 0, 15, 14),
    COMBAT_STICK(       3, 6, .9, 2,        2, 0, 30, 18),
    COMBAT_SCEPTER(     4, 8, 1.1, 1.4,     2, 20, 25, 17),
    RAM(                10, 21, 3, 2.8,     2, 0, 25, 24),

    //short
    KNIFE(              2, 3, .6, .5,       1, 0, 0, 7),
    DAGGER(             2, 5, .6, .6,       1, 0, 0, 9),
    KNUCKLE_DUSTER(     2, 3, .6, .4,       1, 0, 0, 17),

    //long
    SPEAR(              4, 8, 1.3, 3,       2, 0, 20, 10),
    TRIDENT(            7, 9, 1.4, 3,       2, 0, 25, 15),
    PIQUE(              9, 13, 1.9, 4,      2, 0, 15, 12),
    HARPOON(            5, 10, 1.3, 3,      2, 0, 20, 9),
    SCYTHE(             2, 8, 2, 2.5,         2, 0, 10, 7),

    //throwing
    THROWING_KNIFE_THR( 2, 4, 1.1, 10,      1, 0, 0, 10),
    THROWING_KNIFE_DIR( 2, 2, .6, .5,       1, 0, 0, 1),
    THROWING_AXE_THR(   1, 5, 1.2, 10,      1, 0, 0, 10),
    THROWING_AXE_DIR(   2, 2, .7, .7,       1, 0, 0, 1),
    JAVELIN_THR(        5, 8, 1.9, 15,      1, 0, 0, 6),
    JAVELIN_DIR(        1, 4, 1, 1.2,       1, 0, 0, 1),

    //range
    SHORT_BOW(          2, 5, 1.8, 20,      2, 0, 0, 9),
    HUNTER_BOW(         3, 6, 1.7, 20,      2, 5, 0, 13),
    LONG_BOW(           3, 8, 2, 25,        2, 5, 0, 10),
    LIGHT_CROSSBOW(     4, 7, 2.8, 15,      2, 0, 0, 15),
    CROSSBOW(           6, 9, 3, 15,        2, 0, 0, 15),

    //mages
    WAND(               1, 1, .8, .5,       1, 0, 0, 5),
    SPHERE(             1, 2, .8, .6,       1, 0, 15, 4),
    STAFF(              3, 3, 1.2, 1.4,     2, 0, 25, 10);

    private double dmgMin, dmgMax, attackDuration, range;
    private int hands, accuracy, parry, durability;

    Weapon(double dmgMin, double dmgMax, double attackDuration, double range, int hands, int accuracy, int parry, int durability) {
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.attackDuration = attackDuration;
        this.range = range;
        this.hands = hands;
        this.accuracy = accuracy;
        this.parry = parry;
        this.durability = durability;
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
}
