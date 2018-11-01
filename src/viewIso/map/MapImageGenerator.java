package viewIso.map;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import model.map.Map;
import model.map.heights.HeightGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MapImageGenerator {

    private static MapImage mapPreImage;
    private static MapImage mapImage;
    private static Map map;
    private static MapPieceDrawer mapPieceDrawer;
    private static Random r = new Random();
    private static final double HSB_SHIFT = .3;
    private static int COLOR_MIX_RADIUS = (MapDrawer.MAP_PIECE_SCREEN_SIZE_X + MapDrawer.MAP_PIECE_SCREEN_SIZE_Y) / 2;

    static void initialize(Map map, MapPieceDrawer mapPieceDrawer){
        MapImageGenerator.map = map;
        MapImageGenerator.mapPieceDrawer = mapPieceDrawer;
        calcAndSetSizeAndShift();
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


    static MapImage generateMapPreImage() {
        drawMapOnPreImage();

        return mapPreImage;
    }

    private static void drawMapOnPreImage() {
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
//        mapImage = mapPreImage;

        PixelReader pixelReader = mapPreImage.getImage().getPixelReader();
        PixelWriter pixelWriter = mapImage.getImage().getPixelWriter();

        Color color;
        for (int y = 0; y < mapImage.getHeight(); y++) {
            for (int x = 0; x < mapImage.getWidth(); x++) {
                color = calcPixelColor(pixelReader, x, y);
                pixelWriter.setColor(x, y, color);
            }
        }
        System.out.println("mapImage ready");
//        calcAndSetColors();

        return mapImage;
    }

    private static Color calcPixelColor(PixelReader pixelReader, int x, int y) {
        Color originColor = pixelReader.getColor(x, y);
        if (originColor.equals(MapDrawer.BACKGROUND_COLOR))
            return MapDrawer.BACKGROUND_COLOR;

        x = x + r.nextInt(COLOR_MIX_RADIUS) - COLOR_MIX_RADIUS /2;
        y = y + r.nextInt(COLOR_MIX_RADIUS) - COLOR_MIX_RADIUS /2;

        if (x < 0 || x >= mapImage.getWidth() || y < 0 || y >= mapImage.getHeight())
            return MapDrawer.BACKGROUND_COLOR;

        Color shuffleColor = pixelReader.getColor(x + r.nextInt(COLOR_MIX_RADIUS) - COLOR_MIX_RADIUS /2,
                y + r.nextInt(COLOR_MIX_RADIUS) - COLOR_MIX_RADIUS /2);

        shuffleColor = Color.hsb((shuffleColor.getHue() * ((1 - HSB_SHIFT) + r.nextDouble()* HSB_SHIFT *2))%360,
                Math.min(shuffleColor.getSaturation()* ((1 - HSB_SHIFT) + r.nextDouble()* HSB_SHIFT *2), 1),
                Math.min(shuffleColor.getBrightness()* ((1 - HSB_SHIFT) + r.nextDouble()* HSB_SHIFT *2), 1));

        if (shuffleColor.equals(MapDrawer.BACKGROUND_COLOR))
            return originColor;
        return shuffleColor;
    }

    private static Point posRelToMapZero(int pixelX, int pixelY) {
        return new Point(pixelX + mapImage.getxShift(), pixelY + mapImage.getyShift());
    }
}
