package viewIso.panel;

import helpers.my.GeomerticHelper;
import helpers.my.PolygonsHelper;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import model.Battle;
import model.character.Character;
import viewIso.IsoViewer;
import viewIso.map.MapDrawer;

import java.awt.*;
import java.util.List;

public class MinimapViewer {
    public static double minimapScreenSize;
    public static double minimapToScreenRatioX;
    public static double minimapToScreenRatioY;

    private static Canvas fogCanvas;
    private static Image minimapImg;
    private static Rectangle rectangle;
    private static Rectangle positionRectangle;
    private static int choserCharRadius = 5;

    MinimapViewer(Rectangle minimapRect, Rectangle minimapPosRect, Canvas minimapFogCanvas) {
        rectangle = minimapRect;
        positionRectangle = minimapPosRect;
        fogCanvas = minimapFogCanvas;
        rectangle.setFill(new ImagePattern(minimapImg));

        initSizes();
        positionRectangle.setWidth(IsoViewer.getCanvas().getWidth() * minimapToScreenRatioX);
        positionRectangle.setHeight(IsoViewer.getCanvas().getHeight() * minimapToScreenRatioY);
        positionRectangle.toFront();
    }

    private static void initSizes() {
        minimapScreenSize = rectangle.getWidth() * Math.sqrt(2);
        int mapScreenWidth = Battle.getMap().mapXPoints * MapDrawer.MAP_PIECE_SCREEN_SIZE_X;
        int mapScreenHeight = Battle.getMap().mapXPoints * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y;
        minimapToScreenRatioX = minimapScreenSize / mapScreenWidth;
        minimapToScreenRatioY = minimapScreenSize / mapScreenHeight;
    }

    static void refreshMinimap() {
        WritableImage minimapImgWithChars = new WritableImage(minimapImg.getPixelReader(), (int) minimapImg.getWidth(), (int) minimapImg.getHeight());
        PixelWriter pixelWriter = minimapImgWithChars.getPixelWriter();
        for (Character character : Battle.getCharacters()) {
            int radius = calcMinimapRadius(character);
            for (Point point : GeomerticHelper.pointsInRadius(character.getPosition(), radius, Battle.getMap())) {
                pixelWriter.setColor(point.x, point.y, character.getColor());
            }
        }

        rectangle.setFill(new ImagePattern(minimapImgWithChars));

        positionRectangle.setTranslateX(positionRectangle.getWidth() / 2
                - MapDrawer.getZeroScreenPosition().getX() * minimapToScreenRatioX);
        positionRectangle.setTranslateY(-minimapScreenSize / 2 + positionRectangle.getHeight() / 2
                - MapDrawer.getZeroScreenPosition().getY() * minimapToScreenRatioY
                + MapDrawer.getMap().getHeightType().getHilly() / 10.);
    }

    public static void refreshMinimapFog(Polygon exploredView, List<Polygon> holes) {
        GraphicsContext gc = fogCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, fogCanvas.getWidth(), fogCanvas.getHeight());
        gc.setFill(MapDrawer.FOG_COLOR);
        Polygon fog = new Polygon(
                -1, -1, -1, fogCanvas.getHeight() + 1, fogCanvas.getWidth() + 1, fogCanvas.getHeight() + 1, fogCanvas.getWidth() + 1, -1);
        Polygon scaledExploredView = scalePolygon2Minimap(exploredView);
        fog = PolygonsHelper.subtractPolygons(fog, scaledExploredView);
        double[][] coords = PolygonsHelper.polygon2coords(fog);
        gc.fillPolygon(coords[0], coords[1], coords[0].length);
        for (Polygon hole : holes) {
            Polygon scaledHole = scalePolygon2Minimap(hole);
            coords = PolygonsHelper.polygon2coords(scaledHole);
            gc.fillPolygon(coords[0], coords[1], coords[0].length);
        }
    }

    private static Polygon scalePolygon2Minimap(Polygon polygon) {
        Polygon scaledPolygon = new Polygon();
        List<Double> coords = polygon.getPoints();
        double scale = PanelViewer.getPanel().getMinimapFogCanvas().getWidth() / Battle.getMap().mapXPoints;
        for (double coord : coords) {
            coord *= scale;
            scaledPolygon.getPoints().add(coord);
        }
        return scaledPolygon;
    }

    private static int calcMinimapRadius(Character character) {
        int radius = 4;
        if (character.isChosen()) {
            choserCharRadius++;
            choserCharRadius %= 8;
            radius = choserCharRadius + 2;
        }
        return radius;
    }

    public static void setMinimapImg(Image minimapImg) {
        MinimapViewer.minimapImg = minimapImg;
    }

    public static Rectangle getRectangle() {
        return rectangle;
    }

    public static Rectangle getPositionRectangle() {
        return positionRectangle;
    }
}