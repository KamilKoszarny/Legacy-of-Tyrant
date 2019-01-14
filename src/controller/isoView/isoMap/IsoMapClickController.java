package controller.isoView.isoMap;

import controller.isoView.isoPanel.Panel;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import model.Battle;
import model.BattleEvent;
import model.EventType;
import model.IsoBattleLoop;
import model.actions.ItemHandler;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.mapObjects.MapObjectDrawer;
import viewIso.panel.PanelViewer;

import java.awt.*;

public class IsoMapClickController {

    private Canvas mapCanvas;

    public IsoMapClickController(Canvas mapCanvas, Panel panel) {
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
            }
        });
    }

    private BattleEvent eventByLClickPoint(Point clickPoint) {
        if (CharsDrawer.isOtherCharClicked(clickPoint, Battle.getChosenCharacter())) {
            if (CharsDrawer.getClickedCharacter().getColor().equals(Battle.getPlayerColor())) {
                if (ItemHandler.getHeldItem() == null)
                    return new BattleEvent(EventType.CHOOSE_CHARACTER, clickPoint);
                else
                    return new BattleEvent(EventType.GIVE_ITEM, CharsDrawer.getClickedCharacter());
            } else if (Battle.getChosenCharacter() != null) {
                return new BattleEvent(EventType.SHOW_CHAR2ENEMY, clickPoint, CharsDrawer.getClickedCharacter());
            }
        } else {
            Point mapPoint = MapDrawCalculator.mapPointByClickPoint(clickPoint);
            if (Battle.getChosenCharacter() != null && mapPoint != null) {
                MapObject object = MapObjectDrawer.clickedObject(clickPoint);
                if (object != null && object.getType().equals(MapObjectType.DOOR))
                    return new BattleEvent(EventType.SHOW_CHAR2OBJECT, clickPoint, object);
                else
                    return new BattleEvent(EventType.SHOW_CHAR2POINT, clickPoint, mapPoint);
            }
        }
        return null;
    }

}
