package model.actions.movement;

import javafx.geometry.Point2D;
import model.character.CharState;
import model.character.Character;

import java.awt.*;

public class CharTurner {

    public static void turnCharacter(Character character, Point turnPoint, boolean stop) {
        Point charPos = character.getPosition();
        double newDir = ((Math.atan2(turnPoint.y - charPos.y, turnPoint.x - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(newDir);
        if (stop) {
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.getStats().setSpeed(0);
        }
    }

    public static void turnCharacter(Character character, Point2D turnPoint, boolean stop) {
        Point charPos = character.getPosition();
        double newDir = ((Math.atan2(turnPoint.getY() - charPos.y, turnPoint.getX() - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(newDir);
        if (stop) {
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.getStats().setSpeed(0);
        }
    }
}
