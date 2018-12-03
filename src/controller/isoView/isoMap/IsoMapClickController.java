package controller.isoView.isoMap;

import controller.isoView.isoPanel.Panel;
import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IsoMapClickController {

    private Canvas mapCanvas;
    private static Panel panel;

    public IsoMapClickController(Canvas mapCanvas, Panel panel) {
        this.mapCanvas = mapCanvas;
        IsoMapClickController.panel = panel;

        initialize();
    }

    public void initialize(){
        initCanvasClick();
        initItemClick();
    }

    private void initCanvasClick(){
        mapCanvas.setOnMouseClicked(mouseEvent -> {
            Point clickPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                IsoBattleLoop.setBattleEvent(eventByLClickPoint(clickPoint));
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                IsoBattleLoop.setBattleEvent(new BattleEvent(EventType.MAP_PIECE_INFO, clickPoint,
                        MapDrawCalculator.mapPointByClickPoint(clickPoint), MapDrawCalculator.mapPieceByClickPoint(clickPoint)));
            }
        });
    }

    private BattleEvent eventByLClickPoint(Point clickPoint) {
        BattleEvent battleEvent = null;
        if (CharsDrawer.isOtherCharClicked(clickPoint, Battle.getChosenCharacter())) {
            battleEvent = new BattleEvent(EventType.CHOOSE_CHARACTER, clickPoint);
        } else {
            Point mapPoint = MapDrawCalculator.mapPointByClickPoint(clickPoint);
            if (Battle.getChosenCharacter() != null && mapPoint != null) {
                MapObject object = MapObjectDrawer.clickedObject(clickPoint);
                if (object != null && object.getType().equals(MapObjectType.DOOR))
                    battleEvent = new BattleEvent(EventType.CHAR2OBJECT, clickPoint, object);
                else
                    battleEvent = new BattleEvent(EventType.CHAR2POINT, clickPoint, mapPoint);
            }
        }

        return battleEvent;
    }

    private void initItemClick() {
        panel.getHeldItemRect().setMouseTransparent(true);

        for (Rectangle rectangle: panel.getEquipmentSlots()) {
            rectangle.setOnMouseClicked(mouseEvent -> {
                IsoBattleLoop.setClickedInventorySlot(new int[]{-10, panel.getEquipmentSlots().indexOf(rectangle)});
                IsoBattleLoop.setClickedItemPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
                IsoBattleLoop.setItemClickFlag(true);
            });
        }
//
//        panel.getHeldItemRect().setOnMouseClicked(mouseEvent -> {
//            Point clickPoint = MouseInfo.getPointerInfo().getLocation();
//            Point shapeClickPoint = isoBattleLoop.getClickedItemPoint();
//            Rectangle equipmentSlot = checkEquipmentSlot(clickPoint);
//            if (equipmentSlot != null) {
//                isoBattleLoop.setClickedInventorySlot(new int[]{-10, panel.getEquipmentSlots().indexOf(equipmentSlot)});
//            } else {
//                int[] inventorySlot = checkInventorySlot(clickPoint, shapeClickPoint);
//                if(inventorySlot != null) {
//                    isoBattleLoop.setClickedInventorySlot(inventorySlot);
//                } else {
//                    isoBattleLoop.setClickedInventorySlot(null);
//                }
//            }
//            isoBattleLoop.setItemCatch(false);
//            isoBattleLoop.setItemClickFlag(true);
//        });

//        initInventoryClick();
    }

    public static void initInventoryClick(Rectangle rect) {
        rect.setOnMousePressed(mouseEvent -> {
            Point shapeClickPoint = IsoBattleLoop.getClickedItemPoint();
            int[] inventorySlot = checkInventorySlot(new Point((int)(mouseEvent.getX()), (int)(mouseEvent.getY())), shapeClickPoint);
            System.out.println(inventorySlot[0] + " " + inventorySlot[1]);
            IsoBattleLoop.setClickedInventorySlot(inventorySlot);
            IsoBattleLoop.setItemClickFlag(true);
        });
    }

    private static Rectangle checkEquipmentSlot(Point clickPoint) {
        Rectangle itemRectangle = null, screenRectangle;
        for (Rectangle rectangle: panel.getEquipmentSlots()) {
            screenRectangle = GeomerticHelper.screenRectangle(rectangle);
            if (screenRectangle.contains(new Point2D(clickPoint.x, clickPoint.y)))
                itemRectangle = rectangle;
        }
        return itemRectangle;
    }

    private static int[] checkInventorySlot(Point clickPoint, Point shapeClickPoint) {
        int[] inventorySlot = null;
        List<Rectangle> slotScreenRects = calcInventoryScreenRects(panel);
        for (Rectangle rectangle: slotScreenRects) {
            if (rectangle.contains(new Point2D(clickPoint.x, clickPoint.y))){
                int index = slotScreenRects.indexOf(rectangle);
                inventorySlot = new int[]{index % ItemHandler.INVENTORY_X, index / ItemHandler.INVENTORY_X};
            }
        }

        if (inventorySlot != null && shapeClickPoint != null) {
            inventorySlot[0] -= (int)(shapeClickPoint.getX() / ItemHandler.ITEM_SLOT_SIZE);
            inventorySlot[1] -= (int)(shapeClickPoint.getY() / ItemHandler.ITEM_SLOT_SIZE);
        }

        return inventorySlot;
    }

    public static List<Rectangle> calcInventoryScreenRects(Panel panel){
        Rectangle screenInventoryRect = panel.getInventoryRectangle();
        List<Rectangle> slotScreenRects = new ArrayList<>();
        double slotSizeX = screenInventoryRect.getWidth() / ItemHandler.INVENTORY_X;
        double slotSizeY = screenInventoryRect.getHeight() / ItemHandler.INVENTORY_Y;
        for (int y = 0; y < ItemHandler.INVENTORY_Y; y++) {
            for (int x = 0; x < ItemHandler.INVENTORY_X; x++) {
                slotScreenRects.add(new Rectangle(
                        screenInventoryRect.getX() + x * slotSizeX, screenInventoryRect.getY() + y * slotSizeY,
                        slotSizeX, slotSizeY));
            }
        }
        return slotScreenRects;
    }

    public static Rectangle calcInventoryScreenRect(Panel panel, int[] pos){
        Rectangle screenInventoryRect = panel.getInventoryRectangle();
        double slotSizeX = screenInventoryRect.getWidth() / ItemHandler.INVENTORY_X;
        double slotSizeY = screenInventoryRect.getHeight() / ItemHandler.INVENTORY_Y;

        return new Rectangle(screenInventoryRect.getX() + pos[0] * slotSizeX, screenInventoryRect.getY() + pos[1] * slotSizeY,
                        slotSizeX, slotSizeY);
    }

}
