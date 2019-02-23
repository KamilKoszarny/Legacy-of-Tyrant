package model.actions.attack;

import model.BattleEvent;
import model.EventType;
import model.actions.ActionQueuer;
import model.actions.movement.CharTurner;
import model.character.CharState;
import model.character.Character;
import model.items.weapon.WeaponGroup;

public class AttackActioner {

    public static void scheduleGoAndAttack(Character attacker, Character victim, BodyPart bodyPart) {
        attacker.setRunning(true);
        ActionQueuer.clearEventQueue(attacker);
        ActionQueuer.addEvent(attacker, new BattleEvent(EventType.GO2ENEMY, victim));
        ActionQueuer.addEvent(attacker, new BattleEvent(EventType.ATTACK, victim, bodyPart));
    }

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
