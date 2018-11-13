package model.armor;

public enum Helmet implements Armor{

    NOTHING(0, 0),
    LEATHER_HOOD(1, 5),
    CASQUE(2, 15),
    HELMET(3, 12),
    CLOSED_HELMET(4, 16),
    MASK(1, 10),
    SKULL_HELMET(4, 24),
    WOLF_CAP(2, 15),
    EAGLE_HELMET(3, 18),
    BARBARIAN_HELMET(3, 10),
    HORN_HELMET(4, 12),
    GLADIATOR_HELMET(4, 15),
    TOURNAMENT_HELMET(5, 20),
    CROWN(5, 30);

    private int armor, durability;

    Helmet(int armor, int durability) {
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
