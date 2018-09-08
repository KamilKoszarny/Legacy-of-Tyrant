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

    public static double calcChaceToHit(Character charA, Character charB){
        return 34.;
    }

    public static void attackCharacter(Character charA, Character charB, int score){
        int damage = calcDamage(charA, charB, score);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                charA.getName() + " attacks " + charB.getName() + " for " + damage + " damage!", ButtonType.OK);
        alert.showAndWait();
        charB.setHitPoints(charB.getDoubleHitPoints() - damage);
    }

    public static int calcDamage(Character charA, Character charB, int score){
        if (score > charB.getDoubleChanceToHit())
            return 0;
        return (int)(charA.getDoubleDmgMin() +
                (charA.getDoubleDmgMax() - charA.getDoubleDmgMin()) *
                (charB.getDoubleChanceToHit() - score) / 100);

    }
}
