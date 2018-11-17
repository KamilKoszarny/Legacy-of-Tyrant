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

    public static void calcCharSA(Character character){

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

    private static void calcAndSetSAByStrength(Character character){
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

        character.setLoad(character.getStrength());
        character.setCurrentLoad(character.getStrength() / 2);

        updateVimAndHP(character);
    }

    private static void calcAndSetSAByDurability(Character character){
        updateVimAndHP(character);
    }

    private static void calcAndSetSAByStamina(Character character){
        Random r = new Random();
        character.setVigor(20 + character.getStamina() / 5 + r.nextInt(20));
        character.setCurrentVigor(20 + character.getStamina() / 5 + r.nextInt(20) - 10);
        updateVimAndHP(character);
    }

    public static void updateVimAndHP(Character character) {
        character.setVim((character.getStrength() + character.getDurability() + character.getStamina()) / 3);
        character.setHitPoints((int) (30 + character.getVim() * .7));
        character.setCurrentHitPoints((int) (30 + character.getVim() * .7/2.));
    }

    private static void calcAndSetSAByArm(Character character){
        Weapon weapon = character.getWeapon();
        if(!WeaponGroup.RANGE.getWeapons().contains(weapon) && !WeaponGroup.THROWING.getWeapons().contains(weapon)) {
            character.setAccuracy(character.getArm() + weapon.getAccuracy());
        }

        updateDexterityAndSpeed(character);
    }

    private static void calcAndSetSAByEye(Character character){
        Weapon weapon = character.getWeapon();
        if(WeaponGroup.RANGE.getWeapons().contains(weapon) || WeaponGroup.THROWING.getWeapons().contains(weapon)) {
            character.setAccuracy(character.getEye() + weapon.getAccuracy());

            character.setDmgMin(weapon.getDmgMin() * (1 + character.getEye() / 100.));
            character.setDmgMax(weapon.getDmgMax() * (1 + character.getEye() / 100.));
        }

        updateDexterityAndSpeed(character);
    }

    private static void calcAndSetSAByAgility(Character character){
        Weapon weapon = character.getWeapon();
        character.setAvoidance(character.getAgility() + weapon.getParry());

        updateDexterityAndSpeed(character);
    }

    public static void updateDexterityAndSpeed(Character character) {
        character.setDexterity((character.getArm() + character.getEye() + character.getAgility()) / 3);
        character.setSpeed(character.getDexterity() / 25.);
    }

    private static void calcAndSetSAByKnowledge(Character character){
        updateIntelligenceAndMana(character);
    }

    private static void calcAndSetSAByFocus(Character character){
        updateIntelligenceAndMana(character);
    }

    private static void calcAndSetSAByCharisma(Character character){
        updateIntelligenceAndMana(character);
    }

    public static void updateIntelligenceAndMana(Character character) {
        character.setIntelligence((character.getKnowledge() + character.getFocus() + character.getSpirit()) / 3);
        character.setMana(character.getIntelligence() / 5);
        character.setCurrentMana(character.getIntelligence() / 5/2);
    }

    private static void calcAndSetOtherSA(Character character){
        Weapon weapon = character.getWeapon();
        character.setRange(weapon.getRange());
        character.setAttackSpeed(1 / weapon.getAttackDuration());

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
        character.setBlock(shield.getBlock());
    }
}
