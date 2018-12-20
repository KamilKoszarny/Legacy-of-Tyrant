package model.actions.attack;

import model.character.Character;
import model.map.Map;

import java.util.HashMap;
import java.util.Random;

public class AttackCalculator {

    public static final int
            HEAD2HEAD_CHANCE = 50, HEAD2BODY_CHANCE = 20,
            BODY2BODY_CHANCE = 60, BODY2HEAD_CHANCE = 10, BODY2ARMS_CHANCE = 15, BODY2LEGS_CHANCE = 15,
            ARMS2BODY_CHANCE = 20, ARMS2ARMS_CHANCE = 70,
            LEGS2BODY_CHANCE = 20, LEGS2LEGS_CHANCE = 70;

    public static boolean isInRange(Character charA, Character charB){
//        if (charA.getPosition().distance(charB.getPosition()) * Map.RESOLUTION_M <
//                charA.getRange() + charA.getType().getSize()/2 + charB.getType().getSize()/2)
        if (charA.getPosition().distance(charB.getPosition()) * Map.M_PER_POINT < charA.getRange())
            return true;
        return false;
    }

    public static int calcChanceToHit(Character charA, Character charB){
        return (int) (50 + charA.getAccuracy() - charB.getAgility() - 2*charA.getPosition().distance(charB.getPosition())*Map.RESOLUTION_M);
    }

    public static java.util.Map<BodyPart, Integer> calcChancesToHitByBodyPart(Character charA, Character charB, BodyPart bodyPart) {
        int chanceToHit = calcChanceToHit(charA, charB);
        java.util.Map<BodyPart, Integer> chancesToHit = new HashMap<>();
        switch (bodyPart) {
            case BODY:
                chancesToHit.put(BodyPart.HEAD, chanceToHit*AttackCalculator.BODY2HEAD_CHANCE/100);
                chancesToHit.put(BodyPart.BODY, chanceToHit*AttackCalculator.BODY2BODY_CHANCE/100);
                chancesToHit.put(BodyPart.ARMS, chanceToHit*AttackCalculator.BODY2ARMS_CHANCE/100);
                chancesToHit.put(BodyPart.LEGS, chanceToHit*AttackCalculator.BODY2LEGS_CHANCE/100);
                return chancesToHit;
            case HEAD:
                chancesToHit.put(BodyPart.HEAD, chanceToHit*AttackCalculator.HEAD2HEAD_CHANCE/100);
                chancesToHit.put(BodyPart.BODY, chanceToHit*AttackCalculator.HEAD2BODY_CHANCE/100);
                chancesToHit.put(BodyPart.ARMS, 0);
                chancesToHit.put(BodyPart.LEGS, 0);
                return chancesToHit;
            case ARMS:
                chancesToHit.put(BodyPart.HEAD, 0);
                chancesToHit.put(BodyPart.BODY, chanceToHit*AttackCalculator.ARMS2BODY_CHANCE/100);
                chancesToHit.put(BodyPart.ARMS, chanceToHit*AttackCalculator.ARMS2ARMS_CHANCE/100);
                chancesToHit.put(BodyPart.LEGS, 0);
                return chancesToHit;
            case LEGS:
                chancesToHit.put(BodyPart.HEAD, 0);
                chancesToHit.put(BodyPart.BODY, chanceToHit*AttackCalculator.LEGS2BODY_CHANCE/100);
                chancesToHit.put(BodyPart.ARMS, 0);
                chancesToHit.put(BodyPart.LEGS, chanceToHit*AttackCalculator.LEGS2LEGS_CHANCE/100);
                return chancesToHit;
        }
        return null;
    }

    public static BodyPart calcBodyPartHit(BodyPart bodyPart) {
        int a = new Random().nextInt(100);
        switch (bodyPart) {
            case HEAD:
                if (a < HEAD2HEAD_CHANCE)
                    return BodyPart.HEAD;
                if (a < HEAD2HEAD_CHANCE + HEAD2BODY_CHANCE)
                    return BodyPart.BODY;
                return null;
            case BODY:
                if (a < BODY2BODY_CHANCE)
                    return BodyPart.BODY;
                if (a < BODY2BODY_CHANCE + BODY2ARMS_CHANCE)
                    return BodyPart.ARMS;
                if (a < BODY2BODY_CHANCE + BODY2ARMS_CHANCE + BODY2LEGS_CHANCE)
                    return BodyPart.LEGS;
                return BodyPart.HEAD;
            case ARMS:
                if (a < ARMS2ARMS_CHANCE)
                    return BodyPart.ARMS;
                if (a < ARMS2ARMS_CHANCE + ARMS2BODY_CHANCE)
                    return BodyPart.BODY;
                return null;
            case LEGS:
                if (a < LEGS2LEGS_CHANCE)
                    return BodyPart.LEGS;
                if (a < LEGS2LEGS_CHANCE + LEGS2BODY_CHANCE)
                    return BodyPart.BODY;
                return null;
        }
        return null;
    }

    static int calcDamage(Character charA, Character charB, int score, int chanceToHit, BodyPart bodyPart){
        int damage =  (int)(charA.getDmgMin() + (charA.getDmgMax() - charA.getDmgMin())
                * (chanceToHit - score) / chanceToHit);
        int damageResisted = charB.getDurability() / 10;
        switch (bodyPart){
            case HEAD: damageResisted += charB.getHeadArmor() - 1; break;
            case BODY: damageResisted += charB.getBodyArmor(); break;
            case ARMS: damageResisted += charB.getArmsArmor(); break;
            case LEGS: damageResisted += charB.getLegsArmor(); break;
        }

        damage -= damageResisted;
        if (damage < 0)
            damage = 0;
        return damage;
    }

    public static void updateStats(Character attacker, Character victim, int damage){
        victim.setHitPoints(victim.getHitPoints() - damage);
        attacker.setMsLeft(attacker.getMsLeft() - (int)(1 / attacker.getAttackSpeed() * 1000));
        attacker.setVigor((int) (attacker.getVigor() - (1 / attacker.getAttackSpeed())));
    }

    public static Double calcDodgeChance(Character character){
        double dodgeChance = character.getAgility();
        if(character.isReady())
            dodgeChance += 30;
        return dodgeChance;
    }

    public static Double calcParryChance(Character character){
        double parryChance = character.getWeapon().getParry();
        if(character.isReady())
            parryChance += 30;
        return parryChance;
    }

    public static Double calcBounceChance(Character character){
        double bounceChance = character.getAgility() + 10;
        if(character.isReady())
            bounceChance += 30;
        return bounceChance;
    }
}
