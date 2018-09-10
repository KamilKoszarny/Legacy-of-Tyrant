package model.character;

import model.weapon.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CharacterClass {

    BULLY(25, 25, 25, 10, 10, 10, 10, 10, 10, createBullyWeaponsMap()),
    RASCAL(10, 10, 10, 25, 25, 25, 10, 10, 10, new ArrayList<>(Arrays.asList(
            ))),
    ADEPT(10, 10, 10, 10, 10, 10, 25, 25, 25, new ArrayList<>(Arrays.asList(
    )));

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
        weaponsMap.put(Weapon.HALF_HAND_SWORD_2H, .2);
        weaponsMap.put(Weapon.AXE_1H, .2);
        weaponsMap.put(Weapon.AXE_2H, .5);
        weaponsMap.put(Weapon.AXE_2H, .5);
        weaponsMap.put(Weapon.AXE_2H, .5);
        weaponsMap.put(Weapon.AXE_2H, .5);

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

    public Map<Weapon, Double> getWeaponProbability() {
        return weaponProbability;
    }
}
