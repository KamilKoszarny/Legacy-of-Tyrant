package model.character;

public enum CharacterClass {

    BULLY(25, 25, 25, 10, 10, 10, 10, 10, 10),
    RASCAL(10, 10, 10, 25, 25, 25, 10, 10, 10),
    ADEPT(10, 10, 10, 10, 10, 10, 25, 25, 25);

    private double strength;
    private double durability;
    private double stamina;
    private double eye;
    private double arm;
    private double agility;
    private double knowledge;
    private double focus;
    private double charisma;

    CharacterClass(double strength, double durability, double stamina, double eye, double arm, double agility, double knowledge, double focus, double charisma) {
        this.strength = strength;
        this.durability = durability;
        this.stamina = stamina;
        this.eye = eye;
        this.arm = arm;
        this.agility = agility;
        this.knowledge = knowledge;
        this.focus = focus;
        this.charisma = charisma;
    }


    public double getStrength() {
        return strength;
    }

    public double getDurability() {
        return durability;
    }

    public double getStamina() {
        return stamina;
    }

    public double getEye() {
        return eye;
    }

    public double getArm() {
        return arm;
    }

    public double getAgility() {
        return agility;
    }

    public double getKnowledge() {
        return knowledge;
    }

    public double getFocus() {
        return focus;
    }

    public double getCharisma() {
        return charisma;
    }
}
