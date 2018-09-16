package model.armor;

public enum BodyArmor implements Armor{

    NOTHING(0, 0, 0, 0, 0),
    LEATHER_SHIRT(0, 1, 1, 0, 12),
    GAMBISON(0, 1, 1, 1, 8),
    LEATHER_ARMOR(0, 2, 1, 0, 16),
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

    private double headArmor, bodyArmor, armsArmor, legsArmor, durability;

    BodyArmor(double headArmor, double bodyArmor, double armsArmor, double legsArmor, double durability) {
        this.headArmor = headArmor;
        this.bodyArmor = bodyArmor;
        this.armsArmor = armsArmor;
        this.legsArmor = legsArmor;
        this.durability = durability;
    }

    public double getHeadArmor() {
        return headArmor;
    }

    public double getBodyArmor() {
        return bodyArmor;
    }

    public double getArmsArmor() {
        return armsArmor;
    }

    public double getLegsArmor() {
        return legsArmor;
    }

    public double getDurability() {
        return durability;
    }
}
