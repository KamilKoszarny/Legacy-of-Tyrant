package model.map;

import java.awt.*;
import java.util.HashMap;

public class Map {

    public static final double RESOLUTION_M = 0.2;
    public static final double M_PER_PIX = 0.1;
    public final double MIN_HEIGHT = -20;
    public final double MAX_HEIGHT = 250;

    public int mapXPoints;
    public int mapYPoints;
    private int widthM;
    private int heightM;
    private java.util.Map<Point, MapPiece> points = new HashMap<>();
    private boolean withRoad;
    private boolean[] roadSides;

    public Map(int widthM, int heightM) {
        this.widthM = widthM;
        this.heightM = heightM;
        mapXPoints = (int) ((double)widthM / Map.RESOLUTION_M);
        mapYPoints = (int) ((double)heightM / Map.RESOLUTION_M);

        for (int i = 0; i < widthM / RESOLUTION_M; i += 1)
            for (int j = 0; j < heightM / RESOLUTION_M; j += 1){
                points.put(new Point(i, j), new MapPiece());
            }
    }

    public java.util.Map<Point, MapPiece> getPoints() {
        return points;
    }

    public int getHeight() {
        return (int)(heightM / RESOLUTION_M);
    }

    public int getWidth() {
        return (int)(widthM / RESOLUTION_M);
    }

    public boolean isWithRoad() {
        return withRoad;
    }

    public void setWithRoad(boolean withRoad) {
        this.withRoad = withRoad;
    }

    public boolean[] getRoadSides() {
        return roadSides;
    }

    public void setRoadSides(boolean[] roadSides) {
        this.roadSides = roadSides;
    }

    boolean isOnMapM(Point p){
        return p.x >= 0 && p.y >= 0 && p.x < widthM && p.y < heightM;
    }

    public boolean isOnMapPoints(Point p){
        return p.x >= 0 && p.y >= 0 && p.x < mapXPoints && p.y < mapYPoints;
    }
}
