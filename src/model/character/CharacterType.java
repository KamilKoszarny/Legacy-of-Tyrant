package model.character;

import model.character.movement.MoveCalculator;

import java.awt.*;
import java.util.Set;

public enum CharacterType {

    HUMAN(0.8, 0, 0, 0, -5, 5, 0, 0, -10, 10, 0, 0),
    DWARF(1.2, 10, 10, 0, -5, 0, -10, -5, 5, -5, 0, 0),
    ELF(0.6, -5, -5, 5, 10, 0, 10, 0, -5, -10, 0, 0),
    HALFELF(0.7, -5, -5, -5, 0, -5, 0, 5, 10, 5, 0, 0),

    BROWN_BEAR(2, 45, 40, 30, 25, 35, 30, 5, 5, 5, 2, 6),
    BLACK_BEAR(2, 50, 45, 35, 30, 40, 35, 5, 5, 5, 3, 7);


    double size;
    Set<Point> relativePointsUnder;

    private int strength;
    private int durability;
    private int stamina;
    private int eye;
    private int arm;
    private int agility;
    private int knowledge;
    private int focus;
    private int charisma;
    private double dmgMin;
    private double dmgMax;


    CharacterType(double size, int strength, int durability, int stamina, int eye, int arm, int agility, int knowledge, int focus, int charisma,
            double dmgMin, double dmgMax) {
        this.size = size;
        relativePointsUnder = MoveCalculator.calcRelativePointsUnder(this);

        this.strength = strength;
        this.durability = durability;
        this.stamina = stamina;
        this.eye = eye;
        this.arm = arm;
        this.agility = agility;
        this.knowledge = knowledge;
        this.focus = focus;
        this.charisma = charisma;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
    }

    public double getSize() {
        return size;
    }


    public Set<Point> getRelativePointsUnder() {
        return relativePointsUnder;
    }

    public int getStrength() {
        return strength;
    }

    public int getDurability() {
        return durability;
    }

    public int getStamina() {
        return stamina;
    }

    public int getEye() {
        return eye;
    }

    public int getArm() {
        return arm;
    }

    public int getAgility() {
        return agility;
    }

    public int getKnowledge() {
        return knowledge;
    }

    public int getFocus() {
        return focus;
    }

    public int getCharisma() {
        return charisma;
    }

    public double getDmgMin() {
        return dmgMin;
    }

    public double getDmgMax() {
        return dmgMax;
    }
}
