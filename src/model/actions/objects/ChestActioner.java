package model.actions.objects;


import controller.isoView.isoPanel.PanelController;
import javafx.scene.shape.Rectangle;
import model.map.buildings.Furniture;
import viewIso.panel.CharDescriptor;

import java.awt.*;

public class ChestActioner {

    private static Rectangle inventoryRect;
    private static Furniture chest;

    public static void openChest(Furniture chest, Point clickPoint) {
        ChestActioner.chest = chest;
        openChestInventory(clickPoint);

        System.out.println("chest open");

    }

    private static void openChestInventory(Point clickPoint) {
        final int INV_POS_OFFSET = 10;
        Point invPos = new Point(clickPoint.x + INV_POS_OFFSET, clickPoint.y + INV_POS_OFFSET);
        inventoryRect = CharDescriptor.initInvRect(invPos);
        PanelController.initInventoryClick(inventoryRect, true);
    }

    public static void hideChestInventory() {
        inventoryRect = null;
    }
}
