package model.actions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.character.Character;
import model.map.Map;

public class AttackCalculator {

    public static boolean isInRange(Character charA, Character charB){
        if (charA.getPosition().distance(charB.getPosition()) * Map.RESOLUTION_M <
                charA.getRange() + charA.getType().getSize()/2 + charB.getType().getSize()/2)
            return true;
        return false;
    }

    public static double calcChanceToHit(Character charA, Character charB){
        return 50 + charA.getAccuracy() - charB.getAgility() - 2*charA.getPosition().distance(charB.getPosition())*Map.RESOLUTION_M;
    }

    public static void attackCharacter(Character charA, Character charB, int score){
        int damage = calcDamage(charA, charB, score);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                charA.getName() + " attacks " + charB.getName() + " for " + damage + " damage!", ButtonType.OK);
        alert.showAndWait();
        charB.setHitPoints(charB.getHitPoints() - damage);
        charA.setMsLeft(charA.getMsLeft() - (int)(1 / charA.getAttackSpeed() * 1000));
        charA.setVigor((int) (charA.getVigor() - (1 / charA.getAttackSpeed())));
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

    private static int calcDamage(Character charA, Character charB, int score){
        if (score > charB.getAvoidance())
            return 0;
        int damage =  (int)(charA.getDmgMin() +
                (charA.getDmgMax() - charA.getDmgMin()) *
                (charB.getAvoidance() - score) / charB.getAvoidance());
        int damageResisted = (int) (charB.getDurability() / 10);
        if (score%10 < 2)
            damageResisted += (int)charB.getHeadArmor() - 1;
        else if (score%10 < 4)
            damageResisted += (int)charB.getLegsArmor();
        else if (score%10 < 6)
            damageResisted += (int)charB.getArmsArmor();
        else
            damageResisted += (int)charB.getBodyArmor();

        damage -= damageResisted;
        if (damage < 0)
            damage = 0;
        return damage;
    }
}
