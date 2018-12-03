package viewIso.map;

import controller.isoView.isoMap.IsoMapBorderHoverController;
import javafx.geometry.Point2D;
import model.map.Map;
import model.map.MapPiece;
import model.map.heights.HeightGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static viewIso.map.MapDrawer.MAP_PIECE_SCREEN_SIZE_X;
import static viewIso.map.MapDrawer.MAP_PIECE_SCREEN_SIZE_Y;

public class MapDrawCalculator {

    static Map map;
    static MapDrawer mapDrawer;

    public static void setMapAndDrawer(Map map, MapDrawer mapDrawer) {
        MapDrawCalculator.map = map;
        MapDrawCalculator.mapDrawer = mapDrawer;
    }


    static java.util.List<Point> calcClosePoints(Point basePoint, int width, int height) {
        java.util.List<Point> closePoints = new ArrayList<>();
        int radius = Math.max(width / MAP_PIECE_SCREEN_SIZE_X, height / MAP_PIECE_SCREEN_SIZE_Y) + 3;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                Point point = new Point(basePoint.x + x, basePoint.y + y);
                if (isOnMap(point))
                    closePoints.add(point);
            }
        }
        return closePoints;
    }

    public static boolean mapOnScreen(){
        int bonus = IsoMapBorderHoverController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y + 30;
        boolean mapOnScreen = mapDrawer.getZeroScreenPosition().x < - relativeScreenPosition(new Point(0, map.getHeight())).x + bonus &&
                mapDrawer.getZeroScreenPosition().x > - relativeScreenPosition(new Point(map.getWidth(), 0)).x + mapDrawer.getCanvas().getWidth() - bonus &&
                mapDrawer.getZeroScreenPosition().y < - relativeScreenPosition(new Point(0, 0)).y + bonus + map.getPoints().get(new Point(0, 0)).getHeight() / HeightGenerator.H_PEX_PIX &&
                mapDrawer.getZeroScreenPosition().y > - relativeScreenPosition(new Point(map.getWidth(), map.getHeight())).y + mapDrawer.getCanvas().getHeight() - bonus + map.getPoints().get(new Point(map.mapXPoints - 1, map.mapYPoints - 1)).getHeight() / HeightGenerator.H_PEX_PIX;
        return mapOnScreen;
    }

    public static Point mapPointByClickPoint(Point clickPoint) {
        MapPiece clickedMapPiece = mapPieceByClickPoint(clickPoint);
        for (Point point: closePointsByPixPosition(clickPoint)) {
            if (map.getPoints().get(point) == clickedMapPiece)
                return point;
        }
        return null;
    }

    public static MapPiece mapPieceByClickPoint(Point clickPoint) {
        java.util.List<MapPiece> suspectedMapPieces = new ArrayList<>();
        for (Point point: closePointsByPixPosition(clickPoint)) {
            if (clickPoint.distance(screenPositionWithHeight(point)) < Math.max(MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y)) {
                suspectedMapPieces.add(map.getPoints().get(point));
            }
        }
        Point relClickPoint = new Point(clickPoint.x - mapDrawer.getZeroScreenPosition().x, clickPoint.y - mapDrawer.getZeroScreenPosition().y);
        for (MapPiece mapPiece: suspectedMapPieces) {
            if (mapPiece.isClicked(relClickPoint)) {
                return mapPiece;
            }
        }
        return null;
    }

    public static java.util.Map<MapPiece, Double> closeMapPiecesWithDistByMapPixPos(Point mapPixPos) {
        java.util.Map<MapPiece, Double> closeMapPiecesWithDist = new HashMap<>();
        for (Point point: closePointsByPixPosition(mapPixPos)) {
            double dist = mapPixPos.distance(relativeScreenPosition(point));
            if (dist < Math.max(MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y)) {
                closeMapPiecesWithDist.put(map.getPoints().get(point), dist);
            }
        }

        return closeMapPiecesWithDist;
    }

    private static List<Point> closePointsByPixPosition(Point pixPos) {
        final int RADIUS = 35;
        List<Point> closePoints = new ArrayList<>();

        Point mapPoint = closePointByPixPosition(pixPos);

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS ; y++) {
                Point point = new Point(mapPoint.x + x, mapPoint.y + y);
                if (isOnMap(point))
                    closePoints.add(point);
            }
        }

        return closePoints;
    }

    private static Point closePointByPixPosition (Point pixPos) {
        Point zero = mapDrawer.getZeroScreenPosition();
        return new Point(
                (pixPos.x - zero.x)/MAP_PIECE_SCREEN_SIZE_X + (pixPos.y - zero.y)/MAP_PIECE_SCREEN_SIZE_Y,
                (- pixPos.x + zero.x)/MAP_PIECE_SCREEN_SIZE_X + (pixPos.y - zero.y)/MAP_PIECE_SCREEN_SIZE_Y);
    }

    static Point screenPosition(Point point){
        return new Point(mapDrawer.getZeroScreenPosition().x + (point.x - point.y)* MAP_PIECE_SCREEN_SIZE_X /2,
                mapDrawer.getZeroScreenPosition().y + (point.x + point.y) * MAP_PIECE_SCREEN_SIZE_Y /2);
    }

    public static Point screenPositionWithHeight(Point point){
        if (!map.getPoints().keySet().contains(point))
            return null;
        return new Point(mapDrawer.getZeroScreenPosition().x + point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                mapDrawer.getZeroScreenPosition().y + point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2 -
                        map.getPoints().get(point).getHeight() / HeightGenerator.H_PEX_PIX);
    }

    public static Point screenPositionWithHeight(Point2D point2D){
        Point point = new Point(Math.round(Math.round(point2D.getX())), Math.round(Math.round(point2D.getY())));
        return new Point((int)(mapDrawer.getZeroScreenPosition().x + point2D.getX() * MAP_PIECE_SCREEN_SIZE_X /2 - point2D.getY() * MAP_PIECE_SCREEN_SIZE_X /2),
                (int)(mapDrawer.getZeroScreenPosition().y + point2D.getX() * MAP_PIECE_SCREEN_SIZE_Y /2 + point2D.getY() * MAP_PIECE_SCREEN_SIZE_Y /2 -
                        map.getPoints().get(point).getHeight() / HeightGenerator.H_PEX_PIX));
    }

    static Point relativeScreenPosition(Point point){
        return new Point(point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2);
    }

    public static java.util.List<Point> calcVisiblePoints(){
        List<Point> visiblePoints = new ArrayList<>();
        for (Point point: map.getPoints().keySet()) {
            if (isOnCanvas(screenPosition(point)))
                visiblePoints.add(point);
        }

        return visiblePoints;
    }

    public static boolean isOnMap(Point point) {
        return point.x >= 0 && point.x < map.mapXPoints && point.y >= 0 && point.y < map.mapYPoints;
    }

    private static boolean isOnCanvas(Point screenPoint){
        return screenPoint.x >= 0 && screenPoint.x <= mapDrawer.getCanvas().getWidth() + MAP_PIECE_SCREEN_SIZE_X &&
                screenPoint.y >= 0 + map.MIN_HEIGHT_PIX * 10 && screenPoint.y <= mapDrawer.getCanvas().getHeight() + map.MAX_HEIGHT_PIX * 10;
    }

}
