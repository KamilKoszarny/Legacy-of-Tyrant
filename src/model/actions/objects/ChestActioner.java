package model.actions.objects;


import controller.isoView.isoPanel.PanelController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.map.buildings.Chest;
import model.map.buildings.Furniture;
import viewIso.panel.CharPanelViewer;
import viewIso.panel.InventoryRectanglesViewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.AccessibleRole.IMAGE_VIEW;

public class ChestActioner {

    private static Chest chest;
    private static Rectangle inventoryRect;

    public static void openChest(Chest chest, Point clickPoint) {
        ChestActioner.chest = chest;
        openChestInventory(clickPoint);
        InventoryRectanglesViewer.refreshInventory(chest.getInventory(), inventoryRect);
    }

    private static void openChestInventory(Point clickPoint) {
        final int INV_POS_OFFSET = 10;
        Point invPos = new Point(clickPoint.x + INV_POS_OFFSET, clickPoint.y + INV_POS_OFFSET);
        inventoryRect = CharPanelViewer.initInvRect(invPos);
        inventoryRect.getProperties().put(IMAGE_VIEW, "chestInventory");
        PanelController.initInventoryClick(inventoryRect, true);
    }

    public static void hideChestInventory() {
        if (inventoryRect != null) {
            Pane pane = (Pane) inventoryRect.getParent();
            pane.getChildren().remove(inventoryRect);
            List<Node> nodesToRemove = new ArrayList<>();
            for (Node node: pane.getChildren()) {
                if (node.getProperties().containsValue("chestItem"))
                    nodesToRemove.add(node);
            }
            pane.getChildren().removeAll(nodesToRemove);
            inventoryRect = null;
        }
    }

    private static void redrawInventoryRect(Pane pane) {
        pane.getChildren().remove(inventoryRect);
        pane.getChildren().add(inventoryRect);
    }



    public static Chest getChest() {
        return chest;
    }

    public static Rectangle getInventoryRect() {
        return inventoryRect;
    }
}
