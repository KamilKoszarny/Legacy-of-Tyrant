package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.image.Image;
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
    Item catchItem;
    Point catchPoint;

    public PanelViewer(Panel panel, List<Character> characters, Rectangle caughtItemRect) {
        this.panel = panel;
        this.caughtItemRect = caughtItemRect;
        charDescriptor = new CharDescriptor(this.panel, characters);
    }

    public void refresh() {
        charDescriptor.refresh();
        if (catchItem != null)
            drawCaughtItem();
        else
            caughtItemRect.setVisible(false);
    }

    private void drawCaughtItem() {
        Image itemImage = catchItem.getImage();
        caughtItemRect.setFill(new ImagePattern(itemImage));
        caughtItemRect.setX(MouseInfo.getPointerInfo().getLocation().x - catchPoint.x);
        caughtItemRect.setY(MouseInfo.getPointerInfo().getLocation().y - catchPoint.y);
        caughtItemRect.setWidth(itemImage.getWidth());
        caughtItemRect.setHeight(itemImage.getHeight());
        caughtItemRect.setVisible(true);
    }

    public void setCatchItem(Item catchItem) {
        this.catchItem = catchItem;
    }

    public void setCatchPoint(Point catchPoint) {
        this.catchPoint = catchPoint;
    }
}
