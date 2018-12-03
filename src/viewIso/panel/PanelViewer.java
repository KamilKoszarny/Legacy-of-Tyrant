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
    private static CharDescriptor charDescriptor;
    private static Rectangle heldItemRect;
    private static Item heldItem;
    private static Point catchPoint;
    private static Image mapImg, minimapImg;
    private static Rectangle miniMapRect;

    public PanelViewer(Panel panel, List<Character> characters) {
        PanelViewer.panel = panel;
        this.heldItemRect = panel.getHeldItemRect();
        charDescriptor = new CharDescriptor(PanelViewer.panel, characters);
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

    private static void refreshMinimap(Panel panel) {

    }

    public static void refresh() {
        charDescriptor.refresh();
        if (heldItem != null)
            drawCaughtItem();
        else
            heldItemRect.setVisible(false);
        refreshMinimap(panel);
    }

    private void drawMinimap() {
        Rectangle miniMapRect = panel.getMiniMapRect();
        Image minimapImg = MapImageGenerator.getMapImage().getImage();
//        miniMapRect.setFill();
    }

    private static void drawCaughtItem() {
        Image itemImage = heldItem.getImage();
        heldItemRect.setFill(new ImagePattern(itemImage));
        heldItemRect.setX(MouseInfo.getPointerInfo().getLocation().x - catchPoint.x);
        heldItemRect.setY(MouseInfo.getPointerInfo().getLocation().y - catchPoint.y);
        heldItemRect.setWidth(itemImage.getWidth());
        heldItemRect.setHeight(itemImage.getHeight());
        heldItemRect.toFront();
        heldItemRect.setVisible(true);
    }

    public static void setHeldItem(Item heldItem, Point catchPoint) {
        PanelViewer.heldItem = heldItem;
        PanelViewer.catchPoint = catchPoint;
        if (heldItem == null) {
            PanelViewer.catchPoint = new Point(0, 0);
            IsoBattleLoop.setClickedItemPoint(new Point(0, 0));
        }
    }
}
