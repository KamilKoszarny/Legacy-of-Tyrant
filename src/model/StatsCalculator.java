package model;

import model.armor.*;
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

        int strength = charType.getStrength() + charClass.getStrength() + r.nextInt(10);
        character.setStrength(strength);

        int durability = charType.getDurability() + charClass.getDurability() + r.nextInt(10);
        character.setDurability(durability);

        int stamina = charType.getStamina() + charClass.getStamina() + r.nextInt(10);
        character.setStamina(stamina);

        int arm = charType.getArm() + charClass.getArm() + r.nextInt(10);
        character.setArm(arm);

        int eye = charType.getEye() + charClass.getEye() + r.nextInt(10);
        character.setEye(eye);

        int agility = charType.getAgility() + charClass.getAgility() + r.nextInt(10);
        character.setAgility(agility);

        int knowledge = charType.getKnowledge() + charClass.getKnowledge() + r.nextInt(10);
        character.setKnowledge(knowledge);

        int focus = charType.getFocus() + charClass.getFocus() + r.nextInt(10);
        character.setFocus(focus);

        int charisma = charType.getCharisma() + charClass.getCharisma() + r.nextInt(10);
        character.setSpirit(charisma);
    }

    private static void calcAndSetPAByType(Character character){
        CharacterType charType = character.getType();
        Random r = new Random();

        int strength = charType.getStrength() + r.nextInt(10);
        character.setStrength(strength);

        int durability = charType.getDurability() +  r.nextInt(10);
        character.setDurability(durability);

        int stamina = charType.getStamina() + r.nextInt(10);
        character.setStamina(stamina);

        int arm = charType.getArm() + r.nextInt(10);
        character.setArm(arm);

        int eye = charType.getEye() + r.nextInt(10);
        character.setEye(eye);

        int agility = charType.getAgility() + r.nextInt(10);
        character.setAgility(agility);

        int knowledge = charType.getKnowledge() + r.nextInt(10);
        character.setKnowledge(knowledge);

        int focus = charType.getFocus() + r.nextInt(10);
        character.setFocus(focus);

        int charisma = charType.getCharisma() + r.nextInt(10);
        character.setSpirit(charisma);
    }

    private static void calcAndSetSAByStrength(Character character, boolean recalc){
        if(CharacterGroup.INTELLIGENT.getBelongingTypes().contains(character.getType()) ||
                CharacterGroup.HUMANOIDS.getBelongingTypes().contains(character.getType()) ) {
            Weapon weapon = character.getWeapon();
            if (!WeaponGroup.RANGE.getWeapons().contains(weapon) && !WeaponGroup.THROWING.getWeapons().contains(weapon)) {
                character.setDmgMin(weapon.getDmgMin() * (1 + character.getStrength() / 50.));
                character.setDmgMax(weapon.getDmgMax() * (1 + character.getStrength() / 50.));
            }
        } else {
            character.setDmgMin(character.getType().getDmgMin());
            character.setDmgMax(character.getType().getDmgMax());
        }

        if(!recalc)
            character.setHitPoints(character.getStrength() / 9);
    }

    private static void calcAndSetSAByDurability(Character character, boolean recalc){
        if(!recalc)
            character.setHitPoints(character.getHitPoints() + character.getDurability() / 9);
    }

    private static void calcAndSetSAByStamina(Character character, boolean recalc){
        if(!recalc) {
            Random r = new Random();
            character.setVigor(20 + character.getStamina() / 5 + r.nextInt(20));
            character.setHitPoints(character.getHitPoints() + character.getStamina() / 9);
        }
    }

    private static void calcAndSetSAByArm(Character character, boolean recalc){
        Weapon weapon = character.getWeapon();
        if(!WeaponGroup.RANGE.getWeapons().contains(weapon) && !WeaponGroup.THROWING.getWeapons().contains(weapon)) {
            int weaponAccuracy = weapon.getAccuracy();
            character.setChanceToHit(character.getArm() + weaponAccuracy);
        }

        character.setSpeed(1 + character.getArm() / 75.);
    }

    private static void calcAndSetSAByEye(Character character, boolean recalc){
        Weapon weapon = character.getWeapon();
        if(WeaponGroup.RANGE.getWeapons().contains(weapon) || WeaponGroup.THROWING.getWeapons().contains(weapon)) {
            int weaponAccuracy = weapon.getAccuracy();
            character.setChanceToHit(character.getEye() + weaponAccuracy);

            character.setDmgMin(weapon.getDmgMin() * (1 + character.getEye() / 100.));
            character.setDmgMax(weapon.getDmgMax() * (1 + character.getEye() / 100.));
        }

        character.setSpeed(character.getSpeed() + character.getEye()/75.);
    }

    private static void calcAndSetSAByAgility(Character character, boolean recalc){

        character.setSpeed(character.getSpeed() + character.getAgility()/75.);
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

        Armor[] armor = character.getArmor();
        Shield shield = (Shield) armor[0];
        BodyArmor bodyArmor = (BodyArmor) armor[1];
        Helmet helmet = (Helmet) armor[2];
        Gloves gloves = (Gloves) armor[3];
        Boots boots = (Boots) armor[4];
        Belt belt = (Belt) armor[5];

        character.setHeadArmor(bodyArmor.getHeadArmor() + helmet.getArmor());
        character.setBodyArmor(bodyArmor.getBodyArmor() + belt.getArmor());
        character.setArmsArmor(bodyArmor.getArmsArmor() + gloves.getArmor());
        character.setLegsArmor(bodyArmor.getLegsArmor() + boots.getArmor());
    }
}
