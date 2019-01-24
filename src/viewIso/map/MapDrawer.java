package viewIso.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import main.App;
import model.Battle;
import model.character.Character;
import model.map.Map;
import viewIso.panel.PanelViewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        gc.setFill(new ImagePattern(mapImage.getImage(),
                mapImage.getxShift() + zeroScreenPosition.x, mapImage.getyShift() + zeroScreenPosition.y,
                mapImage.getWidth(), mapImage.getHeight(), false));

        if (map.isDiscovered()) {
            gc.drawImage(mapImage.getImage(),
                    -mapImage.getxShift() - zeroScreenPosition.x, -mapImage.getyShift() - zeroScreenPosition.y,
                    canvas.getWidth(), canvas.getHeight(), 0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            List<Polygon> exploredEdges = mapImage.getExploredView();
            drawMapPart(gc, exploredEdges);
        }

//        drawAllCharsViews(gc);
//        drawChosenCharView(gc);

        gc.setEffect(null);
    }

    private void drawAllCharsViews(GraphicsContext gc) {
        List<Polygon> viewAll = new ArrayList<>();
        for (Character character: Battle.getCharacters()) {
            if (character.getColor().equals(Battle.getPlayerColor()))
            viewAll.add(character.getView());
        }
        gc.setEffect(new Glow(.5));
        drawMapPart(gc, viewAll);
    }

    private void drawChosenCharView(GraphicsContext gc) {
        if (Battle.getChosenCharacter() != null) {
            List<Polygon> view = Arrays.asList(Battle.getChosenCharacter().getView());
            gc.setEffect(new Glow(1));
            drawMapPart(gc, view);
        }
    }

    private void drawMapPart(GraphicsContext gc, List<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            double[][] coords = MapDrawCalculator.screenWithHeightCoordsForDrawPolygon(polygon);
            gc.fillPolygon(coords[0], coords[1], coords[0].length);
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

    public static MapImage getMapImage() {
        return mapImage;
    }
}
