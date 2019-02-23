package model.ai;

import model.BattleEvent;
import model.EventType;
import model.actions.ActionQueuer;
import model.actions.movement.CharTurner;
import model.character.CharState;
import model.character.Character;

public class EnemyFinder {

    private static final int MAX_TURNS = 80;

    static void scheduleFinding(Character aiCharacter) {
        ActionQueuer.clearEventQueue(aiCharacter);
        for (int turns = 0; turns < MAX_TURNS; turns++) {
            ActionQueuer.addEvent(aiCharacter, new BattleEvent(EventType.LOOK4ENEMY));
        }
    }

    public static void turnAround(Character aiCharacter) {
        aiCharacter.setState(CharState.IDLE);
        double newDirection = (aiCharacter.getPreciseDirection() + 8. / MAX_TURNS) % 8;
        CharTurner.turnStandingCharacter(aiCharacter, newDirection);
    }
}
