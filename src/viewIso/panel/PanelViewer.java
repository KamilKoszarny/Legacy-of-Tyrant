package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.IsoBattleLoop;
import model.character.Character;
import model.items.Item;
import model.map.Map;
import viewIso.IsoViewer;
import viewIso.map.MapDrawer;

import java.awt.*;
import java.util.List;

public class PanelViewer {

    private static Panel panel;
    private static CharDescriptor charDescriptor;
    private static Rectangle heldItemRect;
    private static Item heldItem;
    private static Point catchPoint;
    private static Image mapImg, minimapImg;
    private static Rectangle minimapRect, minimapPosRect;
    public static double minimapScreenSize;
    public static double minimapToScreenRatioX, minimapToScreenRatioY;

    public PanelViewer(Panel panel, List<Character> characters) {
        PanelViewer.panel = panel;
        heldItemRect = panel.getHeldItemRect();
        charDescriptor = new CharDescriptor(PanelViewer.panel, characters);
        initMinimapImage();
    }

    public static void refresh() {
        charDescriptor.refresh();
        if (heldItem != null)
            drawCaughtItem();
        else
            heldItemRect.setVisible(false);
        refreshMinimap();
    }

    private static void initMinimapImage() {
        minimapRect = panel.getMiniMapRect();
        minimapPosRect = panel.getMiniMapPosRect();
        minimapRect.setFill(new ImagePattern(mapImg));

        initPosRect();
    }

    private static void initPosRect() {
        minimapScreenSize = minimapRect.getWidth() * Math.sqrt(2);
        int mapScreenWidth = Map.mapXPoints * MapDrawer.MAP_PIECE_SCREEN_SIZE_X;
        int mapScreenHeight = Map.mapXPoints * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y;
        minimapToScreenRatioX = minimapScreenSize / mapScreenWidth;
        minimapToScreenRatioY = minimapScreenSize / mapScreenHeight;
        minimapPosRect.setWidth(IsoViewer.getCanvas().getWidth() * minimapToScreenRatioX);
        minimapPosRect.setHeight(IsoViewer.getCanvas().getHeight() * minimapToScreenRatioY);
    }

    private static void refreshMinimap() {
        minimapRect.setFill(new ImagePattern(mapImg));

        minimapPosRect.setTranslateX(minimapPosRect.getWidth()/2
                - MapDrawer.getZeroScreenPosition().getX() * minimapToScreenRatioX);
        minimapPosRect.setTranslateY(- minimapScreenSize/2 + minimapPosRect.getHeight()/2
                - MapDrawer.getZeroScreenPosition().getY() * minimapToScreenRatioY
                + MapDrawer.getMap().getHeightType().getHilly() / 10);
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

    public static void setMapImg(Image mapImg) {
        PanelViewer.mapImg = mapImg;
    }

    public static void setHeldItem(Item heldItem, Point catchPoint) {
        PanelViewer.heldItem = heldItem;
        PanelViewer.catchPoint = catchPoint;
        if (heldItem == null) {
            PanelViewer.catchPoint = new Point(0, 0);
            IsoBattleLoop.setClickedItemPoint(new Point(0, 0));
        }
    }

    public static Rectangle getMinimapRect() {
        return minimapRect;
    }

    public static Rectangle getMinimapPosRect() {
        return minimapPosRect;
    }
}
