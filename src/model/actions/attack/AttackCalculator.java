package model.actions.attack;

import model.character.Character;
import model.map.Map;

public class AttackCalculator {

    public static boolean isInRange(Character charA, Character charB){
//        if (charA.getPosition().distance(charB.getPosition()) * Map.RESOLUTION_M <
//                charA.getRange() + charA.getType().getSize()/2 + charB.getType().getSize()/2)
        if (charA.getPosition().distance(charB.getPosition()) * Map.M_PER_POINT < charA.getRange())
            return true;
        return false;
    }

    public static void updateStats(Character attacker, Character victim, int damage){
        victim.setHitPoints(victim.getHitPoints() - damage);
        attacker.setMsLeft(attacker.getMsLeft() - (int)(1 / attacker.getAttackSpeed() * 1000));
        attacker.setVigor((int) (attacker.getVigor() - (1 / attacker.getAttackSpeed())));
    }

    public static int calcChanceToHit(Character charA, Character charB){
        return (int) (50 + charA.getAccuracy() - charB.getAgility() - 2*charA.getPosition().distance(charB.getPosition())*Map.RESOLUTION_M);
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

    static int calcDamage(Character charA, Character charB, int score, int chanceToHit, AttackType attackType){
        int damage =  (int)(charA.getDmgMin() +
                (charA.getDmgMax() - charA.getDmgMin()) * (chanceToHit - score) / chanceToHit);
        int damageResisted = charB.getDurability() / 10;
        switch (attackType){
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
}
