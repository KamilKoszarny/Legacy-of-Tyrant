package model.ai;

import model.actions.movement.CharTurner;
import model.character.Character;

public class EnemyFinder {

    public static void turnAround(Character aiCharacter) {
        double newDirection = (aiCharacter.getPreciseDirection() + 1) % 8;
        CharTurner.turnStandingCharacter(aiCharacter, newDirection);
    }
}
