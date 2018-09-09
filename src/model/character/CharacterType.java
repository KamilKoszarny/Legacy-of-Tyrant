package model.character;

import model.MoveCalculator;

import java.awt.*;
import java.util.Set;

public enum CharacterType {

    HUMAN(0.8, 0, 0, 0, -5, 5, 0, 0, -10, 10),
    DWARF(1.2, 10, 10, 0, -5, 0, -10, -5, 5, -5),
    ELF(0.6, -5, -5, 5, 10, 0, 10, 0, -5, -10),
    HALFELF(0.7, -5, -5, -5, 0, -5, 0, 5, 10, 5);

    double size;
    Set<Point> relativePointsUnder;

    private double strength;
    private double durability;
    private double stamina;
    private double eye;
    private double arm;
    private double agility;
    private double knowledge;
    private double focus;
    private double charisma;


    CharacterType(double size, double strength, double durability, double stamina, double eye, double arm, double agility, double knowledge, double focus, double charisma) {
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
    }

    public double getSize() {
        return size;
    }


    public Set<Point> getRelativePointsUnder() {
        return relativePointsUnder;
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
