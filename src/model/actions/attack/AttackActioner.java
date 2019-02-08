package model.actions.attack;

import model.actions.movement.CharTurner;
import model.character.CharState;
import model.character.Character;
import model.items.weapon.WeaponGroup;

import java.util.Random;

public class AttackActioner {

    public static void attackCharacter(Character attacker, Character victim, BodyPart bodyPart){
        AttackResult result;
        CharTurner.turnStandingCharacter(attacker, victim.getPosition(), true);
        if (WeaponGroup.RANGE.getWeapons().contains(attacker.getItems().getWeapon()))
            attacker.setState(CharState.ATTACK_RANGE);
        else
            attacker.setState(CharState.ATTACK);
        victim.setState(CharState.HIT);
        int score = new Random().nextInt(100);
        int chanceToHit = AttackCalculator.calcChanceToHit(attacker, victim);
        if (score > chanceToHit) {
            result = new AttackResult(AttackResultType.MISS);
        } else {
            bodyPart = AttackCalculator.calcBodyPartHit(bodyPart);
            if (bodyPart == null)
                result = new AttackResult(AttackResultType.MISS);
            else {
                int damage = AttackCalculator.calcDamage(attacker, victim, score, chanceToHit, bodyPart);
                AttackCalculator.updateStats(attacker, victim, damage);
                result = new AttackResult(AttackResultType.HIT, damage);
            }
        }
        victim.setAttackResult(result);
    }
}
