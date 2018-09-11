package model.character;

import model.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;

public enum CharacterClass {

    BULLY(25, 25, 25, 10, 10, 10, 10, 10, 10, createBullyWeaponsMap()),
    RASCAL(10, 10, 10, 25, 25, 25, 10, 10, 10, createRascalWeaponsMap()),
    ADEPT(10, 10, 10, 10, 10, 10, 25, 25, 25, createAdeptWeaponsMap()),
    FANATIC(50, 40, 50, 20, 30, 30, 20, 35, 20, createFanaticWeaponsMap()),
    WIZARD(20, 25, 25, 30, 20, 35, 50, 50, 40, createWizardWeaponsMap());

    private double strength;
    private double durability;
    private double stamina;
    private double eye;
    private double arm;
    private double agility;
    private double knowledge;
    private double focus;
    private double charisma;
    private Map<Weapon, Double> weaponsMap;

    CharacterClass(double strength, double durability, double stamina, double eye, double arm, double agility, double knowledge, double focus, double charisma, Map<Weapon, Double> weaponsMap) {
        this.strength = strength;
        this.durability = durability;
        this.stamina = stamina;
        this.eye = eye;
        this.arm = arm;
        this.agility = agility;
        this.knowledge = knowledge;
        this.focus = focus;
        this.charisma = charisma;
        this.weaponsMap = weaponsMap;
    }

    private static Map<Weapon, Double> createBullyWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, 1.);
        weaponsMap.put(Weapon.CHOPPER, .2);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .5);
        weaponsMap.put(Weapon.ADZE, 1.);
        weaponsMap.put(Weapon.AXE_1H, .2);
        weaponsMap.put(Weapon.AXE_2H, .8);
        weaponsMap.put(Weapon.BIG_ADZE, .6);
        weaponsMap.put(Weapon.CUDGEL, 1.5);
        weaponsMap.put(Weapon.HAMMER, .7);
        weaponsMap.put(Weapon.SPIKE_CLUB, .4);
        weaponsMap.put(Weapon.COMBAT_STICK, .2);
        weaponsMap.put(Weapon.KNIFE, .2);
        weaponsMap.put(Weapon.DAGGER, .1);
        weaponsMap.put(Weapon.KNUCKLE_DUSTER, .5);
        weaponsMap.put(Weapon.SPEAR, .2);
        weaponsMap.put(Weapon.SCYTHE, .3);
        weaponsMap.put(Weapon.LIGHT_CROSSBOW, .1);

        recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static Map<Weapon, Double> createRascalWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .8);
        weaponsMap.put(Weapon.SABRE, .6);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .2);
        weaponsMap.put(Weapon.CLAYMORE, .2);
        weaponsMap.put(Weapon.HALF_HAND_SWORD_2H, .2);
        weaponsMap.put(Weapon.ADZE, .5);
        weaponsMap.put(Weapon.AXE_2H, .1);
        weaponsMap.put(Weapon.PICK, .3);
        weaponsMap.put(Weapon.BIG_ADZE, .1);
        weaponsMap.put(Weapon.CUDGEL, .7);
        weaponsMap.put(Weapon.SPIKE_CLUB, .3);
        weaponsMap.put(Weapon.COMBAT_STICK, .4);
        weaponsMap.put(Weapon.KNIFE, 1.2);
        weaponsMap.put(Weapon.DAGGER, .8);
        weaponsMap.put(Weapon.KNUCKLE_DUSTER, .1);
        weaponsMap.put(Weapon.SPEAR, .5);
        weaponsMap.put(Weapon.SCYTHE, .3);
        weaponsMap.put(Weapon.THROWING_KNIFE_THR, .5);
        weaponsMap.put(Weapon.THROWING_AXE_THR, .3);
        weaponsMap.put(Weapon.SHORT_BOW, 1.);
        weaponsMap.put(Weapon.HUNTER_BOW, .3);
        weaponsMap.put(Weapon.LIGHT_CROSSBOW, .3);

        recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static Map<Weapon, Double> createAdeptWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .3);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .1);
        weaponsMap.put(Weapon.ADZE, .5);
        weaponsMap.put(Weapon.BIG_ADZE, .1);
        weaponsMap.put(Weapon.CUDGEL, .6);
        weaponsMap.put(Weapon.HAMMER, .3);
        weaponsMap.put(Weapon.SPIKE_CLUB, .1);
        weaponsMap.put(Weapon.KNIFE, .6);
        weaponsMap.put(Weapon.DAGGER, .4);
        weaponsMap.put(Weapon.SPEAR, .2);
        weaponsMap.put(Weapon.SCYTHE, .3);
        weaponsMap.put(Weapon.SHORT_BOW, .3);
        weaponsMap.put(Weapon.WAND, .8);
        weaponsMap.put(Weapon.SPHERE, .8);
        weaponsMap.put(Weapon.STAFF, .8);

        recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static Map<Weapon, Double> createFanaticWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .5);
        weaponsMap.put(Weapon.SABRE, .4);
        weaponsMap.put(Weapon.CHOPPER, .7);
        weaponsMap.put(Weapon.AXE_2H, .5);
        weaponsMap.put(Weapon.AXE_1H, .5);
        weaponsMap.put(Weapon.TWO_SIDE_AXE, .7);
        weaponsMap.put(Weapon.PICK, .5);
        weaponsMap.put(Weapon.BATTLE_AXE, .6);
        weaponsMap.put(Weapon.HAMMER, .3);
        weaponsMap.put(Weapon.SPIKE_CLUB, .1);
        weaponsMap.put(Weapon.KNIFE, .1);
        weaponsMap.put(Weapon.DAGGER, .5);
        weaponsMap.put(Weapon.KNUCKLE_DUSTER, .2);
        weaponsMap.put(Weapon.SPEAR, .2);
        weaponsMap.put(Weapon.TRIDENT, .3);
        weaponsMap.put(Weapon.SCYTHE, .2);
        weaponsMap.put(Weapon.THROWING_KNIFE_THR, .2);
        weaponsMap.put(Weapon.THROWING_AXE_THR, .2);
        weaponsMap.put(Weapon.LIGHT_CROSSBOW, .4);

        recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static Map<Weapon, Double> createWizardWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .2);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .1);
        weaponsMap.put(Weapon.AXE_2H, .1);
        weaponsMap.put(Weapon.PICK, .1);
        weaponsMap.put(Weapon.CUDGEL, .2);
        weaponsMap.put(Weapon.HAMMER, .3);
        weaponsMap.put(Weapon.SPIKE_CLUB, .1);
        weaponsMap.put(Weapon.KNIFE, .6);
        weaponsMap.put(Weapon.DAGGER, .4);
        weaponsMap.put(Weapon.SPEAR, .1);
        weaponsMap.put(Weapon.TRIDENT, .2);
        weaponsMap.put(Weapon.SCYTHE, .2);
        weaponsMap.put(Weapon.SHORT_BOW, .2);
        weaponsMap.put(Weapon.HUNTER_BOW, .2);
        weaponsMap.put(Weapon.WAND, 2.);
        weaponsMap.put(Weapon.SPHERE, 2.);
        weaponsMap.put(Weapon.STAFF, 2.);

        recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }


    public static Map<Weapon, Double> recalcWeaponsMap(Map<Weapon, Double> weaponsMap) {
        double sum = 0;
        for (Double probability: weaponsMap.values()) {
            sum += probability;
        }
        for (Weapon weapon: weaponsMap.keySet()) {
            weaponsMap.put(weapon, weaponsMap.get(weapon) / sum);
        }

        return weaponsMap;
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

    public Map<Weapon, Double> getWeaponsMap() {
        return weaponsMap;
    }

}
