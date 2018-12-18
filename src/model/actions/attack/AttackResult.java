package model.actions.attack;

public class AttackResult {

    private AttackResultType type;
    private int damage;

    public AttackResult(AttackResultType type) {
        this.type = type;
    }

    public AttackResult(AttackResultType type, int damage) {
        this.type = type;
        this.damage = damage;
    }

    public AttackResultType getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }
}
