package controller.isoView.isoPanel;

import helpers.my.GeomerticHelper;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.items.armor.*;

import java.util.List;

public class Panel {


    private List<Label> charStatsLabels;
    private List<ProgressBar> charBars;
    private List<Rectangle> itemRectangles;
    private Rectangle portraitRect, charPictBackgroundRect;
    private Rectangle heldItemRect;
    private Pane inventoryGridPane;
    private Rectangle inventoryScreenRect, miniMapRect, miniMapPosRect;
    private Canvas minimapFogCanvas;

    public Panel(List<Label> charStatsLabels, List<ProgressBar> charBars, Rectangle portraitRect, Rectangle charPictBackgroundRect,
                 List<Rectangle> itemRectangles, Rectangle heldItemRect, Pane inventoryGridPane,
                 Rectangle miniMapRect, Rectangle miniMapPosRect, Canvas minimapFogCanvas) {
        this.charStatsLabels = charStatsLabels;
        this.charBars = charBars;
        this.itemRectangles = itemRectangles;
        this.portraitRect = portraitRect;
        this.charPictBackgroundRect = charPictBackgroundRect;
        this.heldItemRect = heldItemRect;
        heldItemRect.setVisible(false);
        this.inventoryGridPane = inventoryGridPane;
        this.miniMapRect = miniMapRect;
        this.miniMapPosRect = miniMapPosRect;
        this.minimapFogCanvas = minimapFogCanvas;
//        initInventoryClick();
    }

    private void initInventoryClick() {
        inventoryScreenRect = getInventoryRectangle();
        inventoryScreenRect.setOnMouseClicked(mouseEvent -> {
            System.out.println("inv click");
        });
    }

    public List<Label> getCharStatsLabels() {
        return charStatsLabels;
    }

    public List<ProgressBar> getCharBars() {
        return charBars;
    }

    public Rectangle getPortraitRect() {
        return portraitRect;
    }

    public Rectangle getCharPortraitBackgroundRect() {
        return charPictBackgroundRect;
    }

    public Rectangle getWeaponRect() {
        return itemRectangles.get(0);
    }

    public Rectangle getSpareWeaponRect() {
        return itemRectangles.get(1);
    }

    public Rectangle getShieldRect() {
        return itemRectangles.get(2);
    }

    public Rectangle getArmorRect() {
        return itemRectangles.get(3);
    }

    public Rectangle getHelmetRect() {
        return itemRectangles.get(4);
    }

    public Rectangle getGlovesRect() {
        return itemRectangles.get(5);
    }

    public Rectangle getBootsRect() {
        return itemRectangles.get(6);
    }

    public Rectangle getBeltRect() {
        return itemRectangles.get(7);
    }

    public Rectangle getAmuletRect() {
        return itemRectangles.get(8);
    }

    public Rectangle getRing1Rect() {
        return itemRectangles.get(9);
    }

    public Rectangle getRing2Rect() {
        return itemRectangles.get(10);
    }

    public Rectangle getSpareShieldRect() {
        return itemRectangles.get(11);
    }

    public List<Rectangle> getItemRectangles() {
        return itemRectangles;
    }

    public Rectangle getHeldItemRect() {
        return heldItemRect;
    }

    public Rectangle getInventoryRectangle() {
        if (inventoryScreenRect == null)
            return GeomerticHelper.screenRectangle(inventoryGridPane);
        return inventoryScreenRect;
    }

    public Rectangle getMiniMapRect() {
        return miniMapRect;
    }

    public Rectangle getMiniMapPosRect() {
        return miniMapPosRect;
    }

    public Canvas getMinimapFogCanvas() {
        return minimapFogCanvas;
    }
}
