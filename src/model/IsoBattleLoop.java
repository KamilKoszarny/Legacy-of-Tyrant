package model;

import javafx.animation.AnimationTimer;
import model.items.Item;
import model.map.MapPiece;
import model.map.buildings.Door;
import viewIso.*;
import viewIso.characters.CharsDrawer;
import viewIso.panel.PanelViewer;

import java.awt.*;

public class IsoBattleLoop extends AnimationTimer{

    private static final int FRAME_RATE = 50;

    private static int lastMs, curMs;

    private static Battle battle;
    private static BattleEvent battleEvent = null;
    private static BattleEvent buttonBattleEvent = null;

    private static Point hoverPoint = null;

    private static boolean itemClickFlag = false;
    private static int[] clickedInventorySlot;
    private static Point clickedItemPoint;


    public IsoBattleLoop(Battle battle) {
        IsoBattleLoop.battle = battle;
        lastMs = (int) (System.nanoTime() / 1000000);
    }

    @Override
    public void handle(long curNanoTime) {
        if(nextFrame(curNanoTime)) {
            animate();
            battle.incrementTimer();
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
        battle.updateCharacters(FRAME_RATE);
        IsoViewer.draw();
        PanelViewer.refresh();
    }

    private void handleBattleEvent() {
        switch (battleEvent.getType()){
            case MOVE_MAP:
                IsoViewer.setMapMove(battleEvent.getClickPoint());
                break;
            case CHOOSE_CHARACTER:
                Battle.chooseCharacter(CharsDrawer.getClickedCharacter());
                break;
            case CHAR2POINT:
                ClickMenusDrawer.drawChar2PointMenu(battleEvent.getClickPoint());
                buttonBattleEvent = battleEvent;
                break;
            case CHAR2OBJECT: ClickMenusDrawer.drawChar2DoorMenu(battleEvent.getClickPoint(),
                    (Door) battleEvent.getObject(), Battle.getChosenCharacter());
                buttonBattleEvent = battleEvent;
                break;
            case MAP_PIECE_INFO:
                showMapPieceInfo(battleEvent.getMapPoint(), battleEvent.getMapPiece());
                break;
            case MAP_HOVER:
                CharsDrawer.checkHoverCharacter(battleEvent.getClickPoint());
                break;
        }
    }

    private static void handleItemClick() {
        Item heldItem = battle.moveItem(clickedInventorySlot, clickedItemPoint);
        PanelViewer.setHeldItem(heldItem, clickedItemPoint);
        itemClickFlag = false;
    }

    private static void handleButtonAction(ClickMenuButton button) {
        if (button == ClickMenuButton.LOOK)
            battle.turnCharacter(buttonBattleEvent.getMapPoint());
        if (button == ClickMenuButton.RUN) {
            if (buttonBattleEvent.getMapPoint() == null)
                System.out.println("null");
            battle.startRunCharacter(buttonBattleEvent.getMapPoint());
        }
        if (button == ClickMenuButton.DOOR_LOOK)
            battle.turnCharacter(buttonBattleEvent.getMapPoint());
        if (button == ClickMenuButton.DOOR_OPEN)
            battle.openDoor(battleEvent.getObject());
        if (button == ClickMenuButton.DOOR_CLOSE)
            battle.closeDoor(battleEvent.getObject());


        ClickMenusDrawer.hideMenus();
    }

    private void handleHover() {
        if (hoverPoint != null)
            CharsDrawer.checkHoverCharacter(hoverPoint);
    }


    private static boolean nextFrame(long curNanoTime) {
        curMs = (int) (curNanoTime / 1000000);
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
