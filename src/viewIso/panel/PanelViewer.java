package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.character.Character;
import model.items.Item;

import java.awt.*;
import java.util.List;

public class PanelViewer {

    private Panel panel;
    private CharDescriptor charDescriptor;
    private Rectangle caughtItemRect;
    private Item heldItem;
    private Point catchPoint;

    public PanelViewer(Panel panel, List<Character> characters, Rectangle caughtItemRect) {
        this.panel = panel;
        this.caughtItemRect = caughtItemRect;
        charDescriptor = new CharDescriptor(this.panel, characters);
    }

    public void refresh() {
        charDescriptor.refresh();
        if (heldItem != null)
            drawCaughtItem();
        else
            caughtItemRect.setVisible(false);
    }

    private void drawCaughtItem() {
        Image itemImage = heldItem.getImage();
        caughtItemRect.setFill(new ImagePattern(itemImage));
        caughtItemRect.setX(MouseInfo.getPointerInfo().getLocation().x - catchPoint.x);
        caughtItemRect.setY(MouseInfo.getPointerInfo().getLocation().y - catchPoint.y);
        caughtItemRect.setWidth(itemImage.getWidth());
        caughtItemRect.setHeight(itemImage.getHeight());
        caughtItemRect.toFront();
        caughtItemRect.setVisible(true);
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    public void setCatchPoint(Point catchPoint) {
        this.catchPoint = catchPoint;
    }
}
