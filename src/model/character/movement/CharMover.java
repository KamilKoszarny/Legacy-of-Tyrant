package model.character.movement;

import helpers.GeomerticHelper;
import javafx.geometry.Point2D;
import model.character.CharState;
import model.character.Character;
import model.map.Map;
import viewIso.characters.CharDrawer;

import java.awt.*;

public class CharMover {

    private static double POINTS_TO_NEXT_FRAME = 1.6;

    public static void startRunCharacter(Character character, Point movePoint) {
        CharTurner.turnCharacter(character, movePoint);
        character.setState(CharState.RUN);
        Point charPos = character.getPosition();
        double moveDir = ((Math.atan2(movePoint.y - charPos.y, movePoint.x - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(moveDir);
        character.setCurrentSpeed(character.getDoubleSpeed() * 2);
        character.setDestination(movePoint);
    }

    public static void updateCharacterMove(Character character, int ms) {
        Point2D pos = character.getPrecisePosition();
        double distToDestination = pos.distance(new Point2D(character.getDestination().x, character.getDestination().y));
        double changeX, changeY;
        changeX = Math.cos(((character.getPreciseDirection() + 1) / 8) * 2 * Math.PI) *
                character.getCurrentSpeed() * ms/1000 / Map.M_PER_POINT;
        changeY = Math.sin(((character.getPreciseDirection() + 1) / 8) * 2 * Math.PI) *
                character.getCurrentSpeed() * ms/1000 / Map.M_PER_POINT;

        if (distToDestination >= GeomerticHelper.distTo0(changeX, changeY)) {
            character.setPosition(new Point2D(pos.getX() + changeX, pos.getY() + changeY));
            if (distToDestination%POINTS_TO_NEXT_FRAME <
                    GeomerticHelper.distTo0(changeX, changeY))
                CharDrawer.nextFrame(character);
        }
        else {
            character.setPosition(character.getDestination());
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.setCurrentSpeed(0);
        }
    }


}
