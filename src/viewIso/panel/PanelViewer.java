package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import helpers.my.GeomerticHelper;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Battle;
import model.actions.ItemHandler;
import model.character.Character;
import viewIso.IsoViewer;
import viewIso.map.MapDrawer;

import java.awt.*;

public class PanelViewer {

    private static Panel panel;
    private static CharDescriptor charDescriptor;
    private static Rectangle heldItemRect;
    private static Image minimapImg;
    private static Rectangle minimapRect, minimapPosRect;
    public static double minimapScreenSize;
    public static double minimapToScreenRatioX, minimapToScreenRatioY;
    private static int choserCharRadius = 5;

    public PanelViewer(Panel panel) {
        PanelViewer.panel = panel;
        heldItemRect = panel.getHeldItemRect();
        charDescriptor = new CharDescriptor(PanelViewer.panel, Battle.getCharacters());
        initMinimap();
    }

    public static void refresh() {
        charDescriptor.refresh();
        if (ItemHandler.getHeldItem() != null)
            drawHeldItem();
        else
            heldItemRect.setVisible(false);
        refreshMinimap();
    }

    private static void initMinimap() {
        minimapRect = panel.getMiniMapRect();
        minimapPosRect = panel.getMiniMapPosRect();
        minimapRect.setFill(new ImagePattern(minimapImg));

        initSizes();
        minimapPosRect.setWidth(IsoViewer.getCanvas().getWidth() * minimapToScreenRatioX);
        minimapPosRect.setHeight(IsoViewer.getCanvas().getHeight() * minimapToScreenRatioY);
    }

    private static void initSizes() {
        minimapScreenSize = minimapRect.getWidth() * Math.sqrt(2);
        int mapScreenWidth = Battle.getMap().mapXPoints * MapDrawer.MAP_PIECE_SCREEN_SIZE_X;
        int mapScreenHeight = Battle.getMap().mapXPoints * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y;
        minimapToScreenRatioX = minimapScreenSize / mapScreenWidth;
        minimapToScreenRatioY = minimapScreenSize / mapScreenHeight;
    }

    private static void refreshMinimap() {
        WritableImage minimapImgWithChars = new WritableImage(minimapImg.getPixelReader(), (int) minimapImg.getWidth(), (int) minimapImg.getHeight());
        PixelWriter pixelWriter = minimapImgWithChars.getPixelWriter();
        for (Character character : Battle.getCharacters()) {
            int radius = calcMinimapRadius(character);
            for (Point point: GeomerticHelper.pointsInRadius(character.getPosition(), radius, Battle.getMap())) {
                pixelWriter.setColor(point.x, point.y, character.getColor());
            }
        }

        minimapRect.setFill(new ImagePattern(minimapImgWithChars));

        minimapPosRect.setTranslateX(minimapPosRect.getWidth()/2
                - MapDrawer.getZeroScreenPosition().getX() * minimapToScreenRatioX);
        minimapPosRect.setTranslateY(- minimapScreenSize/2 + minimapPosRect.getHeight()/2
                - MapDrawer.getZeroScreenPosition().getY() * minimapToScreenRatioY
                + MapDrawer.getMap().getHeightType().getHilly() / 10);
    }

    private static int calcMinimapRadius(Character character) {
        int radius = 4;
        if (character.isChosen()) {
            choserCharRadius ++;
            choserCharRadius %= 8;
            radius = choserCharRadius + 2;
        }
        return radius;
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

    public static void setMinimapImg(Image minimapImg) {
        PanelViewer.minimapImg = minimapImg;
    }

    public static Rectangle getMinimapRect() {
        return minimapRect;
    }

    public static Rectangle getMinimapPosRect() {
        return minimapPosRect;
    }

}
