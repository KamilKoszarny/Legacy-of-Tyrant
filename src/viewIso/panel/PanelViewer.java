package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.IsoBattleLoop;
import model.character.Character;
import model.items.Item;
import viewIso.map.MapImageGenerator;

import java.awt.*;
import java.util.List;

public class PanelViewer {

    private static Panel panel;
    private CharDescriptor charDescriptor;
    private Rectangle caughtItemRect;
    private Item heldItem;
    private Point catchPoint;
    private static Image mapImg, minimapImg;
    private static Rectangle miniMapRect;

    public PanelViewer(Panel panel, List<Character> characters, Rectangle caughtItemRect) {
        this.panel = panel;
        this.caughtItemRect = caughtItemRect;
        charDescriptor = new CharDescriptor(this.panel, characters);
        initMinimapImage();
    }

    public static void setMapImg(Image mapImg) {
        PanelViewer.mapImg = mapImg;
    }

    private static void initMinimapImage() {
        ImageView minimapImageView = new ImageView(mapImg);
        miniMapRect = panel.getMiniMapRect();
        minimapImageView.setFitWidth(miniMapRect.getWidth());
        minimapImageView.setFitHeight(miniMapRect.getHeight() / 2);
        minimapImageView.setY(150);
        miniMapRect.setFill(new ImagePattern(mapImg));
    }

    private void refreshMinimap(Panel panel) {

    }

    public void refresh() {
        charDescriptor.refresh();
        if (heldItem != null)
            drawCaughtItem();
        else
            caughtItemRect.setVisible(false);
        refreshMinimap(panel);
    }

    private void drawMinimap() {
        Rectangle miniMapRect = panel.getMiniMapRect();
        Image minimapImg = MapImageGenerator.getMapImage().getImage();
//        miniMapRect.setFill();
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

    public void setHeldItem(Item heldItem, Point catchPoint) {
        this.heldItem = heldItem;
        this.catchPoint = catchPoint;
        if (heldItem == null) {
            this.catchPoint = new Point(0, 0);
            IsoBattleLoop.setClickedItemPoint(new Point(0, 0));
        }
    }
}
