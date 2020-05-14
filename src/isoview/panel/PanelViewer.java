package isoview.panel;

import controller.isoview.map.IsoMapBorderHoverController;
import controller.isoview.panel.Panel;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Battle;
import model.TurnsTracker;
import model.actions.ItemHandler;

import java.awt.*;

public class PanelViewer {

    public static final int PANEL_HEIGHT = 200;
    private static final Color PANEL_COLOR = Color.DARKGRAY;

    private static Panel panel;
    private static HBox panelHBox;
    private static Rectangle heldItemRect;
    private static Rectangle inactiveCoverRect;

    public PanelViewer(Panel panel) {
        PanelViewer.panel = panel;
        panelHBox = panel.getPanelHBox();
        heldItemRect = panel.getHeldItemRect();
        initPanelLook();
        new CharPanelViewer(panel, Battle.getCharacters());
        new MinimapViewer(panel);
        new NextTurnButtonViewer(panel);

        initInactiveCoverRect();
    }

    private static void initPanelLook() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panelHBox.setLayoutX(0);
        panelHBox.setLayoutY(screenSize.height - PANEL_HEIGHT);
        panelHBox.setPrefWidth(screenSize.width);
        panelHBox.setPrefHeight(PANEL_HEIGHT);
        panelHBox.setBackground(new Background(new BackgroundFill(PANEL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        panelHBox.setBorder(new Border(new BorderStroke(PANEL_COLOR.darker(),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    }

    private static void initInactiveCoverRect() {
        double minimapPaneLayout = panel.getMiniMapRect().getParent().getLayoutX();
        inactiveCoverRect = new Rectangle(
                panelHBox.getLayoutX(), panelHBox.getLayoutY(), minimapPaneLayout, panelHBox.getPrefHeight());
        inactiveCoverRect.setFill(Color.gray(0.1, 0.6));
        Pane pane = (Pane) panelHBox.getParent();
        pane.getChildren().add(inactiveCoverRect);
        inactiveCoverRect.toFront();
        IsoMapBorderHoverController.borderCanvasesToFront();
    }

    public static void refresh() {
        CharPanelViewer.refresh();
        refreshHeldItemRect();
        MinimapViewer.refreshMinimap();
        refreshInactiveCoverRect();
    }

    private static void refreshHeldItemRect() {
        if (ItemHandler.getHeldItem() != null)
            drawHeldItem();
        else
            heldItemRect.setVisible(false);
    }

    private static void refreshInactiveCoverRect() {
        if (Battle.isTurnMode() && (!TurnsTracker.activeCharChosen() || TurnsTracker.activeCharOutOfAP()))
            inactiveCoverRect.setVisible(true);
        else
            inactiveCoverRect.setVisible(false);
    }

    private static void drawHeldItem() {
        Image itemImage = ItemHandler.getHeldItem().getImage();
        heldItemRect.setFill(new ImagePattern(itemImage));
        heldItemRect.setX(MouseInfo.getPointerInfo().getLocation().x - ItemHandler.getHeldPoint().x);
        heldItemRect.setY(MouseInfo.getPointerInfo().getLocation().y - ItemHandler.getHeldPoint().y);
        heldItemRect.setWidth(itemImage.getWidth());
        heldItemRect.setHeight(itemImage.getHeight());
        heldItemRect.toFront();
        heldItemRect.setVisible(true);
    }

    public static Panel getPanel() {
        return panel;
    }
}
