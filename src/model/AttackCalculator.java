package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.character.Character;
import model.map.Map;

public class AttackCalculator {

    public static boolean isInRange(Character charA, Character charB){
        if (charA.getPosition().distance(charB.getPosition()) * Map.RESOLUTION_M <
                charA.getDoubleRange() + charA.getType().getSize()/2 + charB.getType().getSize()/2)
            return true;
        return false;
    }

    public static double calcChanceToHit(Character charA, Character charB){
        return 50 + charA.getDoubleChanceToHit() - charB.getDoubleAgility() - 2*charA.getPosition().distance(charB.getPosition())*Map.RESOLUTION_M;
    }

    public static void attackCharacter(Character charA, Character charB, int score){
        int damage = calcDamage(charA, charB, score);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                charA.getName() + " attacks " + charB.getName() + " for " + damage + " damage!", ButtonType.OK);
        alert.showAndWait();
        charB.setHitPoints(charB.getDoubleHitPoints() - damage);
        charA.setMsLeft(charA.getMsLeft() - (int)(charA.getDoubleAttackDuration() * 1000));
        charA.setVigor(charA.getDoubleVigor() - (charA.getDoubleAttackDuration()));
    }

    public static Double calcDodgeChance(Character character){
        double dodgeChance = character.getDoubleAgility();
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
        double bounceChance = character.getDoubleAgility() + 10;
        if(character.isReady())
            bounceChance += 30;
        return bounceChance;
    }

    private static int calcDamage(Character charA, Character charB, int score){
        if (score > charB.getDoubleChanceToBeHit())
            return 0;
        int damage =  (int)(charA.getDoubleDmgMin() +
                (charA.getDoubleDmgMax() - charA.getDoubleDmgMin()) *
                (charB.getDoubleChanceToBeHit() - score) / 100);
        int damageResisted = (int) (charB.getDoubleDurability() / 10);
        if (score%10 < 2)
            damageResisted += (int)charB.getDoubleHeadArmor();
        else if (score%10 < 4)
            damageResisted += (int)charB.getDoubleLegsArmor();
        else if (score%10 < 6)
            damageResisted += (int)charB.getDoubleArmsArmor();
        else
            damageResisted += (int)charB.getDoubleBodyArmor();

        damage -= damageResisted;
        if (damage < 0)
            damage = 0;
        return damage;
    }
}
