package model.armor;

public enum Boots implements Armor{

    NOTHING(0, 0),
    RAG_BOOTS(0, 3),
    LEATHER_BOOTS(1, 6),
    CHAIN_BOOTS(2, 9),
    STEEL_BOOTS(4, 16),
    PLATE_BOOTS(3, 13);

    private int armor, durability;

    Boots(int armor, int durability) {
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
