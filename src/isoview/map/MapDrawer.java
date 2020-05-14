package isoview.map;

import isoview.panel.MinimapViewer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import main.App;
import model.Battle;
import model.character.Character;
import model.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapDrawer {

    public static final Color BACKGROUND_COLOR = Color.BLACK, FOG_COLOR = Color.gray(0.1);
    public static final int MAP_PIECE_SCREEN_SIZE_X = 24;
    public static final int MAP_PIECE_SCREEN_SIZE_Y = 16;
    public static int PIX_PER_M;

    private static Point zeroScreenPosition = new Point(600, -50);
    private static Map map;
    private static Canvas canvas;
    private static GraphicsContext gc;
    private MapPieceDrawer mPDrawer;
    private static MapImage mapImage;
    private static List<Polygon> mapPolygon;

    public MapDrawer(Map map, Canvas canvas) {
        MapDrawer.map = map;
        MapDrawer.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        mPDrawer = new MapPieceDrawer(map, gc, this, MAP_PIECE_SCREEN_SIZE_X, MAP_PIECE_SCREEN_SIZE_Y);
        PIX_PER_M = (int) ((MAP_PIECE_SCREEN_SIZE_X + MAP_PIECE_SCREEN_SIZE_Y) / 2. / Map.M_PER_POINT);
        MapDrawCalculator.setMapAndDrawer(map, this);

        MapImageGenerator.initialize(map, mPDrawer);
        App.resetTime(0);
        mapImage = MapImageGenerator.generateMapPreImage();
        App.showAndResetTime("mapPreImage", -1);
        if (App.FULL_MODE) {
            mapImage = MapImageGenerator.generateMapImage();
            App.showAndResetTime("mapImageGen", -1);
        }
        MinimapViewer.setMinimapImg(MapImageGenerator.generateMinimapImage());

        int x = map.mapXPoints - 1, y = map.mapYPoints - 1;
        mapPolygon = Collections.singletonList(new Polygon(0, 0, x, 0, x, y, 0, y));

        if (map.isDiscovered())
            mapImage.setExploredView(mapPolygon);
    }

    public static void drawMap() {
        App.resetTime(1);
        App.resetTime(2);
        clearMap();
        App.showAndResetTime("clearMap", 2);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(new ImagePattern(mapImage.getImage(),
                mapImage.getxShift() + zeroScreenPosition.x, mapImage.getyShift() + zeroScreenPosition.y,
                mapImage.getWidth(), mapImage.getHeight(), false));

        if (map.isDiscovered()) {
            drawAll();
        } else {
            drawExploredPart();
        }
        App.showAndResetTime("drawMap", 2);
        drawChosenCharView();
        App.showAndResetTime("drawChosenCharView", 2);

        gc.setEffect(null);
        App.showAndResetTime("mapDraw: ", 1);
    }

    private static void drawAll() {
        gc.drawImage(mapImage.getImage(),
                -mapImage.getxShift() - zeroScreenPosition.x, -mapImage.getyShift() - zeroScreenPosition.y, canvas.getWidth(), canvas.getHeight(),
                0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private static void drawExploredPart() {
        List<Polygon> exploredView = mapImage.getExploredView();
        drawMapPart(exploredView, true, false);
        gc.setFill(FOG_COLOR);
        List<Polygon> holes = mapImage.getHolesInView();
        if (holes.size() > 0)
            drawMapPart(holes, false, false);
        gc.setFill(new ImagePattern(mapImage.getImage(),
                mapImage.getxShift() + zeroScreenPosition.x, mapImage.getyShift() + zeroScreenPosition.y,
                mapImage.getWidth(), mapImage.getHeight(), false));
        gc.setEffect(null);
    }

    private static void drawAllCharsViews() {
        List<Polygon> viewAll = new ArrayList<>();
        for (Character character: Battle.getCharacters()) {
            if (character.getColor().equals(Battle.getPlayerColor()))
                viewAll.add(character.getView());
        }
        drawMapPart(viewAll, false, true);
    }

    private static void drawChosenCharView() {
        if (Battle.getChosenCharacter() != null) {
            List<Polygon> view = Collections.singletonList(Battle.getChosenCharacter().getView());
            drawMapPart(view, false, true);
        }
    }

    private static void drawMapPart(List<Polygon> polygons, boolean heightBonus, boolean bondariesOnly) {
        for (Polygon polygon : polygons) {
            double[][] coords = MapDrawCalculator.screenWithHeightCoordsForDrawPolygon(polygon, heightBonus);
            if (bondariesOnly) {
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(3);
                gc.strokePolygon(coords[0], coords[1], coords[0].length);
            } else
                gc.fillPolygon(coords[0], coords[1], coords[0].length);
        }
    }

    public static void clearMap(){
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(FOG_COLOR);
        drawMapPart(mapPolygon, false, false);
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
