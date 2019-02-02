package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Battle;
import model.actions.ItemHandler;

import java.awt.*;

public class PanelViewer {

    private static Panel panel;
    private static Rectangle heldItemRect;

    public PanelViewer(Panel panel) {
        PanelViewer.panel = panel;
        heldItemRect = panel.getHeldItemRect();
        new CharPanelViewer(panel, Battle.getCharacters());
        new MinimapViewer(panel.getMiniMapRect(), panel.getMiniMapPosRect(), panel.getMinimapFogCanvas());
    }

    public static void refresh() {
        CharPanelViewer.refresh();
        if (ItemHandler.getHeldItem() != null)
            drawHeldItem();
        else
            heldItemRect.setVisible(false);
        MinimapViewer.refreshMinimap();
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
