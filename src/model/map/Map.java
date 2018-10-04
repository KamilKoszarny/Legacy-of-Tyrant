package model.map;

import model.map.heights.MapHeightType;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Map {

    public static final double RESOLUTION_M = 0.2;
    public static final double M_PER_POINT = 0.1;
    public final double MIN_HEIGHT = -50;
    public final double MAX_HEIGHT = 500;

    public int mapXPoints;
    public int mapYPoints;
    private MapType type;
    private MapHeightType heightType;
    private int widthM;
    private int heightM;
    private java.util.Map<Point, MapPiece> points = new HashMap<>();
    private boolean withRoad;
    private boolean[] roadSides;
    private int buildingsCount, buildingMaxSize;

    public Map(int widthM, int heightM, MapType type, MapHeightType heightType, boolean[] roadSides) {
        this.widthM = widthM;
        this.heightM = heightM;
        this.type = type;
        this.heightType = heightType;
        Random r = new Random();
        Terrain.GRASS.setIntensity(type.getGrass() * (r.nextInt(5) + 10));
        Terrain.BUSH.setIntensity(type.getBush() * (r.nextInt(5) + 10));
        Terrain.TREES.setIntensity(type.getTrees() * (r.nextInt(5) + 10));
        Terrain.GROUND.setIntensity(type.getGround() * (r.nextInt(5) + 10));
        this.roadSides = roadSides;
        if (roadSides[0] || roadSides[1] || roadSides[2] || roadSides[3])
            withRoad = true;
        buildingsCount = (int) (type.getBuildings() * (r.nextDouble() + 0.5));
        buildingMaxSize = type.getBuildingMaxSize();

        mapXPoints = (int) ((double)widthM / Map.RESOLUTION_M);
        mapYPoints = (int) ((double)heightM / Map.RESOLUTION_M);

        for (int i = 0; i < widthM / RESOLUTION_M; i += 1)
            for (int j = 0; j < heightM / RESOLUTION_M; j += 1){
                points.put(new Point(i, j), new MapPiece());
            }

        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.generateMap(this);
    }

    public java.util.Map<Point, MapPiece> getPoints() {
        return points;
    }

    public MapType getType() {
        return type;
    }

    public MapHeightType getHeightType() {
        return heightType;
    }

    public int getWidthM() {
        return widthM;
    }

    public int getHeightM() {
        return heightM;
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

    public boolean[] getRoadSides() {
        return roadSides;
    }

    public int getBuildingsCount() {
        return buildingsCount;
    }

    public int getBuildingMaxSize() {
        return buildingMaxSize;
    }

    boolean isOnMapM(Point p){
        return p.x >= 0 && p.y >= 0 && p.x < widthM && p.y < heightM;
    }

    public boolean isOnMapPoints(Point p){
        return p.x >= 0 && p.y >= 0 && p.x < mapXPoints && p.y < mapYPoints;
    }
}
