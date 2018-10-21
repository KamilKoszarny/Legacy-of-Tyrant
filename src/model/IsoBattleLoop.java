package model;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.map.MapPiece;
import viewIso.*;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawer;

import java.awt.*;

public class IsoBattleLoop extends AnimationTimer{

    public static final int FRAME_RATE = 50;

    private Battle battle;
    private boolean mapMoveFlag = false;
    private Point mapMove = new Point(0, 0);
    private boolean canvasLClickFlag = false;
    private Point canvasLClickPoint;
    private boolean canvasRClickFlag = false;
    private Point canvasRClickPoint;
    private boolean canvasHoverFlag = false;
    private Point canvasHoverPoint = new Point(0, 0);
    private Point clickedMapPoint;
    private Alert alert;
    private boolean alertOn = false;
    private IsoViewer isoViewer;
    private PanelViewer panelViewer;
    private MapDrawer mapDrawer;
    private CharsDrawer charsDrawer;
    private ClickMenusDrawer clickMenusDrawer;
    private int lastMs, curMs;

    public IsoBattleLoop(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void handle(long curNanoTime) {
        animate(curNanoTime);

        if (mapMoveFlag)
            handleMapMoving();
        if (canvasLClickFlag)
            handleCanvasLClick();
        if (canvasRClickFlag)
            handleCanvasRClick();
        if (canvasHoverFlag)
            handleCanvasHover();
        ClickMenuButton clickedButton = clickMenusDrawer.clickedButton();
        if (clickedButton != null)
            handleButtonAction(clickedButton);
    }

    private void animate(long curNanoTime) {
        curMs = (int) (curNanoTime / 1000000);
        if(msLeft(FRAME_RATE)){
            battle.updateCharactersLook(FRAME_RATE);
            isoViewer.draw(FRAME_RATE);
            panelViewer.refresh();
            lastMs = curMs;
            battle.incrementTimer();
        }
    }


    private void handleMapMoving(){
        if (mapMoveFlag) {
            isoViewer.moveMap(mapMove);
        }
    }

    private void handleCanvasLClick(){
        if (charsDrawer.isCharClicked(canvasLClickPoint)) {
            battle.chooseCharacter(charsDrawer.getClickedCharacter());
            canvasLClickFlag = false;
        }
        else {
            clickedMapPoint = mapDrawer.mapPointByClickPoint(canvasLClickPoint);
            if (battle.getChosenCharacter() != null && clickedMapPoint != null) {
                clickMenusDrawer.drawChar2PointMenu(canvasLClickPoint);
            }
            canvasLClickFlag = false;
        }
    }

    private void handleCanvasRClick(){
        MapPiece clickedMapPiece = mapDrawer.mapPieceByClickPoint(canvasRClickPoint);
        if (clickedMapPiece != null)
            showMapPieceInfo(clickedMapPiece);
    }

    private void handleCanvasHover() {
        charsDrawer.checkHoverCharacter(canvasHoverPoint);
        canvasHoverFlag = false;
    }

    private void handleButtonAction(ClickMenuButton button) {
        if (button == ClickMenuButton.LOOK)
            battle.turnCharacter(clickedMapPoint);
        if (button == ClickMenuButton.RUN)
            battle.startRunCharacter(clickedMapPoint);


        clickMenusDrawer.hideChar2PointMenu();
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


    public void setCanvasLClickFlag(boolean canvasLClickFlag) {
        this.canvasLClickFlag = canvasLClickFlag;
    }

    public void setCanvasLClickPoint(Point canvasLClickPoint) {
        this.canvasLClickPoint = canvasLClickPoint;
    }

    public void setCanvasRClickFlag(boolean canvasRClickFlag) {
        this.canvasRClickFlag = canvasRClickFlag;
    }

    public void setCanvasRClickPoint(Point canvasRClickPoint) {
        this.canvasRClickPoint = canvasRClickPoint;
    }

    public void setCanvasHoverFlag(boolean canvasHoverFlag) {
        this.canvasHoverFlag = canvasHoverFlag;
    }

    public void setCanvasHoverPoint(Point canvasHoverPoint) {
        this.canvasHoverPoint = canvasHoverPoint;
    }

    public void setViewersAndDrawers(IsoViewer isoViewer, PanelViewer panelViewer) {
        this.isoViewer = isoViewer;
        this.panelViewer = panelViewer;
        mapDrawer = isoViewer.getMapDrawer();
        charsDrawer = isoViewer.getCharsDrawer();
        clickMenusDrawer = isoViewer.getClickMenusDrawer();
    }

    private boolean msLeft (int ms) {
        return curMs - ms > lastMs;
    }

    private void showMapPieceInfo(MapPiece clickedMapPiece) {
        if (!alertOn) {
            this.stop();
            Point clickedPoint = new Point();
            for (Point point: mapDrawer.getMap().getPoints().keySet()) {
                if (mapDrawer.getMap().getPoints().get(point) == clickedMapPiece)
                    clickedPoint = point;
            }

            alert = new MapPieceInfo(clickedMapPiece, clickedPoint);
            alertOn = true;
        } else if (alert.getResult() == ButtonType.OK) {
            canvasRClickFlag = false;
            alertOn = false;
            alert.setResult(null);
        }
        this.start();
    }
}
