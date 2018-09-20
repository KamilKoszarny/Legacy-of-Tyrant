package controller.isoView;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import viewIso.IsoViewer;

import java.awt.*;


public class IsoMapMoveController {

    private IsoViewer isoViewer;
    private Canvas canvas;
    public static final int MAP_MOVE_BOUNDARY = 5;
    public static final int MAP_MOVE_STEP = 10;

    IsoMapMoveController(IsoViewer isoViewer, Canvas canvas) {
        this.isoViewer = isoViewer;
        this.canvas = canvas;
        initialize();
    }

    private void initialize(){
        initMapMoving();
    }

    private void initMapMoving(){
        canvas.setOnMouseMoved(mouseEvent -> {
            Point2D mousePos = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            if (isOnBoundary(mousePos)) {
                moveMap(moveByBoundary(mousePos));
            }
        });
    }

    private boolean isOnBoundary(Point2D mousePos){
        return mousePos.getX() < MAP_MOVE_BOUNDARY ||
                mousePos.getX() > canvas.getWidth() - MAP_MOVE_BOUNDARY ||
                mousePos.getY() < MAP_MOVE_BOUNDARY ||
                mousePos.getY() > canvas.getHeight() - MAP_MOVE_BOUNDARY;
    }

    private Point moveByBoundary(Point2D mousePos){
        Point move = new Point(0, 0);
        if (mousePos.getX() < MAP_MOVE_BOUNDARY)
            move.x = MAP_MOVE_STEP;
        else if (mousePos.getX() > canvas.getWidth() - MAP_MOVE_BOUNDARY)
            move.x = -MAP_MOVE_STEP;
        if (mousePos.getY() < MAP_MOVE_BOUNDARY)
            move.y = MAP_MOVE_STEP;
        else if(mousePos.getY() > canvas.getHeight() - MAP_MOVE_BOUNDARY)
            move.y = -MAP_MOVE_STEP;
        return move;
    }

    private void moveMap(Point move){
        isoViewer.getMapDrawer().changeZeroScreenPosition(move);
        isoViewer.getMapDrawer().clearMapBounds();
        isoViewer.getMapDrawer().drawMap();
    }
}
