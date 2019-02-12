package model.actions.movement;

import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import model.Battle;
import model.character.Character;
import model.map.GridGraphCalculator;
import viewIso.PathDrawer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PathCalculator {

    public static List<Point2D> calcAndDrawPath(Point mapPoint) {
        List<Point2D> path = calcPath(Battle.getChosenCharacter(), mapPoint);
        PathDrawer.showPathIfNotMoving(path, Battle.getChosenCharacter().getColor());
        return path;
    }

    public static List<Point2D> calcPath(Character character, Point movePoint) {
        if (!Battle.getMap().getPoints().get(movePoint).isWalkable())
            return new ArrayList<>();
        GridGraphCalculator.regenerateGridGraph(Battle.getMap(), Battle.getMap().getPoints().keySet(), Battle.getCharacters());
        return calcPathOnCurrentGrid(character, movePoint);
    }

    public static List<Point2D> calcPathOnCurrentGrid(Character character, Point movePoint) {
        GridGraphCalculator.clearGridGraphForChar(character);
        List<Point2D> path = PathFinder.calcPath(character.getPrecisePosition(), movePoint, true);
        return path;
    }

    public static List<Point2D> findPathToObject(Character character, Point objectPoint, int clearRadius) {
        clearGridAroundPoint(objectPoint, clearRadius);
        List<Point2D> path = calcPathOnCurrentGrid(character, objectPoint);
        regenerateGridAroundPoint(objectPoint, clearRadius);

        if (path.size() > 0) {
            return path;
        }
        return null;
    }

    private static void clearGridAroundPoint(Point objectPoint, int radius) {
        //TODO: radius by type
        List<Point> pointsAround = GeomerticHelper.pointsInRadius(objectPoint, radius, Battle.getMap());
        GridGraphCalculator.clearGridGraphForPoints(pointsAround, false);
    }

    private static void regenerateGridAroundPoint(Point objectPoint, int radius) {
        //TODO: radius by type
        List<Point> pointsAround = GeomerticHelper.pointsInRadius(objectPoint, radius, Battle.getMap());
        GridGraphCalculator.regenerateGridGraph(pointsAround);
    }
}