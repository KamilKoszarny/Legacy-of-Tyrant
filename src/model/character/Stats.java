package model.character;

import model.Battle;
import model.TurnsTracker;

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

    private float actionPoints = 100;
    private float actionPointsMax = 100;

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

    public int getVim() {
        return vim;
    }

    public void setVim(int vim) {
        this.vim = vim;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getEye() {
        return eye;
    }

    public void setEye(int eye) {
        this.eye = eye;
    }

    public int getArm() {
        return arm;
    }

    public void setArm(int arm) {
        this.arm = arm;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(int knowledge) {
        this.knowledge = knowledge;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
        if (hitPoints <= 0)
            character.setState(CharState.DEATH);
    }

    public int getHitPointsMax() {
        return hitPointsMax;
    }

    public void setHitPointsMax(int hitPointsMax) {
        this.hitPointsMax = hitPointsMax;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public float getVigor() {
        return vigor;
    }

    public void setVigor(double vigor) {
        this.vigor = (float) vigor;
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

    public int getVigorMax() {
        return vigorMax;
    }

    public void setVigorMax(int vigorMax) {
        this.vigorMax = vigorMax;
    }

    public float getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(double actionPoints) {
        this.actionPoints = (float) actionPoints;
    }

    public void addActionPoints(double gain) {
        actionPoints += gain;
        if (actionPoints > actionPointsMax)
            actionPoints = actionPointsMax;
    }

    public void subtractActionPoints(double cost) {
        actionPoints -= cost;
    }

    public float getActionPointsMax() {
        return actionPointsMax;
    }

    public void setActionPointsMax(float actionPointsMax) {
        this.actionPointsMax = actionPointsMax;
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

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getLoadMax() {
        return loadMax;
    }

    public void setLoadMax(int loadMax) {
        this.loadMax = loadMax;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(double speedMax) {
        this.speedMax = speedMax;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public double getDmgMin() {
        return dmgMin;
    }

    public void setDmgMin(double dmgMin) {
        this.dmgMin = dmgMin;
    }

    public double getDmgMax() {
        return dmgMax;
    }

    public void setDmgMax(double dmgMax) {
        this.dmgMax = dmgMax;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAvoidance() {
        return avoidance;
    }

    public void setAvoidance(int avoidance) {
        this.avoidance = avoidance;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getHeadArmor() {
        return headArmor;
    }

    public void setHeadArmor(int headArmor) {
        this.headArmor = headArmor;
    }

    public int getBodyArmor() {
        return bodyArmor;
    }

    public void setBodyArmor(int bodyArmor) {
        this.bodyArmor = bodyArmor;
    }

    public int getArmsArmor() {
        return armsArmor;
    }

    public void setArmsArmor(int armsArmor) {
        this.armsArmor = armsArmor;
    }

    public int getLegsArmor() {
        return legsArmor;
    }

    public void setLegsArmor(int legsArmor) {
        this.legsArmor = legsArmor;
    }

    public int getFireResistance() {
        return fireResistance;
    }

    public void setFireResistance(int fireResistance) {
        this.fireResistance = fireResistance;
    }

    public int getWaterResistance() {
        return waterResistance;
    }

    public void setWaterResistance(int waterResistance) {
        this.waterResistance = waterResistance;
    }

    public int getWindResistance() {
        return windResistance;
    }

    public void setWindResistance(int windResistance) {
        this.windResistance = windResistance;
    }

    public int getEarthResistance() {
        return earthResistance;
    }

    public void setEarthResistance(int earthResistance) {
        this.earthResistance = earthResistance;
    }

    public int getMagicResistance() {
        return magicResistance;
    }

    public void setMagicResistance(int magicResistance) {
        this.magicResistance = magicResistance;
    }
}
