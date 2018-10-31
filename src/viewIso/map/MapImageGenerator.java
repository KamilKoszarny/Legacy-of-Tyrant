package viewIso.map;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.Color;
import model.map.Map;
import model.map.heights.HeightGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapImageGenerator {

    static MapImage mapPreImage;
    static MapImage mapImage;
    static Map map;
    static MapPieceDrawer mapPieceDrawer;

    static void initialize(Map map, MapPieceDrawer mapPieceDrawer){
        MapImageGenerator.map = map;
        MapImageGenerator.mapPieceDrawer = mapPieceDrawer;
        calcAndSetSizeAndShift();
    }

    static MapImage generateMapPreImage() {
        drawMapOnPreImage();

        return mapPreImage;
    }

    public static void drawMapOnPreImage() {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(mapPreImage.getImage(), null);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(java.awt.Color.BLACK);
        graphics.fillRect(0, 0, mapPreImage.getWidth(), mapPreImage.getHeight());
        List<Point> mapPointsInDrawOrder = sortPointsForDrawing();
        for (Point point: mapPointsInDrawOrder) {
            mapPieceDrawer.drawMapPiece(point, graphics, mapPreImage);
        }
        mapPreImage.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
    }

    private static List<Point> sortPointsForDrawing() {
        List<Point> mapPointsInDrawOrder = new ArrayList<>();
        mapPointsInDrawOrder.addAll(map.getPoints().keySet());
        mapPointsInDrawOrder.sort(Comparator.comparingInt(c -> c.x + c.y));
        return mapPointsInDrawOrder;
    }

    static MapImage generateMapImage() {
        mapImage = mapPreImage;

        return mapImage;
    }

    private static void calcAndSetSizeAndShift() {
        int width = (Math.max(map.mapXPoints, map.mapYPoints) + 2* MapImage.EXTRA_X) * MapDrawer.MAP_PIECE_SCREEN_SIZE_X;
        int height = (Math.max(map.mapXPoints, map.mapYPoints) + 2* MapImage.EXTRA_Y) * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y
                + map.MAX_HEIGHT_PIX - map.MIN_HEIGHT_PIX ;
        int shiftX = - width / 2 + MapImage.EXTRA_X;
        int shiftY = - (map.MAX_HEIGHT_PIX /HeightGenerator.H_PEX_PIX + MapImage.EXTRA_Y) * MapDrawer.MAP_PIECE_SCREEN_SIZE_Y;

        mapPreImage = new MapImage(width, height, shiftX, shiftY);
        mapImage = new MapImage(width, height, shiftX, shiftY);
    }

    private static void applyEffect () {
        mapImage = mapPreImage;
    }

    private static void calcAndSetColors() {
        for(int x = 0; x < mapImage.getWidth(); x++) {
            for(int y = 0; y < mapImage.getHeight(); y++) {
                Color color = calcPixelColor(x, y);
                mapImage.setPixelColor(x, y, color);
            }
        }
    }

    private static Color calcPixelColor(int x, int y) {
        System.out.println(x + " " + y);

        return Color.RED;
    }

    private static Point posRelToMapZero(int pixelX, int pixelY) {
        return new Point(pixelX + mapImage.getxShift(), pixelY + mapImage.getyShift());
    }
}
