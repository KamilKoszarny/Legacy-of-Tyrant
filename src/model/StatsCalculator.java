package model;

import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterType;

import java.util.Random;

public class StatsCalculator {

    public static void calcCharStats(Character character){

        calcAndSetPAByTypeAndClass(character);
        calcAndSetSAByStrength(character);
        calcAndSetSAByDurability(character);
        calcAndSetSAByStamina(character);
        calcAndSetSAByArm(character);
        calcAndSetSAByEye(character);
        calcAndSetSAByAgility(character);
        calcAndSetSAByKnowledge(character);
        calcAndSetSAByFocus(character);
        calcAndSetSAByCharisma(character);

        calcAndSetOtherSA(character);
    }

    private static void calcAndSetPAByTypeAndClass(Character character){
        CharacterType charType = character.getType();
        CharacterClass charClass = character.getCharClass();
        Random r = new Random();

        double strength = charType.getStrength() + charClass.getStrength() + r.nextInt(10);
        character.setStrength(strength);

        double durability = charType.getDurability() + charClass.getDurability() + r.nextInt(10);
        character.setDurability(durability);

        double stamina = charType.getStamina() + charClass.getStamina() + r.nextInt(10);
        character.setStamina(stamina);

        double arm = charType.getArm() + charClass.getArm() + r.nextInt(10);
        character.setArm(arm);

        double eye = charType.getEye() + charClass.getEye() + r.nextInt(10);
        character.setEye(eye);

        double agility = charType.getAgility() + charClass.getAgility() + r.nextInt(10);
        character.setAgility(agility);

        double knowledge = charType.getKnowledge() + charClass.getKnowledge() + r.nextInt(10);
        character.setKnowledge(knowledge);

        double focus = charType.getFocus() + charClass.getFocus() + r.nextInt(10);
        character.setFocus(focus);

        double charisma = charType.getCharisma() + charClass.getCharisma() + r.nextInt(10);
        character.setCharisma(charisma);
    }


    private static void calcAndSetSAByStrength(Character character){
        double weaponDmgMin = 1;
        double weaponDmgMax = 3;
        character.setDmgMin(weaponDmgMin * (1 + character.getDoubleStrength() /50));
        character.setDmgMax(weaponDmgMax * (1 + character.getDoubleStrength() /50));

        character.setHitPoints(character.getDoubleStrength() / 9);
    }

    private static void calcAndSetSAByDurability(Character character){
        character.setHitPoints(character.getDoubleHitPoints() + character.getDoubleDurability() / 9);
    }

    private static void calcAndSetSAByStamina(Character character){
        character.setHitPoints(character.getDoubleHitPoints() + character.getDoubleStamina() / 9);
    }

    private static void calcAndSetSAByArm(Character character){
        double weaponChanceToHit = 0;
        character.setChanceToHit(weaponChanceToHit + character.getDoubleArm());

        character.setSpeed(1 + character.getDoubleArm()/75);
    }

    private static void calcAndSetSAByEye(Character character){

        character.setSpeed(1 + character.getDoubleArm()/75);
    }

    private static void calcAndSetSAByAgility(Character character){

        character.setSpeed(1 + character.getDoubleArm()/75);
    }

    private static void calcAndSetSAByKnowledge(Character character) {

    }

    private static void calcAndSetSAByFocus(Character character) {

    }

    private static void calcAndSetSAByCharisma(Character character) {

    }

    private static void calcAndSetOtherSA(Character character){
        character.setRange(1);
        character.setAttackDuration(1);
        character.setHeadArmor(1);
        character.setBodyArmor(1);
        character.setArmsArmor(1);
        character.setLegsArmor(1);
    }
}
