package model.character;

import lombok.Data;
import model.Battle;
import model.TurnsTracker;

@Data
public class Stats {

    private Character character;

    private int vim;
    private int strength;
    private int durability;
    private int stamina;

    private int dexterity;
    private int eye;
    private int arm;
    private int agility;

    private int intelligence;
    private int knowledge;
    private int focus;
    private int spirit;


    private int hitPoints;
    private int hitPointsMax;
    private int mana;
    private int manaMax;
    private float vigor = -999;
    private int vigorMax = -999;

    private double actionPoints = 100;
    private double actionPointsMax = 100;

    private int load;
    private int loadMax;
    private double speed;
    private double speedMax;
    private double range;

    private double dmgMin, dmgMax;
    private int accuracy;
    private double attackSpeed;

    private int avoidance;
    private int block;
    private int headArmor = 0, bodyArmor = 0, armsArmor = 0 ,legsArmor = 0;
    private int fireResistance, waterResistance, windResistance, earthResistance, magicResistance;

    public Stats(Character character) {
        this.character = character;
    }

    public Stats(Character character, int strength, int durability, int stamina, int eye, int arm, int agility, int knowledge, int focus, int spirit) {
        this.character = character;

        this.strength = strength;
        this.durability = durability;
        this.stamina = stamina;

        this.eye = eye;
        this.arm = arm;
        this.agility = agility;

        this.knowledge = knowledge;
        this.focus = focus;
        this.spirit = spirit;
    }

    public Stats(Character character, int strength, int durability, int stamina, int eye, int arm, int agility, int knowledge, int focus, int spirit, double dmgMin, double dmgMax) {
        this.character = character;

        this.strength = strength;
        this.durability = durability;
        this.stamina = stamina;

        this.eye = eye;
        this.arm = arm;
        this.agility = agility;

        this.knowledge = knowledge;
        this.focus = focus;
        this.spirit = spirit;

        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
    }

    public void addVigor(double gain) {
        vigor += gain;
        if (vigor > vigorMax)
            vigor = vigorMax;
    }

    public void subtractVigor(double cost) {
        vigor -= cost;
        if (vigor < 0)
            vigor = 0;
        if (Battle.isTurnMode())
            TurnsTracker.calcAPMax(character);
    }

    public void addActionPoints(double gain) {
        actionPoints += gain;
        if (actionPoints > actionPointsMax)
            actionPoints = actionPointsMax;
    }

    public void subtractActionPoints(double cost) {
        actionPoints -= cost;
    }

    public void addActionPointsMax(double gain) {
        actionPointsMax += gain;
        if (actionPointsMax > 100)
            actionPointsMax = 100;
    }

    public void subtractActionPointsMax(double cost) {
        actionPointsMax -= cost;
        if (actionPointsMax < 1)
            actionPointsMax = 1;
    }
}
