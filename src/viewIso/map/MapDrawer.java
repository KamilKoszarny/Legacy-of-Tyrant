package viewIso.map;

import controller.isoView.IsoMapMoveController;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.map.heights.HeightGenerator;
import model.map.Map;
import model.map.MapPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapDrawer {

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final int MAP_PIECE_SCREEN_SIZE_X = 24;
    public static final int MAP_PIECE_SCREEN_SIZE_Y = 16;
    public static int PIX_PER_M;

    private Point zeroScreenPosition = new Point(600, -100);
    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapPieceDrawer mPDrawer;

    public MapDrawer(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        mPDrawer = new MapPieceDrawer(map, gc, this, MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y);
        PIX_PER_M = (int) ((MAP_PIECE_SCREEN_SIZE_X + MAP_PIECE_SCREEN_SIZE_Y) / 2 / Map.M_PER_POINT);
    }

    public void drawMap(){
        drawMapPoints(calcVisiblePoints());
    }

    public void drawMapPoints(List<Point> points) {
        for (Point point: points) {
            mPDrawer.drawMapPiece(point);
        }
    }

    public void clearPointAround(Point point, int width, int height) {
        drawMapPoints(calcClosePoints(point, width, height));
    }

    private List<Point> calcClosePoints(Point basePoint, int width, int height) {
        List<Point> closePoints = new ArrayList<>();
        int radius = Math.max(width / MAP_PIECE_SCREEN_SIZE_X, height / MAP_PIECE_SCREEN_SIZE_Y) + 1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                Point point = new Point(basePoint.x + x, basePoint.y + y);
                if (isOnMap(point))
                    closePoints.add(point);
            }
        }
        return closePoints;
    }

    public boolean mapOnScreen(){
        int bonus = IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;
        boolean mapOnScreen = zeroScreenPosition.x < - relativeScreenPosition(new Point(0, map.getHeight())).x + bonus &&
                zeroScreenPosition.x > - relativeScreenPosition(new Point(map.getWidth(), 0)).x + canvas.getWidth() - bonus &&
                zeroScreenPosition.y < - relativeScreenPosition(new Point(0, 0)).y + bonus + map.getPoints().get(new Point(0, 0)).getHeight() / HeightGenerator.H_PEX_PIX &&
                zeroScreenPosition.y > - relativeScreenPosition(new Point(map.getWidth(), map.getHeight())).y + canvas.getHeight() - bonus + map.getPoints().get(new Point(map.mapXPoints - 1, map.mapYPoints - 1)).getHeight() / HeightGenerator.H_PEX_PIX;
        return mapOnScreen;
    }

    public void clearMapBounds(){
        //dir 0: N-S, 1: E_W
        double[] xCoords = new double[8];
        double[] yCoords = new double[8];
        Point point;
        int moveStep = IsoMapMoveController.MAP_MOVE_STEP;

        point = new Point(0, 0);
        xCoords[0] = screenPosition(point).x;
        yCoords[0] = screenPosition(point).y - MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, 0);
        xCoords[1] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[1] = screenPosition(point).y - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[2] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[2] = screenPosition(point).y - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[3] = screenPosition(point).x;
        yCoords[3] = screenPosition(point).y - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(0, 0);
        xCoords[4] = screenPosition(point).x;
        yCoords[4] = screenPosition(point).y + MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(0, map.mapYPoints);
        xCoords[5] = screenPosition(point).x;
        yCoords[5] = screenPosition(point).y - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[6] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[6] = screenPosition(point).y - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[7] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[7] = screenPosition(point).y - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        gc.setFill(BACKGROUND_COLOR);
        gc.fillPolygon(xCoords, yCoords, 8);



        point = new Point(map.mapXPoints, map.mapYPoints);
        xCoords[0] = screenPosition(point).x;
        yCoords[0] = screenPosition(point).y - MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, 0);
        xCoords[1] = screenPosition(point).x;
        yCoords[1] = screenPosition(point).y - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[2] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[2] = screenPosition(point).y - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[3] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[3] = screenPosition(point).y - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, map.mapYPoints);
        xCoords[4] = screenPosition(point).x;
        yCoords[4] = screenPosition(point).y + MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y +
                50;

        point = new Point(0, map.mapYPoints);
        xCoords[5] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[5] = screenPosition(point).y - map.MIN_HEIGHT + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[6] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[6] = screenPosition(point).y - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[7] = screenPosition(point).x;
        yCoords[7] = screenPosition(point).y - map.MAX_HEIGHT - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        gc.setFill(BACKGROUND_COLOR);
        gc.fillPolygon(xCoords, yCoords, 8);
    }

    public void changeZeroScreenPosition(Point zSPChange) {
        this.zeroScreenPosition.x += zSPChange.x * MAP_PIECE_SCREEN_SIZE_X;
        this.zeroScreenPosition.y += zSPChange.y * MAP_PIECE_SCREEN_SIZE_Y;
    }


    Point getZeroScreenPosition() {
        return zeroScreenPosition;
    }

    public Map getMap() {
        return map;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public MapPieceDrawer getmPDrawer() {
        return mPDrawer;
    }


    public Point mapPointByClickPoint(Point clickPoint) {
        MapPiece clickedMapPiece = mapPieceByClickPoint(clickPoint);
        for (Point point: map.getPoints().keySet()) {
            if (map.getPoints().get(point) == clickedMapPiece)
                return point;
        }
        return null;
    }

    public MapPiece mapPieceByClickPoint(Point clickPoint) {
        List<MapPiece> suspectedMapPieces = new ArrayList<>();
        for (Point point: map.getPoints().keySet()) {
            if (clickPoint.distance(screenPositionWithHeight(point)) < Math.max(MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y)) {
                suspectedMapPieces.add(map.getPoints().get(point));
            }
        }
        Point relClickPoint = new Point(clickPoint.x - zeroScreenPosition.x, clickPoint.y - zeroScreenPosition.y);
        for (MapPiece mapPiece: suspectedMapPieces) {
            if (mapPiece.isClicked(relClickPoint)) {
                return mapPiece;
            }
        }
        return null;
    }


    private Point screenPosition(Point point){
        return new Point(zeroScreenPosition.x + point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                zeroScreenPosition.y + point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2);
    }

    public Point screenPositionWithHeight(Point point){
        return new Point(zeroScreenPosition.x + point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                zeroScreenPosition.y + point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2 -
                        map.getPoints().get(point).getHeight() / HeightGenerator.H_PEX_PIX);
    }

    public Point screenPositionWithHeight(Point2D point2D){
        Point point = new Point(Math.round(Math.round(point2D.getX())), Math.round(Math.round(point2D.getY())));
        return new Point((int)(zeroScreenPosition.x + point2D.getX() * MAP_PIECE_SCREEN_SIZE_X /2 - point2D.getY() * MAP_PIECE_SCREEN_SIZE_X /2),
                (int)(zeroScreenPosition.y + point2D.getX() * MAP_PIECE_SCREEN_SIZE_Y /2 + point2D.getY() * MAP_PIECE_SCREEN_SIZE_Y /2 -
                        map.getPoints().get(point).getHeight() / HeightGenerator.H_PEX_PIX));
    }

    Point relativeScreenPosition(Point point){
        return new Point(point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2);
    }

    public List<Point> calcVisiblePoints(){
        List<Point> visiblePoints = new ArrayList<>();
        for (Point point: map.getPoints().keySet()) {
            if (isOnCanvas(screenPosition(point)))
                visiblePoints.add(point);
        }

        return visiblePoints;
    }

    public boolean isOnMap(Point point) {
        return point.x >= 0 && point.x <= map.mapXPoints && point.y >= 0 && point.y <= map.mapYPoints;
    }

    private boolean isOnCanvas(Point screenPoint){
        return screenPoint.x >= 0 && screenPoint.x <= canvas.getWidth() + MAP_PIECE_SCREEN_SIZE_X &&
                screenPoint.y >= 0 + map.MIN_HEIGHT * 10 && screenPoint.y <= canvas.getHeight() + map.MAX_HEIGHT * 10;
    }
}
