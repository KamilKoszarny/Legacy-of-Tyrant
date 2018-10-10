package model.character.movement;

import model.character.CharState;
import model.character.Character;

import java.awt.*;

public class CharMover {

    public static void startRunCharacter(Character character, Point movePoint) {
        CharTurner.turnCharacter(character, movePoint);
        character.setState(CharState.RUN);
        Point charPos = character.getPosition();
        double moveDir = Math.round (Math.round(Math.atan2(movePoint.y - charPos.y, movePoint.x - charPos.x) * 8 / 2. / Math.PI) - 1)%8;
        character.setMoveDirection(moveDir);
        character.setCurrentSpeed(character.getDoubleSpeed() * 2);
        character.setDestination(movePoint);
    }

    public static void updateCharacterMove(Character character, int ms) {
        Point pos = character.getPosition();
//        double changeX
    }
}
