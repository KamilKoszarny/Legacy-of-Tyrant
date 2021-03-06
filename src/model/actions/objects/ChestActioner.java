package model.actions.objects;


import controller.isoview.panel.ItemClickController;
import isoview.map.MapDrawer;
import isoview.panel.InventoryRectanglesViewer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.character.Character;
import model.map.buildings.Chest;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.AccessibleRole.IMAGE_VIEW;

public class ChestActioner {

    private static Chest chest;
    private static Rectangle inventoryRect;

    public static void openChest(Character character, Chest chest, Point clickPoint) {
        ChestActioner.chest = chest;
        openChestInventory(clickPoint);
        InventoryRectanglesViewer.refreshInventory(chest.getInventory(), inventoryRect);
    }

    private static void openChestInventory(Point clickPoint) {
        final int INV_POS_OFFSET = 10;
        Point invPos = new Point(clickPoint.x + INV_POS_OFFSET, clickPoint.y + INV_POS_OFFSET);
        inventoryRect = InventoryRectanglesViewer.createInventoryRectangle(invPos);
        inventoryRect.getProperties().put(IMAGE_VIEW, "chestInventory");
        ItemClickController.initInventoryClick(inventoryRect, true);
    }

    public static void hideChestInventory() {
        if (inventoryRect != null) {
            Pane pane = (Pane) inventoryRect.getParent();
            pane.getChildren().removeAll(chestItemNodes());
            pane.getChildren().remove(inventoryRect);
            inventoryRect = null;
        }
    }

    public static void moveChestInventory(Point mapMove) {
        if (inventoryRect != null) {
            inventoryRect.setTranslateX(inventoryRect.getTranslateX() + mapMove.x * MapDrawer.MAP_PIECE_SCREEN_SIZE_X);
            inventoryRect.setTranslateY(inventoryRect.getTranslateY() + mapMove.y * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y);
            for (Node node : chestItemNodes()) {
                node.setTranslateX(node.getTranslateX() + mapMove.x * MapDrawer.MAP_PIECE_SCREEN_SIZE_X);
                node.setTranslateY(node.getTranslateY() + mapMove.y * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y);
            }
        }
    }

    private static List<Node> chestItemNodes() {
        List<Node> chestItemNodes = new ArrayList<>();
        Pane pane = (Pane) inventoryRect.getParent();
        for (Node node: pane.getChildren()) {
            if (node.getProperties().containsValue("chestItem")) {
                chestItemNodes.add(node);
            }
        }
        return chestItemNodes;
    }

    public static Chest getChest() {
        return chest;
    }

    public static Rectangle getInventoryRect() {
        return inventoryRect;
    }
}
