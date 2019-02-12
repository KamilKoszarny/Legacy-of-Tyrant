package model.actions.attack;

import model.actions.movement.CharTurner;
import model.character.CharState;
import model.character.Character;
import model.items.weapon.WeaponGroup;

public class AttackActioner {

    public static void attackCharacter(Character attacker, Character victim, BodyPart bodyPart){
        CharTurner.turnStandingCharacter(attacker, victim.getPosition(), true);
        setStates(attacker, victim);
        AttackResult result = AttackCalculator.calcAttackResult(attacker, victim, bodyPart);
        victim.setAttackResult(result);
        AttackCalculator.updateVictimStats(victim);
        AttackCalculator.updateAttackerStats(attacker);

    }

    private static void setStates(Character attacker, Character victim) {
        if (WeaponGroup.RANGE.getWeapons().contains(attacker.getItems().getWeapon()))
            attacker.setState(CharState.ATTACK_RANGE);
        else
            attacker.setState(CharState.ATTACK);
        victim.setState(CharState.HIT);
    }
}
