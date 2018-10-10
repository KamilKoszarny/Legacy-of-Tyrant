package model.character.movement;

import model.character.Character;
import model.map.Map;
import viewIso.MapDrawer;

import java.awt.*;

public class CharTurner {

    public static void turnCharacter(Character character, Point turnPoint) {
        Point charPos = character.getPosition();
        int newDir = Math.round (Math.round(Math.atan2(turnPoint.y - charPos.y, turnPoint.x - charPos.x) * 8 / 2. / Math.PI) - 1)%8;
        character.setDirection(newDir);
    }

}
