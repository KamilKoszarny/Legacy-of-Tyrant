package controller.isoView.isoPanel;

import helpers.my.GeomerticHelper;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.IsoBattleLoop;
import model.actions.ItemHandler;
import viewIso.map.MapDrawer;
import viewIso.panel.PanelViewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelController {

    public static final int PANEL_HEIGHT = 200;
    private static final Color PANEL_COLOR = Color.DARKGRAY;

    private static HBox panelHBox;
    private static Panel panel;


    public PanelController(HBox panelHbox, Panel panel) {
        PanelController.panelHBox = panelHbox;
        PanelController.panel = panel;

        initialize();
    }

    public void initialize(){
        initPanelLook();
        initItemClick();
        initMinimapClick();
    }

    private void initPanelLook() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panelHBox.setLayoutX(0);
        panelHBox.setLayoutY(screenSize.height - PANEL_HEIGHT);
        panelHBox.setPrefWidth(screenSize.width);
        panelHBox.setPrefHeight(PANEL_HEIGHT);
        panelHBox.setBackground(new Background(new BackgroundFill(PANEL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        panelHBox.setBorder(new Border(new BorderStroke(PANEL_COLOR.darker(),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    }

    private void initItemClick() {
        panel.getHeldItemRect().setMouseTransparent(true);

        for (javafx.scene.shape.Rectangle rectangle: panel.getEquipmentSlots()) {
            rectangle.setOnMouseClicked(mouseEvent -> {
                IsoBattleLoop.setClickedInventorySlot(new int[]{-10, panel.getEquipmentSlots().indexOf(rectangle)});
                IsoBattleLoop.setClickedItemPoint(new Point((int)mouseEvent.getX(), (int)mouseEvent.getY()));
                IsoBattleLoop.setItemClickFlag(true);
            });
        }

//        initInventoryClick();
    }

    public static void initInventoryClick(javafx.scene.shape.Rectangle rect) {
        rect.setOnMousePressed(mouseEvent -> {
            Point shapeClickPoint = IsoBattleLoop.getClickedItemPoint();
            int[] inventorySlot = checkInventorySlot(new Point((int)(mouseEvent.getX()), (int)(mouseEvent.getY())), shapeClickPoint);
            System.out.println(inventorySlot[0] + " " + inventorySlot[1]);
            IsoBattleLoop.setClickedInventorySlot(inventorySlot);
            IsoBattleLoop.setItemClickFlag(true);
        });
    }

    private static void initMinimapClick() {
        panel.getMiniMapRect().setOnMousePressed(PanelController::minimapMouseAction);
        panel.getMiniMapRect().setOnMouseDragged(PanelController::minimapMouseAction);
    }

    private static void minimapMouseAction(MouseEvent mouseEvent) {
        int clickScreenX = (int) (PanelViewer.minimapScreenSize/2 + (mouseEvent.getX() - mouseEvent.getY()) / Math.sqrt(2));
        int clickScreenY = (int) ((mouseEvent.getX() + mouseEvent.getY()) / Math.sqrt(2));

        MapDrawer.setZeroScreenPosition(
                (- clickScreenX + PanelViewer.getMinimapRect().getWidth()/2 + PanelViewer.getMinimapPosRect().getWidth())
                        / PanelViewer.minimapToScreenRatioX ,
                (- clickScreenY + PanelViewer.getMinimapPosRect().getHeight()/2 + MapDrawer.getMap().getHeightType().getHilly() / 10)
                        / PanelViewer.minimapToScreenRatioY);
    }

    private static javafx.scene.shape.Rectangle checkEquipmentSlot(Point clickPoint) {
        javafx.scene.shape.Rectangle itemRectangle = null, screenRectangle;
        for (javafx.scene.shape.Rectangle rectangle: panel.getEquipmentSlots()) {
            screenRectangle = GeomerticHelper.screenRectangle(rectangle);
            if (screenRectangle.contains(new Point2D(clickPoint.x, clickPoint.y)))
                itemRectangle = rectangle;
        }
        return itemRectangle;
    }

    private static int[] checkInventorySlot(Point clickPoint, Point shapeClickPoint) {
        int[] inventorySlot = null;
        java.util.List<javafx.scene.shape.Rectangle> slotScreenRects = calcInventoryScreenRects(panel);
        for (javafx.scene.shape.Rectangle rectangle: slotScreenRects) {
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

    private static java.util.List<javafx.scene.shape.Rectangle> calcInventoryScreenRects(Panel panel){
        javafx.scene.shape.Rectangle screenInventoryRect = panel.getInventoryRectangle();
        List<javafx.scene.shape.Rectangle> slotScreenRects = new ArrayList<>();
        double slotSizeX = screenInventoryRect.getWidth() / ItemHandler.INVENTORY_X;
        double slotSizeY = screenInventoryRect.getHeight() / ItemHandler.INVENTORY_Y;
        for (int y = 0; y < ItemHandler.INVENTORY_Y; y++) {
            for (int x = 0; x < ItemHandler.INVENTORY_X; x++) {
                slotScreenRects.add(new javafx.scene.shape.Rectangle(
                        screenInventoryRect.getX() + x * slotSizeX, screenInventoryRect.getY() + y * slotSizeY,
                        slotSizeX, slotSizeY));
            }
        }
        return slotScreenRects;
    }

    public static javafx.scene.shape.Rectangle calcInventoryScreenRect(Panel panel, int[] pos){
        javafx.scene.shape.Rectangle screenInventoryRect = panel.getInventoryRectangle();
        double slotSizeX = screenInventoryRect.getWidth() / ItemHandler.INVENTORY_X;
        double slotSizeY = screenInventoryRect.getHeight() / ItemHandler.INVENTORY_Y;

        return new Rectangle(screenInventoryRect.getX() + pos[0] * slotSizeX, screenInventoryRect.getY() + pos[1] * slotSizeY,
                slotSizeX, slotSizeY);
    }
}
