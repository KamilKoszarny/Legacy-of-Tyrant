package model.actions.movement;

import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import model.Battle;
import model.character.Character;
import model.map.GridGrapCalculator;
import model.map.mapObjects.MapObjectType;
import viewIso.PathDrawer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PathCalculator {

    public static List<Point2D> calcAndDrawPath(Point mapPoint) {
        List<Point2D> path = calcPath(Battle.getChosenCharacter(), mapPoint);
        PathDrawer.showPathIfNotMoving(path);
        return path;
    }

    public static List<Point2D> calcPath(Character character, Point movePoint) {
        if (!Battle.getMap().getPoints().get(movePoint).isWalkable())
            return new ArrayList<>();
        GridGrapCalculator.regenerateGridGraph(Battle.getMap(), Battle.getMap().getPoints().keySet(), Battle.getCharacters());
        return calcPathOnCurrentGrid(character, movePoint);
    }

    public static List<Point2D> calcPathOnCurrentGrid(Character character, Point movePoint) {
        GridGrapCalculator.clearGridGraphForChar(character);
        List<Point2D> path = PathFinder.calcPath(character.getPrecisePosition(), movePoint, true);
        return path;
    }

    public static List<Point2D> findPathToObject(Point objectPoint, MapObjectType objectType) {
        clearGridAroundObject(objectPoint, objectType);
        List<Point2D> path = calcPathOnCurrentGrid(Battle.getChosenCharacter(), objectPoint);
        regenerateGridAroundObject(objectPoint, objectType);

        if (path.size() > 0) {
            return path;
        }
        return null;

//        final Point[] POINT_OFFSETS = new Point[]{
//                new Point(0, 0), new Point(0, -1), new Point(0, 1), new Point(-1, 0), new Point(1, 0),
//                new Point(0, 0), new Point(0, -2), new Point(0, 2), new Point(-2, 0), new Point(2, 0)};
//
//        for (int i = 0; i < POINT_OFFSETS.length; i++) {
//            Point nearObjectPoint = new Point(objectPoint.x + POINT_OFFSETS[i].x, objectPoint.y + POINT_OFFSETS[i].y);
//            List<Point2D> path = calcPath(Battle.getChosenCharacter(), nearObjectPoint);
//            if (path.size() > 0) {
//                return path;
//            }
//        }
    }

    private static void clearGridAroundObject(Point objectPoint, MapObjectType objectType) {
        //TODO: radius by type
        List<Point> pointsAround = GeomerticHelper.pointsInRadius(objectPoint, 2, Battle.getMap());
        GridGrapCalculator.clearGridGraphForPoints(pointsAround, false);
    }

    private static void regenerateGridAroundObject(Point objectPoint, MapObjectType objectType) {
        //TODO: radius by type
        List<Point> pointsAround = GeomerticHelper.pointsInRadius(objectPoint, 2, Battle.getMap());
        GridGrapCalculator.regenerateGridGraph(pointsAround);
    }
}