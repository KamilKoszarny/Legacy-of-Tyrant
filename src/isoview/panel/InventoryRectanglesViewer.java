package isoview.panel;

import controller.isoview.panel.ItemClickController;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.actions.ItemHandler;
import model.items.Item;

import java.awt.*;
import java.util.Map;

import static javafx.scene.AccessibleRole.IMAGE_VIEW;

public class InventoryRectanglesViewer {


    public static void drawInventoryRectangle(Rectangle rectangle) {
        Rectangle invFirstRect = ItemClickController.calcInventoryScreenRect(rectangle, new int[]{0, 0});
        rectangle.setX(invFirstRect.getX());
        rectangle.setY(invFirstRect.getY());
        ItemClickController.initInventoryClick(rectangle, false);
        rectangle.setVisible(true);
    }

    public static void redrawInventoryRect(Rectangle rectangle) {
        Pane pane = (Pane) rectangle.getParent();
        pane.getChildren().remove(rectangle);
        pane.getChildren().add(rectangle);
    }


    public static void refreshInventory(Map<Item, int[]> inventory, Rectangle invRectangle) {
        Pane pane = (Pane) invRectangle.getParent();
        InventoryRectanglesViewer.redrawInventoryRect(invRectangle);

        int[] itemInvPos;
        Rectangle itemInvFirstRect;
        for (Item item: inventory.keySet()) {
            itemInvPos = inventory.get(item);
            itemInvFirstRect = ItemClickController.calcInventoryScreenRect(invRectangle, itemInvPos);
            Rectangle inventoryItemRect = new Rectangle(itemInvFirstRect.getX(), itemInvFirstRect.getY(),
                    item.getImage().getWidth(), item.getImage().getHeight());
            inventoryItemRect.setFill(new ImagePattern(item.getImage()));

            String name = item.getName();
            Tooltip tooltip = new Tooltip(name);
            Tooltip.install(inventoryItemRect, tooltip);

            if (!invRectangle.equals(CharPanelViewer.getInventoryRect())) {
                inventoryItemRect.getProperties().put(IMAGE_VIEW, "chestItem");
            }
            ItemClickController.initInventoryItemClick(inventoryItemRect, item, inventory);
            pane.getChildren().add(inventoryItemRect);
        }
    }

    public static Rectangle createInventoryRectangle(Point pos) {
        javafx.scene.image.Image inventoryImg = new Image("/items/inventory.png");
        Rectangle inventoryRect = new Rectangle(pos.getX(), pos.getY(),
                ItemHandler.INVENTORY_SLOTS_X * ItemHandler.ITEM_SLOT_SIZE, ItemHandler.INVENTORY_SLOTS_Y * ItemHandler.ITEM_SLOT_SIZE);
        inventoryRect.setFill(new ImagePattern(inventoryImg));
        Pane pane = (Pane) PanelViewer.getPanel().getHeldItemRect().getParent();
        inventoryRect.setVisible(true);
        pane.getChildren().add(inventoryRect);
        return inventoryRect;
    }
}
