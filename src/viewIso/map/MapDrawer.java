package viewIso.map;

import controller.isoView.isoMap.IsoMapMoveController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.map.Map;

import java.awt.*;
import java.util.List;

import static viewIso.map.MapDrawCalculator.screenPosition;

public class MapDrawer {

    static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final int MAP_PIECE_SCREEN_SIZE_X = 24;
    public static final int MAP_PIECE_SCREEN_SIZE_Y = 16;
    public static int PIX_PER_M;

    private Point zeroScreenPosition = new Point(600, -50);
    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapPieceDrawer mPDrawer;
    private MapImage mapImage;

    public MapDrawer(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        mPDrawer = new MapPieceDrawer(map, gc, this, MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y);
        PIX_PER_M = (int) ((MAP_PIECE_SCREEN_SIZE_X + MAP_PIECE_SCREEN_SIZE_Y) / 2 / Map.M_PER_POINT);
        MapDrawCalculator.setMapAndDrawer(map, this);

        MapImageGenerator.initialize(map, mPDrawer);
        long time = System.nanoTime();
        mapImage = MapImageGenerator.generateMapPreImage();
        System.out.println("mapPreImageGen:" + (System.nanoTime() - time)/1000000. + " ms");
//        time = System.nanoTime();
//        mapImage = MapImageGenerator.generateMapImage();
//        System.out.println("mapImageGen:" + (System.nanoTime() - time)/1000000. + " ms");
//        time = System.nanoTime();
    }

    public void drawMap() {
        canvas.getGraphicsContext2D().drawImage(mapImage.getImage(),
                -mapImage.getxShift() - zeroScreenPosition.x, -mapImage.getyShift() - zeroScreenPosition.y,
                canvas.getWidth(), canvas.getHeight(), 0, 0, canvas.getWidth(), canvas.getHeight());
    }

//    public void drawMap(){
//        drawMapPoints(MapDrawCalculator.calcVisiblePoints());
//    }

    public void drawMapPoints(List<Point> points) {
        for (Point point: points) {
            mPDrawer.drawMapPiece(point);
        }
    }

    public void clearPointAround(Point point, int width, int height, int shiftX, int shiftY) {
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        clearBox(new Point(screenPos.x - shiftX, screenPos.y - shiftY), width, height);
        drawMapPoints(MapDrawCalculator.calcClosePoints(point, width, height));
    }

    private void clearBox(Point point, int width, int height) {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(point.x, point.y, width, height);
    }

    public void clearMapBounds(){
        //dir 0: N-S, 1: E_W
        double[] xCoords = new double[8];
        double[] yCoords = new double[8];
        Point point;
        int moveStep = IsoMapMoveController.MAP_MOVE_STEP;

        setCoordsUpper(xCoords, yCoords, moveStep);
        gc.setFill(BACKGROUND_COLOR);
        gc.fillPolygon(xCoords, yCoords, 8);

        setCoordsBottom(xCoords, yCoords, moveStep);
        gc.setFill(BACKGROUND_COLOR);
        gc.fillPolygon(xCoords, yCoords, 8);
    }

    private void setCoordsUpper(double[] xCoords, double[] yCoords, int moveStep) {
        Point point;
        point = new Point(0, 0);
        xCoords[0] = screenPosition(point).x;
        yCoords[0] = screenPosition(point).y - MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, 0);
        xCoords[1] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[1] = screenPosition(point).y - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[2] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[2] = screenPosition(point).y - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[3] = screenPosition(point).x;
        yCoords[3] = screenPosition(point).y - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(0, 0);
        xCoords[4] = screenPosition(point).x;
        yCoords[4] = screenPosition(point).y + MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(0, map.mapYPoints);
        xCoords[5] = screenPosition(point).x;
        yCoords[5] = screenPosition(point).y - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[6] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[6] = screenPosition(point).y - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[7] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[7] = screenPosition(point).y - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;
    }

    private void setCoordsBottom(double[] xCoords, double[] yCoords, int moveStep) {
        Point point;
        point = new Point(map.mapXPoints, map.mapYPoints);
        xCoords[0] = screenPosition(point).x;
        yCoords[0] = screenPosition(point).y - MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, 0);
        xCoords[1] = screenPosition(point).x;
        yCoords[1] = screenPosition(point).y - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[2] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[2] = screenPosition(point).y - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[3] = screenPosition(point).x + MAP_PIECE_SCREEN_SIZE_X / 2 + moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[3] = screenPosition(point).y - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        point = new Point(map.mapXPoints, map.mapYPoints);
        xCoords[4] = screenPosition(point).x;
        yCoords[4] = screenPosition(point).y + MAP_PIECE_SCREEN_SIZE_Y / 2 - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y +
                50;

        point = new Point(0, map.mapYPoints);
        xCoords[5] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[5] = screenPosition(point).y - map.MIN_HEIGHT_PIX + moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[6] = screenPosition(point).x - MAP_PIECE_SCREEN_SIZE_X / 2 - moveStep * MAP_PIECE_SCREEN_SIZE_X;
        yCoords[6] = screenPosition(point).y - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;

        xCoords[7] = screenPosition(point).x;
        yCoords[7] = screenPosition(point).y - map.MAX_HEIGHT_PIX - moveStep * MAP_PIECE_SCREEN_SIZE_Y;
    }

    public void changeZeroScreenPosition(Point zSPChange) {
        this.zeroScreenPosition.x += zSPChange.x * MAP_PIECE_SCREEN_SIZE_X;
        this.zeroScreenPosition.y += zSPChange.y * MAP_PIECE_SCREEN_SIZE_Y;
    }


    public Point getZeroScreenPosition() {
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


}
