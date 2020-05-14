package controller.isoview.panel;

import helpers.my.GeomerticHelper;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

import java.util.List;

@Getter
public class Panel {

    private HBox panelHBox;
    private List<Label> charStatsLabels;
    private List<Label> actionPointsLabels;
    private List<ProgressBar> charBars;
    private List<Rectangle> itemRectangles;
    private Rectangle portraitRect, charPictBackgroundRect;
    private Rectangle heldItemRect;
    private Pane inventoryGridPane;
    private Rectangle inventoryScreenRect, miniMapRect, miniMapPosRect;
    private Canvas minimapFogCanvas;
    private Button nextTurnButton;

    public Panel(HBox panelHBox, List<Label> charStatsLabels, List<Label> actionPointsLabels, List<ProgressBar> charBars, Rectangle portraitRect, Rectangle charPictBackgroundRect,
                 List<Rectangle> itemRectangles, Rectangle heldItemRect, Pane inventoryGridPane,
                 Rectangle miniMapRect, Rectangle miniMapPosRect, Canvas minimapFogCanvas, Button nextTurnButton) {
        this.panelHBox = panelHBox;
        this.charStatsLabels = charStatsLabels;
        this.actionPointsLabels = actionPointsLabels;
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
        this.nextTurnButton = nextTurnButton;
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

    public Rectangle getInventoryRectangle() {
        if (inventoryScreenRect == null)
            return GeomerticHelper.screenRectangle(inventoryGridPane);
        return inventoryScreenRect;
    }
}
