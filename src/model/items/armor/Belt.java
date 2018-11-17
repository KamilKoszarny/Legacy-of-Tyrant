package model.items.armor;

public enum Belt implements Armor{

    NOTHING(0, 0),
    SASH(0, 3),
    LEATHER_BELT(1, 6),
    WIDE_BELT(1, 8),
    PLATE_BELT(2, 14);

    private int armor, durability;

    Belt(int armor, int durability) {
        this.armor = armor;
        this.durability = durability;
    }

    public int getArmor() {
        return armor;
    }

    public int getDurability() {
        return durability;
    }
}
