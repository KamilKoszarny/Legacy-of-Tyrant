package model.actions.movement;

import isoview.PathDrawer;
import javafx.geometry.Point2D;
import model.actions.attack.AttackCalculator;
import model.character.Character;
import model.map.Map;

import java.awt.*;
import java.util.List;

public class Char2CharMover {

    private static final int GIVE_DIST = 4;
    private static final int THROW_BASE_DIST = 15;
    private static final int SPEAK_DIST = 8;

    public static void calcPathAndStartRunToChar(Character character, Character targetChar) {
        if (AttackCalculator.isInRange(character, targetChar))
            return;
        Point targetCharPos = targetChar.getPosition();
        Point attackInRangePoint = calcAttackInRangePoint(character, targetChar);
        List<Point2D> path = PathCalculator.findPathToObject(character, attackInRangePoint, 3);
        if (path != null && !path.isEmpty()) {
            character.setPath(path);
            PathDrawer.createPathView(character, targetChar.getColor());
        } else {
            path = PathCalculator.findPathToObject(character, targetCharPos, 3);
            if (path != null) {
                character.setPath(path);
                if (!calcAttackInRangePoint(character, targetChar).equals(character.getPosition())) {
                    PathDrawer.createPathView(character, targetChar.getColor());
                }
            }
        }
        CharMover.startMove(character);
    }

    public static boolean drawPathToCharIfExists(Character character, Character targetChar) {
        if (AttackCalculator.isInRange(character, targetChar))
            return false;
        Point targetCharPos = targetChar.getPosition();
        Point attackInRangePoint = calcAttackInRangePoint(character, targetChar);
        List<Point2D> path = PathCalculator.findPathToObject(character, attackInRangePoint, 3);
        if (path != null) {
            if (!path.isEmpty()) {
                PathDrawer.showPathIfNotMoving(path, targetChar.getColor());
            }
            return true;
        }
        path = PathCalculator.findPathToObject(character, targetCharPos, 3);
        if (path != null && !path.isEmpty()) {
            PathDrawer.showPathIfNotMoving(path, targetChar.getColor());
            return true;
        }
        return false;
    }

    private static Point calcAttackInRangePoint(Character character, Character targetChar) {
        double rangeInP = character.getItems().getWeapon().getRange() / Map.M_PER_POINT;
        double dist = character.getPosition().distance(targetChar.getPosition());
        if (dist < rangeInP)
            return character.getPosition();
        Point2D unitVectorTargetToChar = new Point2D(
                (character.getPosition().x - targetChar.getPosition().x) / dist,
                (character.getPosition().y - targetChar.getPosition().y) / dist);
        return new Point(
                (int)(targetChar.getPosition().x + (rangeInP - 1) * unitVectorTargetToChar.getX()),
                (int)(targetChar.getPosition().y + (rangeInP - 1) * unitVectorTargetToChar.getY()));
    }
}
