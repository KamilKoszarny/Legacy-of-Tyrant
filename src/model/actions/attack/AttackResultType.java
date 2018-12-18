package model.actions.attack;

public enum AttackResultType {
    HIT, MISS, DODGE, PARRY, BOUNCE, BLOCK;

    private int damage;

    AttackResultType() {
    }

    AttackResultType(int damage) {
        this.damage = damage;
    }
}
