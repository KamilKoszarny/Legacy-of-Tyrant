package model.character;

import model.items.armor.*;
import model.items.weapon.Weapon;
import model.items.weapon.WeaponGroup;

import java.util.Random;

public class StatsCalculator {

    public static void createStats(Character character){
        if(CharacterGroup.INTELLIGENT.getBelongingTypes().contains(character.getType()) ||
                CharacterGroup.HUMANOIDS.getBelongingTypes().contains(character.getType()) )
            setStatsByTypeAndClass(character);
        else
            setStatsByType(character);
        calcMainStats(character);
        calcMinorStats(character);
    }

    private static void setStatsByTypeAndClass(Character character){
        CharacterType charType = character.getType();
        CharacterClass charClass = character.getCharClass();
        Random r = new Random();

        int strength = charType.getStats().getStrength() + charClass.getStats().getStrength() + r.nextInt(10);
        int durability = charType.getStats().getDurability() + charClass.getStats().getDurability() + r.nextInt(10);
        int stamina = charType.getStats().getStamina() + charClass.getStats().getStamina() + r.nextInt(10);
        int arm = charType.getStats().getArm() + charClass.getStats().getArm() + r.nextInt(10);
        int eye = charType.getStats().getEye() + charClass.getStats().getEye() + r.nextInt(10);
        int agility = charType.getStats().getAgility() + charClass.getStats().getAgility() + r.nextInt(10);
        int knowledge = charType.getStats().getKnowledge() + charClass.getStats().getKnowledge() + r.nextInt(10);
        int focus = charType.getStats().getFocus() + charClass.getStats().getFocus() + r.nextInt(10);
        int spirit = charType.getStats().getSpirit() + charClass.getStats().getSpirit() + r.nextInt(10);

        character.setStats(new Stats(character, strength, durability, stamina, arm, eye, agility, knowledge, focus, spirit));
    }

    private static void setStatsByType(Character character){
        CharacterType charType = character.getType();
        Random r = new Random();

        int strength = charType.getStats().getStrength() + r.nextInt(10);
        int durability = charType.getStats().getDurability() +  r.nextInt(10);
        int stamina = charType.getStats().getStamina() + r.nextInt(10);
        int arm = charType.getStats().getArm() + r.nextInt(10);
        int eye = charType.getStats().getEye() + r.nextInt(10);
        int agility = charType.getStats().getAgility() + r.nextInt(10);
        int knowledge = charType.getStats().getKnowledge() + r.nextInt(10);
        int focus = charType.getStats().getFocus() + r.nextInt(10);
        int spirit = charType.getStats().getSpirit() + r.nextInt(10);

        character.setStats(new Stats(character, strength, durability, stamina, arm, eye, agility, knowledge, focus, spirit));
    }

    public static void calcStats(Character character) {
        calcMainStats(character);
        calcMinorStats(character);
    }

    private static void calcMainStats(Character character) {
        Stats stats = character.getStats();
        stats.setVim((stats.getStrength() + stats.getDurability() + stats.getStamina()) / 3);
        stats.setDexterity((stats.getArm() + stats.getEye() + stats.getAgility()) / 3);
        stats.setIntelligence((stats.getKnowledge() + stats.getFocus() + stats.getSpirit()) / 3);
    }

    private static void calcMinorStats(Character character){
        Stats stats = character.getStats();
        calcHitPoints(stats);
        calcMana(stats);
        calcVigor(stats);
        calcLoad(stats);
        calcSpeed(stats);
        calcRange(character);
        calcAttackSpeed(character);
        calcDmgAndAccuracy(character);
        calcAvoidance(character);
        calcBlock(character);
        calcMagicResistance(character);
        calcArmor(character);
    }

    private static void calcHitPoints(Stats stats) {
       stats.setHitPointsMax((int) (30 + stats.getVim() * .7));
       stats.setHitPoints((int) (30 + stats.getVim() * .7/2.));
    }

    private static void calcMana(Stats stats) {
        stats.setManaMax(stats.getIntelligence() / 4);
        stats.setMana(stats.getIntelligence() / 4/2);
    }

    private static void calcVigor(Stats stats) {
        Random r = new Random();
        stats.setVigorMax(20 + stats.getStamina() / 5);
        stats.setVigor(20 + stats.getStamina() / 5 - 10);
    }

    private static void calcLoad(Stats stats) {
        stats.setLoadMax(stats.getStrength());
        stats.setLoad(stats.getStrength() / 2);
    }

    private static void calcSpeed(Stats stats) {
        stats.setSpeedMax(1 + stats.getDexterity() / 50.);
    }

    private static void calcRange(Character character) {
        character.getStats().setRange(character.getItems().getWeapon().getRange());
    }

    private static void calcAttackSpeed(Character character) {
        character.getStats().setAttackSpeed(1 / character.getItems().getWeapon().getAttackDuration());
    }

    private static void calcDmgAndAccuracy(Character character){
        Stats stats = character.getStats();
        if(CharacterGroup.useWeapon(character.getType())) {
            Weapon weapon = character.getItems().getWeapon();
            if (WeaponGroup.isRange(weapon)) {
                stats.setAccuracy(stats.getEye() + weapon.getAccuracy());
                stats.setDmgMin(weapon.getDmgMin() * (1 + stats.getEye() / 100.));
                stats.setDmgMax(weapon.getDmgMax() * (1 + stats.getEye() / 100.));
            } else {
                character.getStats().setAccuracy(character.getStats().getArm() + weapon.getAccuracy());
                stats.setDmgMin(weapon.getDmgMin() * (1 + stats.getStrength() / 50.));
                stats.setDmgMax(weapon.getDmgMax() * (1 + stats.getStrength() / 50.));
            }
        } else {
            stats.setDmgMin(character.getType().getStats().getDmgMin());
            stats.setDmgMax(character.getType().getStats().getDmgMax());
        }
    }

    private static void calcAvoidance(Character character) {
        character.getStats().setAvoidance(character.getStats().getAgility() + character.getItems().getWeapon().getParry());
    }

    private static void calcBlock(Character character) {
        character.getStats().setBlock(character.getItems().getShield().getBlock());
    }

    private static void calcMagicResistance(Character character) {

    }

    private static void calcArmor(Character character) {
        Armor[] armor = character.getItems().getArmor();
        BodyArmor bodyArmor = (BodyArmor) armor[1];
        Helmet helmet = (Helmet) armor[2];
        Gloves gloves = (Gloves) armor[3];
        Boots boots = (Boots) armor[4];
        Belt belt = (Belt) armor[5];

        character.getStats().setHeadArmor(bodyArmor.getHeadArmor() + helmet.getArmor());
        character.getStats().setBodyArmor(bodyArmor.getBodyArmor() + belt.getArmor());
        character.getStats().setArmsArmor(bodyArmor.getArmsArmor() + gloves.getArmor());
        character.getStats().setLegsArmor(bodyArmor.getLegsArmor() + boots.getArmor());
    }
}
