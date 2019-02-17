package model.ai;

import model.actions.ActionQueuer;
import model.actions.attack.AttackActioner;
import model.actions.attack.BodyPart;
import model.actions.movement.CharTurner;
import model.character.CharState;
import model.character.Character;
import viewIso.characters.CharsDrawer;

public class AIActioner {

    private static final int MAX_TURNS = 8;

    private static int turns = 0;

    public static void nextAIAction(Character aiCharacter) {
        Character enemy = EnemyChooser.chooseVisibleEnemy(aiCharacter);
        if (enemy != null)
            AttackActioner.goAndAttack(aiCharacter, enemy, BodyPart.BODY);
        else
            turnAround(aiCharacter);
    }

    private static void turnAround(Character aiCharacter) {
        if (turns < MAX_TURNS) {
            double newDirection = (aiCharacter.getPreciseDirection() + 1) % 8;
            CharTurner.turnStandingCharacter(aiCharacter, newDirection);
            turns++;
        }
    }

    public static boolean actionFinished(Character character) {
        return (character.getStats().getActionPoints() <= 0 && CharsDrawer.animationFinished(character)) ||
                (ActionQueuer.noMoreActions(character) && character.getState().equals(CharState.IDLE));
    }

    public static void reset() {
        AIActioner.turns = 0;
    }
}