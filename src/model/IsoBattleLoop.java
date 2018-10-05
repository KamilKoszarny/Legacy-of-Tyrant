package model;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.map.MapPiece;
import viewIso.*;

import java.awt.*;

public class IsoBattleLoop extends AnimationTimer{

    public static final int FRAME_RATE = 100;

    private Battle battle;
    private boolean mapMoveFlag = false;
    private Point mapMove = new Point(0, 0);
    private boolean canvasClickFlag = false;
    private Point canvasClickPoint;
    private Alert alert;
    private boolean alertOn = false;
    private IsoViewer isoViewer;
    private PanelViewer panelViewer;
    private MapDrawer mapDrawer;
    private CharsDrawer charsDrawer;
    private int lastMs, curMs;

    public IsoBattleLoop(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void handle(long curNanoTime) {
        animate(curNanoTime);

        handleMapMoving();
        handleCanvasClick();
    }

    private void animate(long curNanoTime) {
        curMs = (int) (curNanoTime / 1000000);
        if(msLeft(FRAME_RATE)){
            isoViewer.animate();
            panelViewer.refresh();
            lastMs = curMs;
        }
    }


    private void handleMapMoving(){
        if (mapMoveFlag) {
            isoViewer.moveMap(mapMove);
        }
    }

    private void handleCanvasClick(){
        if (canvasClickFlag) {
            if (charsDrawer.isCharClicked(canvasClickPoint)) {
                battle.chooseCharacter(charsDrawer.getClickedCharacter());
                canvasClickFlag = false;
            }
            else
                showMapPieceInfo();
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


    public void setCanvasClickFlag(boolean canvasClickFlag) {
        this.canvasClickFlag = canvasClickFlag;
    }

    public void setCanvasClickPoint(Point canvasClickPoint) {
        this.canvasClickPoint = canvasClickPoint;
    }

    public void setViewersAndDrawers(IsoViewer isoViewer, PanelViewer panelViewer) {
        this.isoViewer = isoViewer;
        this.panelViewer = panelViewer;
        mapDrawer = isoViewer.getMapDrawer();
        charsDrawer = isoViewer.getCharsDrawer();
    }

    private boolean msLeft (int ms) {
        return curMs - ms > lastMs;
    }

    private void showMapPieceInfo() {
        if (!alertOn) {
            this.stop();
            MapPiece clickedMapPiece = mapDrawer.mapPieceByClickPoint(canvasClickPoint);
            Point clickedPoint = new Point();
            for (Point point: mapDrawer.getMap().getPoints().keySet()) {
                if (mapDrawer.getMap().getPoints().get(point) == clickedMapPiece)
                    clickedPoint = point;
            }

            alert = new MapPieceInfo(clickedMapPiece, clickedPoint);
            alertOn = true;
        } else if (alert.getResult() == ButtonType.OK) {
            System.out.println("OK");
            canvasClickFlag = false;
            alertOn = false;
            alert.setResult(null);
        }
        this.start();
    }
}
