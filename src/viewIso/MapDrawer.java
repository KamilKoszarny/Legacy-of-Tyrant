package viewIso;

import controller.isoView.IsoMapMoveController;
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
    private final int MAP_PIECE_SCREEN_SIZE_X = 24;
    private final int MAP_PIECE_SCREEN_SIZE_Y = 16;

    private Point zeroScreenPosition = new Point(600, -100);
    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapPieceDrawer mPDrawer;

    MapDrawer(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        mPDrawer = new MapPieceDrawer(map, gc, this, MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y);
    }

    public void drawMap(){
        for (Point point: calcVisiblePoints()) {
            mPDrawer.drawMapPiece(point);
        }
    }

    public boolean mapOnScreen(){
        int bonus = IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;
        boolean mapOnScreen = zeroScreenPosition.x < - relativeScreenPosition(new Point(0, map.getHeight())).x + bonus &&
                zeroScreenPosition.x > - relativeScreenPosition(new Point(map.getWidth(), 0)).x + canvas.getWidth() - bonus &&
                zeroScreenPosition.y < - relativeScreenPosition(new Point(0, 0)).y + bonus + map.MAX_HEIGHT &&
                zeroScreenPosition.y > - relativeScreenPosition(new Point(map.getWidth(), map.getHeight())).y + canvas.getHeight() - bonus + map.MIN_HEIGHT;
        return mapOnScreen;
    }

    public void clearMapBounds(){
        //dir 0: N-S, 1: E_W
        double[] xCoords = new double[8];
        double[] yCoords = new double[8];
        Point point;

        point = new Point(0, 0);
        xCoords[0] = screenPosition(point).x;
        yCoords[0] = screenPosition(point).y - MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, 0);
        xCoords[1] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[1] = screenPosition(point).y - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[2] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[2] = screenPosition(point).y - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[3] = screenPosition(point).x;
        yCoords[3] = screenPosition(point).y - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(0, 0);
        xCoords[4] = screenPosition(point).x;
        yCoords[4] = screenPosition(point).y + MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(0, map.mapYPoints);
        xCoords[5] = screenPosition(point).x;
        yCoords[5] = screenPosition(point).y - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[6] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[6] = screenPosition(point).y - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[7] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[7] = screenPosition(point).y - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        gc.setFill(BACKGROUND_COLOR);
        gc.fillPolygon(xCoords, yCoords, 8);



        point = new Point(map.mapXPoints, map.mapYPoints);
        xCoords[0] = screenPosition(point).x;
        yCoords[0] = screenPosition(point).y - MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, 0);
        xCoords[1] = screenPosition(point).x;
        yCoords[1] = screenPosition(point).y - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[2] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[2] = screenPosition(point).y - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[3] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[3] = screenPosition(point).y - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, map.mapYPoints);
        xCoords[4] = screenPosition(point).x;
        yCoords[4] = screenPosition(point).y + MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y +
                50;

        point = new Point(0, map.mapYPoints);
        xCoords[5] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[5] = screenPosition(point).y - map.MIN_HEIGHT + IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[6] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[6] = screenPosition(point).y - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[7] = screenPosition(point).x;
        yCoords[7] = screenPosition(point).y - map.MAX_HEIGHT - IsoMapMoveController.MAP_MOVE_STEP * MAP_PIECE_SCREEN_SIZE_Y;

        gc.setFill(BACKGROUND_COLOR);
        gc.fillPolygon(xCoords, yCoords, 8);
    }

    public void changeZeroScreenPosition(Point zSPChange) {
        this.zeroScreenPosition.x += zSPChange.x * MAP_PIECE_SCREEN_SIZE_X;
        this.zeroScreenPosition.y += zSPChange.y * MAP_PIECE_SCREEN_SIZE_Y;
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

    private Point screenPositionWithHeight(Point point){
        return new Point(zeroScreenPosition.x + point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                zeroScreenPosition.y + point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2 -
                        map.getPoints().get(point).getHeight() / HeightGenerator.H_PEX_PIX);
    }

    Point relativeScreenPosition(Point point){
        return new Point(point.x * MAP_PIECE_SCREEN_SIZE_X /2 - point.y * MAP_PIECE_SCREEN_SIZE_X /2,
                point.x * MAP_PIECE_SCREEN_SIZE_Y /2 + point.y * MAP_PIECE_SCREEN_SIZE_Y /2);
    }

    List<Point> calcVisiblePoints(){
        List<Point> visiblePoints = new ArrayList<>();
        for (Point point: map.getPoints().keySet()) {
            if (isOnCanvas(screenPosition(point)))
                visiblePoints.add(point);
        }

        return visiblePoints;
    }

    private boolean isOnCanvas(Point screenPoint){
        return screenPoint.x >= 0 && screenPoint.x <= canvas.getWidth() + MAP_PIECE_SCREEN_SIZE_X &&
                screenPoint.y >= 0 + map.MIN_HEIGHT * 10 && screenPoint.y <= canvas.getHeight() + map.MAX_HEIGHT * 10;
    }

    Point getZeroScreenPosition() {
        return zeroScreenPosition;
    }

    public Map getMap() {
        return map;
    }
}
