package viewIso.map;

import helpers.my.CalcHelper;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import main.App;
import model.Battle;
import model.character.Character;
import model.map.Map;
import model.map.lights.VisibilityCalculator;
import viewIso.panel.PanelViewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static viewIso.map.MapDrawCalculator.screenPosition;

public class MapDrawer {

    static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final int MAP_PIECE_SCREEN_SIZE_X = 24;
    public static final int MAP_PIECE_SCREEN_SIZE_Y = 16;
    public static int PIX_PER_M;

    private static Point zeroScreenPosition = new Point(600, -50);
    private static Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapPieceDrawer mPDrawer;
    private static MapImage mapImage;
    private static WritableImage lightMapImage;

    public MapDrawer(Map map, Canvas canvas) {
        MapDrawer.map = map;
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        mPDrawer = new MapPieceDrawer(map, gc, this, MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y);
        PIX_PER_M = (int) ((MAP_PIECE_SCREEN_SIZE_X + MAP_PIECE_SCREEN_SIZE_Y) / 2 / Map.M_PER_POINT);
        MapDrawCalculator.setMapAndDrawer(map, this);

        MapImageGenerator.initialize(map, mPDrawer);
        long time = System.nanoTime();
        mapImage = MapImageGenerator.generateMapPreImage();
        System.out.println("mapPreImageGen:" + (System.nanoTime() - time)/1000000. + " ms");
        if (App.FULL_MODE) {
            time = System.nanoTime();
            mapImage = MapImageGenerator.generateMapImage();
            System.out.println("mapImageGen:" + (System.nanoTime() - time) / 1000000. + " ms");
            time = System.nanoTime();
        }
        PanelViewer.setMinimapImg(MapImageGenerator.generateMinimapImage());
    }

    public void drawMap() {
        clearMap();
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        lightMap(gc);
        gc.setFill(new ImagePattern(mapImage.getImage(),
                mapImage.getxShift() + zeroScreenPosition.x, mapImage.getyShift() + zeroScreenPosition.y,
                mapImage.getWidth(), mapImage.getHeight(), false));

        if (map.isDiscovered()) {
            gc.drawImage(mapImage.getImage(),
                    -mapImage.getxShift() - zeroScreenPosition.x, -mapImage.getyShift() - zeroScreenPosition.y,
                    canvas.getWidth(), canvas.getHeight(), 0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            List<List<Point>> exploredEdges = mapImage.getExploredEdges();
            drawMapPart(gc, exploredEdges);
        }

        List<List<Point>> viewAllEdges = mapImage.getViewAllEdges();
        gc.setEffect(new ColorAdjust(0, 0, .25, 0));
        drawMapPart(gc, viewAllEdges);

        List<List<Point>> viewEdges = mapImage.getViewEdges();
        gc.setEffect(new ColorAdjust(0, 0, .5, 0));
        drawMapPart(gc, viewEdges);
        gc.setEffect(null);
    }

    private void drawMapPart(GraphicsContext gc, List<List<Point>> edges) {
        for (List<Point> edge : edges) {
            List<Point> screenEdge = new ArrayList<>();
            for (Point point: edge) {
                screenEdge.add(MapDrawCalculator.screenPositionWithHeight(point));
            }
            double[][] xyCoords = CalcHelper.pointsList2Coords(screenEdge);
            gc.fillPolygon(xyCoords[0], xyCoords[1], xyCoords[0].length);
        }
    }


    private static void lightMap(GraphicsContext gc) {
        lightMapImage = mapImage.getImage();
        Character chosenChar = Battle.getChosenCharacter();
        if (chosenChar != null) {
            Point charScreenPos = MapDrawCalculator.screenPositionWithHeight(chosenChar.getPosition());
            assert charScreenPos != null;

            Light.Point light = new Light.Point();
            light.setX(charScreenPos.x);
            light.setY(charScreenPos.y);
            light.setZ(800);
            light.setColor(Color.WHITE);

            Lighting lighting = new Lighting();
            lighting.setSurfaceScale(0);
//            lighting.setDiffuseConstant(diff += 0.02);
//            lighting.setSpecularConstant(diff += .01);
//            lighting.setSpecularExponent(specExp += .5);
            lighting.setLight(light);
            gc.setEffect(lighting);
        }
    }

    private static void lightMapPixelVersion() {
        lightMapImage = mapImage.getImage();
        PixelWriter pixWriter = lightMapImage.getPixelWriter();
        PixelReader pixReader = lightMapImage.getPixelReader();
        Character chosenChar = Battle.getChosenCharacter();
        if (chosenChar != null) {
            java.util.Map<Point, Integer> lightMap = VisibilityCalculator.calcLightMap(chosenChar);
            for (Point point: lightMap.keySet()) {
                Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
                if (MapDrawCalculator.isOnCanvas(screenPos)) {
                    Color color = pixReader.getColor(screenPos.x, screenPos.y);
//                    if (!color.equals(Color.BLACK)) {
                        double brightnessMultiplier = 1 + lightMap.get(point) / 100;
                        color = color.deriveColor(0, 1, brightnessMultiplier, 1);
                        pixWriter.setColor(screenPos.x, screenPos.y, color);
//                    }
                }
            }
        }
    }

    public void clearMap(){
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    public static Point getZeroScreenPosition() {
        return zeroScreenPosition;
    }

    public static void changeZeroScreenPosition(Point zSPChange) {
        zeroScreenPosition.x += zSPChange.x * MAP_PIECE_SCREEN_SIZE_X;
        zeroScreenPosition.y += zSPChange.y * MAP_PIECE_SCREEN_SIZE_Y;
    }

    public static void setZeroScreenPosition(double x, double y) {
        zeroScreenPosition = new Point((int)x, (int)y);
    }

    public static Map getMap() {
        return map;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
