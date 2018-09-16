package model.armor;

public enum Belt implements Armor{

    NOTHING(0, 0),
    SASH(0, 3),
    LEATHER_BELT(1, 6),
    WIDE_BELT(1, 8),
    PLATE_BELT(2, 14);

    private double armor, durability;

    Belt(double armor, double durability) {
        this.armor = armor;
        this.durability = durability;
    }

    public double getArmor() {
        return armor;
    }

    public double getDurability() {
        return durability;
    }
}
