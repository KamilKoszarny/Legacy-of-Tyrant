package model.items;

import model.items.armor.*;
import model.items.weapon.Weapon;

import java.util.Random;

public class ItemsGenerator {

    private static final Random R = new Random();
    private static final double
            WEAPON_CHANCE = .30,
            SHIELD_CHANCE = .40,
            ARMOR_CHANCE = .50,
            HELMET_CHANCE = .60,
            GLOVES_CHANCE = .70,
            BOOTS_CHANCE = .80,
            BELT_CHANCE = .90,
            AMULET_CHANCE = .92,
            RING_CHANCE = .96;

    public static void main(String[] args) {
        System.out.println(generateItem(0));
    }

    public static Item generateItem(int level) {
        double randomValue = R.nextDouble();
        if (randomValue < WEAPON_CHANCE)
            return generateWeapon();
        else if (randomValue < SHIELD_CHANCE)
            return generateShield();
        else if (randomValue < ARMOR_CHANCE)
            return generateBodyArmor();
        else if (randomValue < HELMET_CHANCE)
            return generateHelmet();
        else if (randomValue < GLOVES_CHANCE)
            return generateGloves();
        else if (randomValue < BOOTS_CHANCE)
            return generateBoots();
        else if (randomValue < BELT_CHANCE)
            return generateBelt();
        else if (randomValue < AMULET_CHANCE)
            return generateAmulet();
        else if (randomValue < RING_CHANCE)
            return generateRing();
        return null;
    }

    private static Weapon generateWeapon(){
        double randomValue = R.nextDouble();
        for (Weapon weapon: Weapon.values()) {
            if (randomValue < (double) (weapon.ordinal() - 1) / Weapon.values().length)
                return weapon;
        }
        return null;
    }

    private static Shield generateShield(){
        double randomValue = R.nextDouble();
        for (Shield shield : Shield.values()) {
            if (randomValue < (double) (shield.ordinal() - 1) / Shield.values().length)
                return shield;
        }
        return null;
    }

    private static BodyArmor generateBodyArmor(){
        double randomValue = R.nextDouble();
        for (BodyArmor bodyArmor : BodyArmor.values()) {
            if (randomValue < (double) (bodyArmor.ordinal() - 1) / BodyArmor.values().length)
                return bodyArmor;
        }
        return null;
    }

    private static Helmet generateHelmet(){
        double randomValue = R.nextDouble();
        for (Helmet helmet : Helmet.values()) {
            if (randomValue < (double) (helmet.ordinal() - 1) / Helmet.values().length)
                return helmet;
        }
        return null;
    }

    private static Gloves generateGloves(){
        double randomValue = R.nextDouble();
        for (Gloves gloves : Gloves.values()) {
            if (randomValue < (double) (gloves.ordinal() - 1) / Gloves.values().length)
                return gloves;
        }
        return null;
    }

    private static Boots generateBoots(){
        double randomValue = R.nextDouble();
        for (Boots boots : Boots.values()) {
            if (randomValue < (double) (boots.ordinal() - 1) / Boots.values().length)
                return boots;
        }
        return null;
    }

    private static Belt generateBelt(){
        double randomValue = R.nextDouble();
        for (Belt belt : Belt.values()) {
            if (randomValue < (double) (belt.ordinal() - 1) / Belt.values().length)
                return belt;
        }
        return null;
    }

    private static Amulet generateAmulet(){
        double randomValue = R.nextDouble();
        for (Amulet amulet : Amulet.values()) {
            if (randomValue < (double) (amulet.ordinal() - 1) / Amulet.values().length)
                return amulet;
        }
        return null;
    }

    private static Ring generateRing(){
        double randomValue = R.nextDouble();
        for (Ring ring : Ring.values()) {
            if (randomValue < (double) (ring.ordinal() - 1) / Ring.values().length)
                return ring;
        }
        return null;
    }


}
