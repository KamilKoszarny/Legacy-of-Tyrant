package model.actions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.character.Character;
import model.map.Map;

import java.util.Random;

public class AttackCalculator {

    public static boolean isInRange(Character charA, Character charB){
        System.out.println(charA.getPosition().distance(charB.getPosition()) * Map.M_PER_POINT);
//        if (charA.getPosition().distance(charB.getPosition()) * Map.RESOLUTION_M <
//                charA.getRange() + charA.getType().getSize()/2 + charB.getType().getSize()/2)
        if (charA.getPosition().distance(charB.getPosition()) * Map.M_PER_POINT < charA.getRange())
            return true;
        return false;
    }

    public static AttackResult attackCharacter(Character charA, Character charB, AttackType attackType){
        int score = new Random().nextInt(100);
        int chanceToHit = calcChanceToHit(charA, charB);
        if (score > chanceToHit)
            return AttackResult.MISS;

        int damage = calcDamage(charA, charB, score, attackType);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                charA.getName() + " attacks " + charB.getName() + " for " + damage + " damage!", ButtonType.OK);
        alert.show();
        charB.setHitPoints(charB.getHitPoints() - damage);
        charA.setMsLeft(charA.getMsLeft() - (int)(1 / charA.getAttackSpeed() * 1000));
        charA.setVigor((int) (charA.getVigor() - (1 / charA.getAttackSpeed())));
        return AttackResult.HIT;
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

    private static int calcDamage(Character charA, Character charB, int score, AttackType attackType){
        if (score > charB.getAvoidance())
            return 0;
        int damage =  (int)(charA.getDmgMin() +
                (charA.getDmgMax() - charA.getDmgMin()) *
                (charB.getAvoidance() - score) / charB.getAvoidance());
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
