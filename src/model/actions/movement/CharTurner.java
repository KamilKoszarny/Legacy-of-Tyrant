package model.actions.movement;

import javafx.geometry.Point2D;
import model.character.CharState;
import model.character.Character;
import model.map.lights.VisibilityCalculator;

import java.awt.*;

public class CharTurner {

    public static void turnStandingCharacter(Character character, Point turnPoint, boolean stop) {
        Point charPos = character.getPosition();
        double newDir = ((Math.atan2(turnPoint.y - charPos.y, turnPoint.x - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(newDir);
        if (stop) {
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.getStats().setSpeed(0);
        }
        VisibilityCalculator.setChange(true);


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
