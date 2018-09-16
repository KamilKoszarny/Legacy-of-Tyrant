package model.armor;

public enum Gloves implements Armor{

    NOTHING(0, 0),
    RAG_GLOVES(0, 3),
    LEATHER_GLOVES(1, 6),
    CHAIN_GLOVES(2, 9),
    STEEL_GLOVES(4, 16),
    PLATE_GLOVES(3, 13);

    private double armor, durability;

    Gloves(double armor, double durability) {
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
