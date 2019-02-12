package model.actions.movement;

import javafx.geometry.Point2D;
import model.actions.attack.AttackCalculator;
import model.character.Character;
import viewIso.PathDrawer;

import java.awt.*;
import java.util.List;

public class Char2CharMover {

    private static final int GIVE_DIST = 4;
    private static final int THROW_BASE_DIST = 15;
    private static final int SPEAK_DIST = 8;

    public static void calcPathAndStartRunToChar(Character character, Character targetChar) {
        Point targetCharPos = targetChar.getPosition();
        List<Point2D> path = PathCalculator.findPathToObject(character, targetCharPos, 3);
        if (path != null) {
            character.setPath(path);
            PathDrawer.createPathView(character, targetChar.getColor());
        }
        CharMover.startMove(character);
    }

    public static boolean pathToCharExists(Character character, Character targetChar) {
        Point targetCharPos = targetChar.getPosition();
        if (!AttackCalculator.isInRange(character, targetChar)) {
            List<Point2D> path = PathCalculator.findPathToObject(character, targetCharPos, 3);
            if (path != null && !path.isEmpty()) {
                PathDrawer.showPathIfNotMoving(path, targetChar.getColor());
                return true;
            }
        }
        return false;
    }
}
