package model;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.map.MapPiece;
import viewIso.IsoViewer;
import viewIso.MapDrawer;
import viewIso.MapPieceInfo;

import java.awt.*;

public class IsoBattleLoop extends AnimationTimer{

    private boolean mapMoveFlag = false;
    private Point mapMove = new Point(0, 0);
    private boolean mapClickFlag = false;
    private Point mapClickPoint;
    Alert alert;
    private boolean alertOn = false;
    private IsoViewer isoViewer;
    private MapDrawer mapDrawer;

    @Override
    public void handle(long curNanoTime) {
        handleMapMoving();
        handleMapClick();
    }

    private void handleMapMoving(){
        if (mapMoveFlag) {
            isoViewer.moveMap(mapMove);
        }
    }

    private void handleMapClick(){
        if (mapClickFlag) {
            if (!alertOn) {
                this.stop();
                MapPiece clickedMapPiece = mapDrawer.mapPieceByClickPoint(mapClickPoint);
                Point clickedPoint = new Point();
                for (Point point: mapDrawer.getMap().getPoints().keySet()) {
                    if (mapDrawer.getMap().getPoints().get(point) == clickedMapPiece)
                        clickedPoint = point;
                }

                alert = new MapPieceInfo(clickedMapPiece, clickedPoint);
                alertOn = true;
            } else if (alert.getResult() == ButtonType.OK) {
                System.out.println("OK");
                mapClickFlag = false;
                alertOn = false;
                alert.setResult(null);
            }
            this.start();
        }
    }

    public void setMapMoveFlag(boolean mapMoveFlag) {
        this.mapMoveFlag = mapMoveFlag;
    }

    public void changeMapMove(Point mapMoveChange) {
        mapMove.x = mapMove.x + mapMoveChange.x;
        mapMove.y = mapMove.y + mapMoveChange.y;
    }

    public void resetMapMove(Point mapMove) {
        this.mapMove = mapMove;
    }


    public void setMapClickFlag(boolean mapClickFlag) {
        this.mapClickFlag = mapClickFlag;
    }

    public void setMapClickPoint(Point mapClickPoint) {
        this.mapClickPoint = mapClickPoint;
    }

    public void setIsoViewer(IsoViewer isoViewer) {
        this.isoViewer = isoViewer;
        mapDrawer = isoViewer.getMapDrawer();
    }
}
