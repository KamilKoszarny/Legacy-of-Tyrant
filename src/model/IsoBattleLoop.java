package model;

import javafx.animation.AnimationTimer;
import main.App;
import model.actions.*;
import model.actions.attack.AttackActioner;
import model.actions.movement.Char2CharMover;
import model.actions.movement.CharMover;
import model.actions.movement.CharTurner;
import model.actions.movement.Char2ObjectMover;
import model.actions.objects.ChestActioner;
import model.actions.objects.DoorActioner;
import model.ai.EnemyFinder;
import model.character.Character;
import model.map.MapPiece;
import model.map.buildings.Chest;
import model.map.buildings.Door;
import model.map.mapObjects.ItemMapObject;
import viewIso.*;
import viewIso.characters.CharsDrawer;
import viewIso.clickMenus.ClickMenuButton;
import viewIso.clickMenus.ClickMenusDrawer;
import viewIso.mapObjects.MapObjectController;
import viewIso.mapObjects.MapObjectDrawer;
import viewIso.panel.PanelViewer;

import java.awt.*;

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
            update();
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

        for (Character character: Battle.getCharacters()) {
            battleEvent = ActionQueuer.getNextEvent(character);
            if (battleEvent != null)
                handleBattleEvent();
        }
        App.showAndResetTime("frame", 0);
    }

    private void update() {
        App.resetTime(1);
        Battle.update(FRAME_RATE);
        App.showAndResetTime("battleUpdate", 1);
        IsoViewer.draw();
        App.showAndResetTime("draw", 1);
        PanelViewer.refresh();
        App.showAndResetTime("panel", 1);
    }

    private void handleBattleEvent() {
        if (Battle.isTurnMode() && battleEvent.getType().isPlayerAction() &&
                (!TurnsTracker.activeCharChosen() || TurnsTracker.activeCharOutOfAP()))
            return;

        switch (battleEvent.getType()){
            case MOVE_MAP:
                MapMover.setMapMove(battleEvent.getClickPoint());
                break;
            case CHOOSE_CHARACTER:
                Battle.setChosenCharacter(CharsDrawer.getClickedCharacter());
                ClickMenusDrawer.hideMenus(false);
                break;
            case SHOW_CHAR2POINT:
                ClickMenusDrawer.drawChar2PointMenuAndPath(battleEvent.getClickPoint(), battleEvent.getMapPoint());
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_CHAR2DOOR: ClickMenusDrawer.drawChar2DoorMenu(battleEvent.getClickPoint(),
                    (Door) battleEvent.getObject(), Battle.getChosenCharacter());
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_CHAR2CHEST: ClickMenusDrawer.drawChar2ChestMenu(battleEvent.getClickPoint(),
                    (Chest) battleEvent.getObject(), Battle.getChosenCharacter());
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_CHAR2ENEMY:
                ClickMenusDrawer.drawChar2EnemyMenu(battleEvent.getClickPoint(), Battle.getChosenCharacter(), CharsDrawer.getClickedCharacter());
                buttonBattleEvent = battleEvent;
                break;
            case SHOW_MAP_PIECE_INFO:
                showMapPieceInfo(battleEvent.getMapPoint(), battleEvent.getMapPiece());
                break;
            case GIVE_ITEM:
                ItemHandler.tryGiveItem(Battle.getChosenCharacter(), battleEvent.getSubjectCharacter(), ItemHandler.getHeldItem());
                break;
            case DROP_ITEM:
                ItemHandler.tryDropItem(Battle.getChosenCharacter(), ItemHandler.getHeldItem(), battleEvent.getMapPoint());
                break;
            case PICKUP_ITEM:
                ItemHandler.tryPickupItem(Battle.getChosenCharacter(), (ItemMapObject) battleEvent.getObject(), battleEvent.getMapPoint());
                break;
            case OPEN_DOOR:
                CharMover.stopCharacter(battleEvent.getDoingCharacter());
                DoorActioner.openDoor(battleEvent.getDoingCharacter(), (Door) battleEvent.getObject());
                break;
            case CLOSE_DOOR:
                CharMover.stopCharacter(battleEvent.getDoingCharacter());
                DoorActioner.closeDoor(battleEvent.getDoingCharacter(), (Door) battleEvent.getObject());
                break;
            case OPEN_CHEST:
                CharMover.stopCharacter(battleEvent.getDoingCharacter());
                ChestActioner.openChest(battleEvent.getDoingCharacter(), (Chest) battleEvent.getObject(), battleEvent.getClickPoint());
                break;
            case GO2OBJECT:
                Char2ObjectMover.calcPathAndStartRunToObject(battleEvent.getDoingCharacter(), battleEvent.getObject());
                break;
            case GO2ENEMY:
                Char2CharMover.calcPathAndStartRunToChar(battleEvent.getDoingCharacter(), battleEvent.getSubjectCharacter());
                break;
            case ATTACK:
                CharMover.stopCharacter(battleEvent.getDoingCharacter());
                AttackActioner.attackCharacter(battleEvent.getDoingCharacter(), battleEvent.getSubjectCharacter(),
                        battleEvent.getBodyPart());
                break;
            case LOOK4ENEMY:
                EnemyFinder.turnAround(battleEvent.getDoingCharacter());
        }

        battleEvent = null;
    }

    private static void handleItemClick() {
        ItemHandler.moveItem(Battle.getChosenCharacter(), clickedInventorySlot, clickedItemPoint);
        itemClickFlag = false;
    }

    private static void handleButtonAction(ClickMenuButton button) {
        switch (button){
            case LOOK:
                CharTurner.turnStandingCharacter(Battle.getChosenCharacter(), buttonBattleEvent.getMapPoint(), true);
                break;
            case DOOR_LOOK:
            case CHEST_LOOK:
                CharTurner.turnStandingCharacter(Battle.getChosenCharacter(),
                        MapObjectDrawer.getMapObject2PointMap().get(buttonBattleEvent.getObject()), true);
                break;
            case ENEMY_LOOK:
                CharTurner.turnStandingCharacter(Battle.getChosenCharacter(),
                        buttonBattleEvent.getSubjectCharacter().getPosition(), true);
                break;
            case RUN:
                Battle.getChosenCharacter().setRunning(true);
                CharMover.calcPathAndStartMove(Battle.getChosenCharacter(), buttonBattleEvent.getMapPoint());
                break;
            case DOOR_OPEN:
                ActionQueuer.clearEventQueue(Battle.getChosenCharacter());
                ActionQueuer.addEvent(new BattleEvent(EventType.GO2OBJECT, buttonBattleEvent.getObject()));
                ActionQueuer.addEvent(new BattleEvent(EventType.OPEN_DOOR, buttonBattleEvent.getObject()));
                break;
            case DOOR_CLOSE:
                ActionQueuer.clearEventQueue(Battle.getChosenCharacter());
                ActionQueuer.addEvent(new BattleEvent(EventType.GO2OBJECT, buttonBattleEvent.getObject()));
                ActionQueuer.addEvent(new BattleEvent(EventType.CLOSE_DOOR, buttonBattleEvent.getObject()));
                break;
            case CHEST_OPEN:
                ActionQueuer.clearEventQueue(Battle.getChosenCharacter());
                ActionQueuer.addEvent(new BattleEvent(EventType.GO2OBJECT, buttonBattleEvent.getObject()));
                ActionQueuer.addEvent(new BattleEvent(EventType.OPEN_CHEST, buttonBattleEvent.getClickPoint(),
                        buttonBattleEvent.getObject()));
                break;
            case ATTACK_BODY:
            case ATTACK_HEAD:
            case ATTACK_ARMS:
            case ATTACK_LEGS:
                AttackActioner.scheduleGoAndAttack(Battle.getChosenCharacter(), buttonBattleEvent.getSubjectCharacter(),
                        ClickMenuButton.getBodyPart(button));
                break;
        }

        ClickMenusDrawer.hideMenus(false);
    }

    private void handleHover() {
        if (hoverPoint != null) {
            LabelsDrawer.checkHoverCharacter(hoverPoint);
            MapObjectController.checkHoverObject(hoverPoint);
//            ItemObjectsDrawer.checkHoverItem(hoverPoint);
        }
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
