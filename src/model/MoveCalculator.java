package model;

import model.character.Character;
import model.map.Map;

import java.awt.*;

public class MoveCalculator {

    public static void moveCharacter(Character character, Point destination) {
        Point start = character.getPosition();

        double xFactor, yFactor, sum;
        double speed = character.getCurrentSA().speed / Map.RESOLUTION_M;

        sum = Math.abs(destination.x - start.x) + Math.abs(destination.y - start.y);
        xFactor = (destination.x - start.x) / sum;
        yFactor = (destination.y - start.y) / sum;

        Point stop = new Point();
        stop.x = start.x + (int) (xFactor * speed * character.getMsLeft() / 1000);
        stop.y = start.y + (int) (yFactor * speed * character.getMsLeft() / 1000);

//        System.out.println(xFactor + " " + yFactor);

        if (start.distance(stop) > start.distance(destination)) {
            character.setPosition(destination);
            character.setMsLeft(character.getMsLeft() - (int) (start.distance(destination) / speed * 1000));
            System.out.println(character.getMsLeft());
        } else {
            character.setPosition(stop);
            character.setMsLeft(0);
        }
    }
}
