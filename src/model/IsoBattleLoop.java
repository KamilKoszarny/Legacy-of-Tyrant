package model;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import model.actions.*;
import model.actions.attack.AttackActioner;
import model.actions.attack.BodyPart;
import model.actions.movement.CharMover;
import model.actions.movement.CharTurner;
import model.items.Item;
import model.map.MapPiece;
import model.map.buildings.Door;
import viewIso.*;
import viewIso.characters.CharsDrawer;
import viewIso.mapObjects.MapObjectDrawer;
import viewIso.panel.PanelViewer;

import java.awt.*;
import java.util.List;

public class IsoBattleLoop extends AnimationTimer{

    private static final int FRAME_RATE = 50;

    private static int lastMs;

    private static BattleEvent battleEvent = null;
    private static BattleEvent buttonBattleEvent = null;

    private static Point hoverPoint = null;

    private static boolean itemClickFlag = false;
    private static int[] clickedInventorySlot;
    private static Point clickedItemPoint;


    public IsoBattleLoop() {
        lastMs = (int) (System.nanoTime() / 1000000);
    }

    @Override
    public void handle(long curNanoTime) {
        if(nextFrame(curNanoTime)) {
            animate();
            Battle.incrementTimer();
        }

        if (battleEvent != null)
            handleBattleEvent();

        handleHover();

        if (itemClickFlag)
            handleItemClick();

        ClickMenuButton clickedButton = ClickMenusDrawer.clickedButton();
        if (clickedButton != null)
            handleButtonAction(clickedButton);
    }

    private void animate() {
        Battle.updateCharacters(FRAME_RATE);
        IsoViewer.draw();
        PanelViewer.refresh();
    }

    private void handleBattleEvent() {
        switch (battleEvent.getType()){
            case MOVE_MAP:
                IsoViewer.setMapMove(battleEvent.getClickPoint());
                break;
            case CHOOSE_CHARACTER:
                CharsChooser.chooseCharacter(CharsDrawer.getClickedCharacter());
                break;
            case SHOW_CHAR2POINT:
                List<Point2D> path = CharMover.calcPath(Battle.getChosenCharacter(), battleEvent.getMapPoint());
                if (Battle.getChosenCharacter().getCurrentSpeed() == 0) {
                    Battle.getChosenCharacter().setPath(path);
                    if (path.size() > 0) {
                        PathDrawer.createPathView(Battle.getChosenCharacter());
                    }
                }
                ClickMenusDrawer.drawChar2PointMenu(battleEvent.getClickPoint(), path);
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_CHAR2OBJECT: ClickMenusDrawer.drawChar2DoorMenu(battleEvent.getClickPoint(),
                    (Door) battleEvent.getObject(), Battle.getChosenCharacter());
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_CHAR2ENEMY:
                ClickMenusDrawer.drawChar2EnemyMenu(battleEvent.getClickPoint(), Battle.getChosenCharacter(), CharsDrawer.getClickedCharacter());
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_MAP_PIECE_INFO:
                showMapPieceInfo(battleEvent.getMapPoint(), battleEvent.getMapPiece());
                break;
        }

        battleEvent = null;
    }

    private static void handleItemClick() {
        Item heldItem = ItemHandler.moveItem(Battle.getChosenCharacter(), clickedInventorySlot, clickedItemPoint);
        PanelViewer.setHeldItem(heldItem, clickedItemPoint);
        itemClickFlag = false;
    }

    private static void handleButtonAction(ClickMenuButton button) {
        switch (button){
            case LOOK:
                CharTurner.turnCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getMapPoint(), true);
                break;
            case DOOR_LOOK:
                CharTurner.turnCharacter(Battle.getChosenCharacter(),
                        MapObjectDrawer.getMapObjectPointMap().get(buttonBattleEvent.getObject()), true);
                break;
            case ENEMY_LOOK:
                CharTurner.turnCharacter(Battle.getChosenCharacter(),
                        buttonBattleEvent.getCharacter().getPosition(), true);
                break;
            case RUN:
                if (buttonBattleEvent.getMapPoint() == null)
                    System.out.println("null");
                CharMover.startRunCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getMapPoint());
                break;
            case DOOR_OPEN:
                DoorActioner.openDoor(buttonBattleEvent.getObject());
                break;
            case DOOR_CLOSE:
                DoorActioner.closeDoor(buttonBattleEvent.getObject());
                break;
            case ATTACK_BODY:
                AttackActioner.attackCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getCharacter(), BodyPart.BODY);
                break;
            case ATTACK_HEAD:
                AttackActioner.attackCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getCharacter(), BodyPart.HEAD);
                break;
            case ATTACK_ARMS:
                AttackActioner.attackCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getCharacter(), BodyPart.ARMS);
                break;
            case ATTACK_LEGS:
                AttackActioner.attackCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getCharacter(), BodyPart.LEGS);
                break;
        }

        ClickMenusDrawer.hideMenus();
    }

    private void handleHover() {
        if (hoverPoint != null)
            LabelsDrawer.checkHoverCharacter(hoverPoint);
    }


    private static boolean nextFrame(long curNanoTime) {
        int curMs = (int) (curNanoTime / 1000000);
        if (curMs - lastMs > FRAME_RATE){
            lastMs = curMs;
            return true;
        }
        return false;
    }

    private void showMapPieceInfo(Point mapPoint, MapPiece mapPiece) {
        this.stop();
        new MapPieceInfo(mapPiece, mapPoint);
        this.start();
    }


    public static void setBattleEvent(BattleEvent battleEvent) {
        IsoBattleLoop.battleEvent = battleEvent;
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


    public static void setHoverPoint(Point hoverPoint) {
        IsoBattleLoop.hoverPoint = hoverPoint;
    }
}
