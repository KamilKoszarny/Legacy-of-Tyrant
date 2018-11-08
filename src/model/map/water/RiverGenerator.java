package model.map.water;

import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.List;

public class RiverGenerator {

    final int DEPTH = 1000;
    static int width = 25;

    static List<Point> riverMidPoints;
    static List<Point> riverPoints;

    Map map;

    public RiverGenerator(Map map) {
        this.map = map;
    }

    public void generateRiver(int width){
        RiverGenerator.width = width;
        riverMidPoints = GeomerticHelper.generateMiddleOfStrip(map, map.getRiverSides());
        riverPoints = GeomerticHelper.generateStripByMidPoints(riverMidPoints, map, -DEPTH, width, -10);
        setRiverTerrain();
    }

    public static boolean isOnRiver(Point point){
        return riverPoints.contains(point);
    }

    private void setRiverTerrain() {
        for (Point point: riverPoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(Terrain.WATER);
            mapPiece.setWalkable(false);
        }
    }

    public static int getWidth() {
        return width;
    }
}
