package model;

import model.character.Character;
import model.map.visibility.VisibilityChecker;

public class TurnModeActivator {

    static boolean shouldStart() {
        if (Battle.isTurnMode())
            return false;
        for (Character aliveEnemy : Battle.getEnemyAliveCharacters()) {
            if (VisibilityChecker.isInPlayerCharsView(aliveEnemy.getPosition()))
                return true;
        }
        for (Character alivePlayerChar: Battle.getPlayerAliveCharacters()) {
            if (alivePlayerChar.getAttackResult() != null)
                return true;
        }
        return false;
    }

    public static boolean shouldFinish() {
        return false;
    }
}
