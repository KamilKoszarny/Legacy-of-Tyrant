package isoview;

import helpers.my.DrawHelper;
import helpers.my.GeomerticHelper;
import isoview.map.MapDrawCalculator;
import isoview.map.MapDrawer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Battle;
import model.TurnsTracker;
import model.actions.movement.MoveCalculator;
import model.character.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PathDrawer {

    private PathDrawer() {
    }

    public static void showPathIfNotMoving(List<Point2D> path, Color color) {
        if (Battle.getChosenCharacter().getStats().getSpeed() == 0) {
            Battle.getChosenCharacter().setPath(path);
            if (path.size() > 0) {
                createPathView(Battle.getChosenCharacter(), color);
            }
        }
    }

    static void drawPaths(boolean fill) {
        for (Character character: Battle.getCharacters()) {
            List<Polygon> pathView = character.getPathView();
            if (pathView != null) {
                drawPath(pathView, fill);
                if (Battle.isTurnMode() && TurnsTracker.activeCharChosen()) {
                    drawPathAPCost(pathView, character.getPathAPCost());
                }
            }
        }
    }

    private static void drawPath(List<Polygon> pathView, boolean fill) {
        for (Polygon pathShape: pathView) {
            DrawHelper.drawPolygonOnCanvas(pathShape, fill, true);
        }
    }

    private static void drawPathAPCost(List<Polygon> pathView, double costAP) {
        if (costAP == 0)
            return;
        Polygon pathViewEndPolygon = pathView.get(pathView.size() - 1);
        Point2D pathViewEndPoint = new Point2D(pathViewEndPolygon.getPoints().get(0), pathViewEndPolygon.getPoints().get(1));
        DrawHelper.drawAPLabel(pathViewEndPoint, (int) costAP, 40, 6, true);
    }

    public static void createPathView(Character character, Color color) {
        List<Point2D> path = character.getPath();
        if (Battle.getPlayerCharacters().contains(character)) {
            createPathView(character, path, color);
            if(Battle.isTurnMode()) {
                character.setPathAPCost(MoveCalculator.calPathAPCost(character));
            }
        }
    }

    private static void createPathView(Character character, List<Point2D> path, Color color) {
        List<Point2D> pointsOnPath = GeomerticHelper.pointsOnPath(path, 2);

        List<Point> screenPoints = new ArrayList<>();
        for (Point2D pointOnPath: pointsOnPath) {
            screenPoints.add(MapDrawCalculator.relativeScreenPositionWithHeight(pointOnPath));
        }

        List<Polygon> pathShapes = new ArrayList<>();
        for (int i = 0; i < screenPoints.size() - 1; i++) {
            Point screenPoint = screenPoints.get(i);
            Point nextScreenPoint = screenPoints.get(i + 1);
            Polygon triangle = createPathTriangle(screenPoint, nextScreenPoint);
            triangle.setFill(color);
            triangle.setStroke(color);
            pathShapes.add(triangle);
        }

        Point2D destination = path.get(path.size() - 1);
        Polygon cross = createCross(MapDrawCalculator.relativeScreenPositionWithHeight(destination));
        cross.setFill(color);
        cross.setStroke(color);
        pathShapes.add(cross);

        character.setPathView(pathShapes);
    }

    private static Polygon createPathTriangle(Point point, Point nextPoint) {
        double dX = nextPoint.x - point.x;
        double dY = nextPoint.y - point.y;

        Polygon triangle = new Polygon();
        int a = 4, b = 6;
        triangle.getPoints().addAll(
                point.x + dX/a, point.y + dY/a,
                point.x - dX/b + dY/b, point.y - dY/b - dX/b,
                point.x - dX/b - dY/b, point.y - dY/b + dX/b);
        return triangle;
    }

    private static Polygon createCross(Point point) {
        Polygon cross = new Polygon();

        double px = point.x;
        double py = point.y;

        double x = MapDrawer.MAP_PIECE_SCREEN_SIZE_X / 4.;
        double y = MapDrawer.MAP_PIECE_SCREEN_SIZE_Y / 4.;
        cross.getPoints().addAll(
                px, py - y,
                px + 2*x, py - 3*y,
                px + 3*x, py - 2*y,
                px + x, py,
                px + 3*x, py + 2*y,
                px + 2*x, py + 3*y,
                px, py + y,
                px - 2*x, py + 3*y,
                px - 3*x, py +2*y,
                px - x, py,
                px - 3*x, py - 2*y,
                px - 2*x, py - 3*y
        );
        return cross;
    }


}
