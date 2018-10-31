package model.character.movement;

import model.character.CharState;
import model.character.Character;

import java.awt.*;

public class CharTurner {

    public static void turnCharacter(Character character, Point turnPoint) {
        Point charPos = character.getPosition();
        double newDir = ((Math.atan2(turnPoint.y - charPos.y, turnPoint.x - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(newDir);
        character.setDestination(null);
        character.setState(CharState.IDLE);
        character.setCurrentSpeed(0);
    }

}
