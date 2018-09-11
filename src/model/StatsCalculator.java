package model;

import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterGroup;
import model.character.CharacterType;
import model.weapon.Weapon;
import model.weapon.WeaponGroup;

import java.util.Random;

public class StatsCalculator {

    public static void calcCharPA(Character character){

        if(CharacterGroup.INTELLIGENT.getBelongingTypes().contains(character.getType()) ||
                CharacterGroup.HUMANOIDS.getBelongingTypes().contains(character.getType()) )
            calcAndSetPAByTypeAndClass(character);
        else
            calcAndSetPAByType(character);

    }

    public static void calcCharSA(Character character, boolean recalc){

        calcAndSetSAByStrength(character, recalc);
        calcAndSetSAByDurability(character, recalc);
        calcAndSetSAByStamina(character, recalc);
        calcAndSetSAByArm(character, recalc);
        calcAndSetSAByEye(character, recalc);
        calcAndSetSAByAgility(character, recalc);
        calcAndSetSAByKnowledge(character, recalc);
        calcAndSetSAByFocus(character, recalc);
        calcAndSetSAByCharisma(character, recalc);

        calcAndSetOtherSA(character, recalc);
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

    private static void calcAndSetPAByType(Character character){
        CharacterType charType = character.getType();
        Random r = new Random();

        double strength = charType.getStrength() + r.nextInt(10);
        character.setStrength(strength);

        double durability = charType.getDurability() +  r.nextInt(10);
        character.setDurability(durability);

        double stamina = charType.getStamina() + r.nextInt(10);
        character.setStamina(stamina);

        double arm = charType.getArm() + r.nextInt(10);
        character.setArm(arm);

        double eye = charType.getEye() + r.nextInt(10);
        character.setEye(eye);

        double agility = charType.getAgility() + r.nextInt(10);
        character.setAgility(agility);

        double knowledge = charType.getKnowledge() + r.nextInt(10);
        character.setKnowledge(knowledge);

        double focus = charType.getFocus() + r.nextInt(10);
        character.setFocus(focus);

        double charisma = charType.getCharisma() + r.nextInt(10);
        character.setCharisma(charisma);
    }

    private static void calcAndSetSAByStrength(Character character, boolean recalc){
        if(CharacterGroup.INTELLIGENT.getBelongingTypes().contains(character.getType()) ||
                CharacterGroup.HUMANOIDS.getBelongingTypes().contains(character.getType()) ) {
            Weapon weapon = character.getWeapon();
            if (!WeaponGroup.RANGE.getWeapons().contains(weapon) && !WeaponGroup.THROWING.getWeapons().contains(weapon)) {
                character.setDmgMin(weapon.getDmgMin() * (1 + character.getDoubleStrength() / 50));
                character.setDmgMax(weapon.getDmgMax() * (1 + character.getDoubleStrength() / 50));
            }
        } else {
            character.setDmgMin(character.getType().getDmgMin());
            character.setDmgMax(character.getType().getDmgMax());
        }

        if(!recalc)
            character.setHitPoints(character.getDoubleStrength() / 9);
    }

    private static void calcAndSetSAByDurability(Character character, boolean recalc){
        if(!recalc)
            character.setHitPoints(character.getDoubleHitPoints() + character.getDoubleDurability() / 9);
    }

    private static void calcAndSetSAByStamina(Character character, boolean recalc){
        if(!recalc) {
            Random r = new Random();
            character.setVigor(20 + character.getDoubleStamina() / 5 + r.nextInt(20));
            character.setHitPoints(character.getDoubleHitPoints() + character.getDoubleStamina() / 9);
        }
    }

    private static void calcAndSetSAByArm(Character character, boolean recalc){
        Weapon weapon = character.getWeapon();
        if(!WeaponGroup.RANGE.getWeapons().contains(weapon) && !WeaponGroup.THROWING.getWeapons().contains(weapon)) {
            double weaponAccuracy = weapon.getAccuracy();
            character.setChanceToHit(character.getDoubleArm() + weaponAccuracy);
        }

        character.setSpeed(1 + character.getDoubleArm() / 75);
    }

    private static void calcAndSetSAByEye(Character character, boolean recalc){
        Weapon weapon = character.getWeapon();
        if(WeaponGroup.RANGE.getWeapons().contains(weapon) || WeaponGroup.THROWING.getWeapons().contains(weapon)) {
            double weaponAccuracy = weapon.getAccuracy();
            character.setChanceToHit(character.getDoubleEye() + weaponAccuracy);

            character.setDmgMin(weapon.getDmgMin() * (1 + character.getDoubleEye() / 100));
            character.setDmgMax(weapon.getDmgMax() * (1 + character.getDoubleEye() / 100));
        }

        character.setSpeed(character.getDoubleSpeed() + character.getDoubleEye()/75);
    }

    private static void calcAndSetSAByAgility(Character character, boolean recalc){

        character.setSpeed(character.getDoubleSpeed() + character.getDoubleAgility()/75);
    }

    private static void calcAndSetSAByKnowledge(Character character, boolean recalc){

    }

    private static void calcAndSetSAByFocus(Character character, boolean recalc){

    }

    private static void calcAndSetSAByCharisma(Character character, boolean recalc){

    }

    private static void calcAndSetOtherSA(Character character, boolean recalc){
        Weapon weapon = character.getWeapon();
        character.setRange(weapon.getRange());
        character.setAttackDuration(weapon.getAttackDuration());

        character.setHeadArmor(1);
        character.setBodyArmor(1);
        character.setArmsArmor(1);
        character.setLegsArmor(1);
    }
}
