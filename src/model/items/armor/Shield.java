package model.items.armor;

public enum Shield implements Armor{

    NOTHING(0, 0, 0),
    WOODEN_SHIELD(10, 0, 5),
    BUCKLER(15, 0, 12),
    TARGE(15, 1, 15),
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

    Shield(int block, int dmg, int durability) {
        this.block = block;
        this.dmg = dmg;
        this.durability = durability;
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
}
