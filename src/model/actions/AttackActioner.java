package model.actions;

import model.actions.movement.CharTurner;
import model.character.CharState;
import model.character.Character;

public class AttackActioner {

    public static void attackCharacter(Character attacker, Character victim, AttackType attackType){
        CharTurner.turnCharacter(attacker, victim.getPosition(), true);
        attacker.setState(CharState.ATTACK);
        AttackCalculator.attackCharacter(attacker, victim, attackType);
    }
}
