package controller.isoView.isoPanel;

import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import model.IsoBattleLoop;
import model.actions.ItemHandler;
import model.items.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemClickController {

    private static Panel panel;

    ItemClickController(Panel panel) {
        ItemClickController.panel = panel;
        initialize();
    }

    private static void initialize(){
        initItemClick();
    }

    private static void initItemClick() {
        panel.getHeldItemRect().setMouseTransparent(true);

        for (Rectangle rectangle : panel.getItemRectangles()) {
            rectangle.setOnMouseClicked(mouseEvent -> {
                IsoBattleLoop.setClickedInventorySlot(new int[]{-10, panel.getItemRectangles().indexOf(rectangle)});
                IsoBattleLoop.setClickedItemPoint(new Point((int) mouseEvent.getX(), (int) mouseEvent.getY()));
                IsoBattleLoop.setItemClickFlag(true);
            });
        }

//        initInventoryClick();
    }

    public static void initInventoryClick(Rectangle inventoryRect, boolean chest) {
        inventoryRect.setOnMousePressed(mouseEvent -> {
            int[] inventorySlot = checkInventorySlot(new Point((int) (mouseEvent.getX()), (int) (mouseEvent.getY())), inventoryRect);
            if (inventoryRect.getProperties().containsValue("chestInventory"))
                inventorySlot[0] += 100;
            IsoBattleLoop.setClickedInventorySlot(inventorySlot);
            IsoBattleLoop.setItemClickFlag(true);
        });
    }

    public static void initInventoryItemClick(Rectangle rectangle, Item item, Map<Item, int[]> inventory) {
        rectangle.setOnMousePressed(mouseEvent -> {
            Point clickPoint = new Point((int) (mouseEvent.getX() - rectangle.getX()), (int) (mouseEvent.getY() - rectangle.getY()));
            int[] inventorySlot = ItemHandler.inventorySlotByItemClickPoint(item, inventory, clickPoint);
            if (rectangle.getProperties().containsValue("chestItem"))
                inventorySlot[0] += 100;
            IsoBattleLoop.setClickedInventorySlot(inventorySlot);
            IsoBattleLoop.setClickedItemPoint(clickPoint);
            IsoBattleLoop.setItemClickFlag(true);
        });
    }

    public static Rectangle checkEquipmentSlot(Point clickPoint) {
        Rectangle itemRectangle = null, screenRectangle;
        for (Rectangle rectangle : panel.getItemRectangles()) {
            screenRectangle = GeomerticHelper.screenRectangle(rectangle);
            if (screenRectangle.contains(new Point2D(clickPoint.x, clickPoint.y)))
                itemRectangle = rectangle;
        }
        return itemRectangle;
    }

    private static int[] checkInventorySlot(Point clickPoint, Rectangle rectangle) {
        int[] inventorySlot = null;
        List<Rectangle> slotScreenRects = calcInventoryScreenRects(rectangle);
        for (Rectangle subRectangle : slotScreenRects) {
            if (subRectangle.contains(new Point2D(clickPoint.x, clickPoint.y))) {
                int index = slotScreenRects.indexOf(subRectangle);
                inventorySlot = new int[]{index % ItemHandler.INVENTORY_SLOTS_X, index / ItemHandler.INVENTORY_SLOTS_X};
            }
        }

        return inventorySlot;
    }

    private static List<Rectangle> calcInventoryScreenRects(Rectangle rectangle) {
        List<Rectangle> slotScreenRects = new ArrayList<>();
        double slotSizeX = rectangle.getWidth() / ItemHandler.INVENTORY_SLOTS_X;
        double slotSizeY = rectangle.getHeight() / ItemHandler.INVENTORY_SLOTS_Y;
        for (int y = 0; y < ItemHandler.INVENTORY_SLOTS_Y; y++) {
            for (int x = 0; x < ItemHandler.INVENTORY_SLOTS_X; x++) {
                slotScreenRects.add(new Rectangle(
                        rectangle.getX() + x * slotSizeX, rectangle.getY() + y * slotSizeY,
                        slotSizeX, slotSizeY));
            }
        }
        return slotScreenRects;
    }

    public static Rectangle calcInventoryScreenRect(Rectangle inventoryRectangle, int[] pos) {
        double slotSizeX = inventoryRectangle.getWidth() / ItemHandler.INVENTORY_SLOTS_X;
        double slotSizeY = inventoryRectangle.getHeight() / ItemHandler.INVENTORY_SLOTS_Y;

        return new Rectangle(inventoryRectangle.getX() + inventoryRectangle.getTranslateX() + pos[0] * slotSizeX,
                inventoryRectangle.getY() + inventoryRectangle.getTranslateY() + pos[1] * slotSizeY,
                slotSizeX, slotSizeY);
    }
}
