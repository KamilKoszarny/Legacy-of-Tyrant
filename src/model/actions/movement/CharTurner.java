package model.actions.movement;

import javafx.geometry.Point2D;
import model.character.CharState;
import model.character.Character;
import model.character.Stats;
import model.map.visibility.VisibilityCalculator;

import java.awt.*;

public class CharTurner {

    public static void turnStandingCharacter(Character character, Point turnPoint, boolean stop) {
        Point charPos = character.getPosition();
        double dirBefore = character.getPreciseDirection();
        double newDir = ((Math.atan2(turnPoint.y - charPos.y, turnPoint.x - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(newDir);
        if (stop) {
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.getStats().setSpeed(0);
        }
        VisibilityCalculator.setChange(true);

        updateVigorAndActionPoints(character, dirBefore, newDir);
    }

    public static void turnStandingCharacter(Character character, double newDir) {
        double dirBefore = character.getPreciseDirection();
        character.setPreciseDirection(newDir);
        VisibilityCalculator.setChange(true);

        updateVigorAndActionPoints(character, dirBefore, newDir);
    }

    private static void updateVigorAndActionPoints(Character character, double dirBefore, double dirAfter) {
        final double AP_PER_TURN = 10, VIGOR_PER_TURN = .3;
        Stats stats = character.getStats();
        double turnRatio = Math.abs(dirAfter - dirBefore) / 8;
        if (turnRatio > .5)
            turnRatio = 1 - turnRatio;
        double costAP = AP_PER_TURN * turnRatio / stats.getSpeedMax();
        double costVigor = VIGOR_PER_TURN * turnRatio / stats.getSpeedMax();

        stats.subtractActionPoints(costAP);
        stats.subtractVigor(costVigor);
    }

    static void turnMovingChar(Character character, Point2D next) {
        turnCharacterPrecisely(character, next, false);
        Point charPos = character.getPosition();
        double moveDir = ((Math.atan2(next.getY() - charPos.y, next.getX() - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(moveDir);
    }

    private static void turnCharacterPrecisely(Character character, Point2D turnPoint, boolean stop) {
        Point charPos = character.getPosition();
        double newDir = ((Math.atan2(turnPoint.getY() - charPos.y, turnPoint.getX() - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(newDir);
        if (stop) {
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.getStats().setSpeed(0);
        }
        VisibilityCalculator.setChange(true);
    }
}
