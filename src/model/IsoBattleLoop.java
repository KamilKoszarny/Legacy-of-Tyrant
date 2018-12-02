package model;

import controller.isoView.isoMap.IsoMapMoveController;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import model.items.Item;
import model.map.MapPiece;
import model.map.buildings.Door;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;
import viewIso.*;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;
import viewIso.mapObjects.MapObjectDrawer;
import viewIso.panel.PanelViewer;

import java.awt.*;

public class IsoBattleLoop extends AnimationTimer{

    private static final int FRAME_RATE = 50;

    private Battle battle;
    private static boolean mapMoveFlag = false;
    private static Point mapMove = new Point(0, 0);
    private static boolean canvasLClickFlag = false, canvasRClickFlag = false, canvasHoverFlag = false, itemClickFlag = false;
    private static Point canvasLClickPoint, canvasRClickPoint, canvasHoverPoint = new Point(0, 0);
    private static Point clickedMapPoint;
    private static int[] clickedInventorySlot;
    private static Point clickedItemPoint;
    private static MapPiece clickedMapPiece;
    private static MapObject clickedObject;
    private static Alert alert;
    private static boolean alertOn = false;
    private static IsoViewer isoViewer;
    private static PanelViewer panelViewer;
    private static MapDrawer mapDrawer;
    private static CharsDrawer charsDrawer;
    private static ClickMenusDrawer clickMenusDrawer;
    private static int lastMs, curMs;

    public IsoBattleLoop(Battle battle) {
        this.battle = battle;
        lastMs = (int) (System.nanoTime() / 1000000);
    }

    @Override
    public void handle(long curNanoTime) {
        if(nextFrame(curNanoTime)) {
            animate();
            battle.incrementTimer();
        }

        if (mapMoveFlag)
            handleMapMoving();
        if (canvasLClickFlag)
            handleCanvasLClick();
        if (canvasRClickFlag)
            handleCanvasRClick();
        if (canvasHoverFlag)
            handleCanvasHover();
        if (itemClickFlag)
            handleItemClick();

        ClickMenuButton clickedButton = clickMenusDrawer.clickedButton();
        if (clickedButton != null)
            handleButtonAction(clickedButton);
    }

    private void animate() {
        battle.updateCharacters(FRAME_RATE);
        isoViewer.draw();
        panelViewer.refresh();
    }


    private void handleMapMoving(){
        if (mapMoveFlag) {
            isoViewer.moveMap(mapMove);
        }
    }

    private void handleCanvasLClick(){
        if (charsDrawer.isOtherCharClicked(canvasLClickPoint, battle.getChosenCharacter())) {
            battle.chooseCharacter(charsDrawer.getClickedCharacter());
        } else {
            clickedMapPoint = MapDrawCalculator.mapPointByClickPoint(canvasLClickPoint);
            if (battle.getChosenCharacter() != null && clickedMapPoint != null) {
                clickedMapPiece = MapDrawCalculator.mapPieceByClickPoint(canvasLClickPoint);
                clickedObject = MapObjectDrawer.clickedObject(canvasLClickPoint);
                if (clickedObject != null && clickedObject.getType().equals(MapObjectType.DOOR))
                    clickMenusDrawer.drawChar2DoorMenu(canvasLClickPoint, (Door) clickedObject, battle.getChosenCharacter());
                else
                    clickMenusDrawer.drawChar2PointMenu(canvasLClickPoint);
            }
        }
        canvasLClickFlag = false;
    }

    private void handleCanvasRClick() {
        MapPiece clickedMapPiece = MapDrawCalculator.mapPieceByClickPoint(canvasRClickPoint);
        if (clickedMapPiece != null)
            showMapPieceInfo(clickedMapPiece);
    }

    private void handleCanvasHover() {
        charsDrawer.checkHoverCharacter(canvasHoverPoint);
        canvasHoverFlag = false;
    }

    private void handleItemClick() {
        Item heldItem = battle.moveItem(clickedInventorySlot, clickedItemPoint);
        panelViewer.setHeldItem(heldItem, clickedItemPoint);
        itemClickFlag = false;
    }

    private void handleButtonAction(ClickMenuButton button) {
        if (button == ClickMenuButton.LOOK)
            battle.turnCharacter(clickedMapPoint);
        if (button == ClickMenuButton.RUN)
            battle.startRunCharacter(clickedMapPoint);
        if (button == ClickMenuButton.DOOR_LOOK)
            battle.turnCharacter(clickedMapPoint);
        if (button == ClickMenuButton.DOOR_OPEN)
            battle.openDoor(clickedObject);
        if (button == ClickMenuButton.DOOR_CLOSE)
            battle.closeDoor(clickedObject);


        clickMenusDrawer.hideMenus();
    }

    public void setMapMoveFlag(boolean mapMoveFlag) {
        IsoBattleLoop.mapMoveFlag = mapMoveFlag;
    }

    public void changeMapMove(Point mapMoveChange) {
        mapMove.x = mapMove.x + mapMoveChange.x;
        mapMove.y = mapMove.y + mapMoveChange.y;
        if (mapMove.x > IsoMapMoveController.MAP_MOVE_STEP)
            mapMove.x = IsoMapMoveController.MAP_MOVE_STEP;
        if (mapMove.y > IsoMapMoveController.MAP_MOVE_STEP)
            mapMove.y = IsoMapMoveController.MAP_MOVE_STEP;
    }

    public void resetMapMove(Point mapMove) {
        IsoBattleLoop.mapMove = mapMove;
    }

    private boolean nextFrame(long curNanoTime) {
        curMs = (int) (curNanoTime / 1000000);
        if (curMs - lastMs > FRAME_RATE){
            lastMs = curMs;
            return true;
        }
        return false;
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
        } else {
            canvasRClickFlag = false;
            alertOn = false;
            alert.setResult(null);
        }
        this.start();
    }


    public static void setCanvasLClickFlag(boolean canvasLClickFlag) {
        IsoBattleLoop.canvasLClickFlag = canvasLClickFlag;
    }

    public static void setCanvasLClickPoint(Point canvasLClickPoint) {
        IsoBattleLoop.canvasLClickPoint = canvasLClickPoint;
    }

    public static void setCanvasRClickFlag(boolean canvasRClickFlag) {
        IsoBattleLoop.canvasRClickFlag = canvasRClickFlag;
    }

    public static void setCanvasRClickPoint(Point canvasRClickPoint) {
        IsoBattleLoop.canvasRClickPoint = canvasRClickPoint;
    }

    public static void setCanvasHoverFlag(boolean canvasHoverFlag) {
        IsoBattleLoop.canvasHoverFlag = canvasHoverFlag;
    }

    public static void setItemClickFlag(boolean itemClickFlag) {
        IsoBattleLoop.itemClickFlag = itemClickFlag;
    }

    public static void setClickedInventorySlot(int[] clickedInventorySlot) {
        IsoBattleLoop.clickedInventorySlot = clickedInventorySlot;
    }

    public static Point getClickedItemPoint() {
        return clickedItemPoint;
    }
    public static void setClickedItemPoint(Point clickedItemPoint) {
        IsoBattleLoop.clickedItemPoint = clickedItemPoint;
    }

    public static void setCanvasHoverPoint(Point canvasHoverPoint) {
        IsoBattleLoop.canvasHoverPoint = canvasHoverPoint;
    }

    public static void setViewersAndDrawers(IsoViewer isoViewer, PanelViewer panelViewer) {
        IsoBattleLoop.isoViewer = isoViewer;
        IsoBattleLoop.panelViewer = panelViewer;
        mapDrawer = isoViewer.getMapDrawer();
        charsDrawer = isoViewer.getCharsDrawer();
        clickMenusDrawer = isoViewer.getClickMenusDrawer();
    }
}
