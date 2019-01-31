package viewIso.panel;

import static javafx.scene.AccessibleRole.IMAGE_VIEW;
import controller.isoView.isoPanel.PanelController;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.items.Item;
import viewIso.IsoViewer;

import java.util.Map;

public class InventoryRectanglesViewer {


    public static void redrawInventoryRect(Rectangle rectangle) {
        Pane pane = (Pane) rectangle.getParent();
        pane.getChildren().remove(rectangle);
        pane.getChildren().add(rectangle);
    }

    public static void drawInventoryRectangle(Rectangle rectangle) {
        Rectangle invFirstRect = PanelController.calcInventoryScreenRect(rectangle, new int[]{0, 0});
        rectangle.setX(invFirstRect.getX());
        rectangle.setY(invFirstRect.getY());
        initInventoryRectangle(rectangle);
    }

    private static void initInventoryRectangle(Rectangle rectangle) {
        PanelController.initInventoryClick(rectangle, false);
        rectangle.setVisible(true);
    }


    public static void refreshInventory(Map<Item, int[]> inventory, Rectangle invRectangle) {
        Pane pane = (Pane) invRectangle.getParent();
        InventoryRectanglesViewer.redrawInventoryRect(invRectangle);

        int[] itemInvPos;
        Rectangle itemInvFirstRect;
        for (Item item: inventory.keySet()) {
            itemInvPos = inventory.get(item);
            itemInvFirstRect = PanelController.calcInventoryScreenRect(invRectangle, itemInvPos);
            Rectangle inventoryItemRect = new Rectangle(itemInvFirstRect.getX(), itemInvFirstRect.getY(),
                    item.getImage().getWidth(), item.getImage().getHeight());
            inventoryItemRect.setFill(new ImagePattern(item.getImage()));

            String name = item.getName();
            Tooltip tooltip = new Tooltip(name);
            Tooltip.install(inventoryItemRect, tooltip);

            if (!invRectangle.equals(CharPanelViewer.getInventoryRect())) {
                inventoryItemRect.getProperties().put(IMAGE_VIEW, "chestItem");
            }
            PanelController.initInventoryItemClick(inventoryItemRect, item, inventory);
            pane.getChildren().add(inventoryItemRect);
        }
    }
}
