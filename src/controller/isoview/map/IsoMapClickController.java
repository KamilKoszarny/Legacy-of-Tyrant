package controller.isoview.map;

import isoview.characters.CharsDrawer;
import isoview.map.MapDrawCalculator;
import isoview.map.objects.MapObjectController;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import model.Battle;
import model.BattleEvent;
import model.EventType;
import model.IsoBattleLoop;
import model.actions.ItemHandler;
import model.map.buildings.FurnitureType;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;
import model.map.visibility.VisibilityChecker;

import java.awt.*;

//TODO fog click

public class IsoMapClickController {

    private Canvas mapCanvas;

    public IsoMapClickController(Canvas mapCanvas) {
        this.mapCanvas = mapCanvas;

        initialize();
    }

    public void initialize(){
        initCanvasClick();
    }

    private void initCanvasClick(){
        mapCanvas.setOnMouseClicked(mouseEvent -> {
            Point clickPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                IsoBattleLoop.setBattleEvent(eventByLClickPoint(clickPoint));
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.SHOW_MAP_PIECE_INFO, clickPoint,
                        MapDrawCalculator.mapPointByClickPoint(clickPoint), MapDrawCalculator.mapPieceByClickPoint(clickPoint)));
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
            }
        });
    }

    private BattleEvent eventByLClickPoint(Point clickPoint) {
        if (CharsDrawer.isOtherCharClicked(clickPoint, Battle.getChosenCharacter())) {
            BattleEvent charClickBattleEvent = getCharClickBattleEvent(clickPoint);
            if (charClickBattleEvent != null) return charClickBattleEvent;
        } else {
            Point mapPoint = MapDrawCalculator.mapPointByClickPoint(clickPoint);
            if (mapPoint == null || !VisibilityChecker.isExplored(mapPoint))
                return null;
            if (Battle.getChosenCharacter() != null) {
                BattleEvent mapClickBattleEvent = getMapClickBattleEvent(clickPoint, mapPoint);
                if (mapClickBattleEvent != null) return mapClickBattleEvent;
            }
        }
        return null;
    }

    private BattleEvent getMapClickBattleEvent(Point clickPoint, Point mapPoint) {
        MapObject object = MapObjectController.clickedObject(clickPoint);
        if (object != null) {
            if (object.getType().equals(MapObjectType.ITEM))
                return new BattleEvent(EventType.PICKUP_ITEM, object, mapPoint);
            if (object.getType().equals(MapObjectType.DOOR))
                return new BattleEvent(EventType.SHOW_CHAR2DOOR, clickPoint, object);
            if (object.getType().equals(MapObjectType.FURNITURE) && object.getFurnitureType().equals(FurnitureType.CHEST))
                return new BattleEvent(EventType.SHOW_CHAR2CHEST, clickPoint, object);
        }
        else if (ItemHandler.getHeldItem() != null)
            return new BattleEvent(EventType.DROP_ITEM, clickPoint, mapPoint);
        else
            return new BattleEvent(EventType.SHOW_CHAR2POINT, clickPoint, mapPoint);
        return null;
    }

    private BattleEvent getCharClickBattleEvent(Point clickPoint) {
        if (CharsDrawer.getClickedCharacter().getColor().equals(Battle.getPlayerColor())) {
            if (ItemHandler.getHeldItem() == null)
                return new BattleEvent(EventType.CHOOSE_CHARACTER, clickPoint);
            else
                return new BattleEvent(EventType.GIVE_ITEM, CharsDrawer.getClickedCharacter());
        } else if (Battle.getChosenCharacter() != null) {
            return new BattleEvent(EventType.SHOW_CHAR2ENEMY, clickPoint, CharsDrawer.getClickedCharacter());
        }
        return null;
    }

}
