package model.map;

import helpers.downloaded.pathfinding.grid.GridGraph;
import model.map.heights.MapHeightType;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Map {

    public static final double RESOLUTION_M = 0.2;
    public static final double M_PER_POINT = 0.25;
    public final int MIN_HEIGHT_PIX = -50;
    public final int MAX_HEIGHT_PIX = 500;

    public int mapXPoints, mapYPoints;
    private MapType type;
    private MapHeightType heightType;
    private int widthM, heightM;
    private static java.util.Map<Point, MapPiece> points = new HashMap<>();
    private GridGraph gridGraph;
    private boolean[] roadSides, waterSides, riverSides;
    private int buildingsCount, buildingMaxSize;
    private boolean discovered = false;

    public Map(int widthM, int heightM, MapType type, MapHeightType heightType,
               boolean[] roadSides, boolean[] riverSides, boolean[] waterSides) {
        this.widthM = widthM;
        this.heightM = heightM;
        this.type = type;
        this.heightType = heightType;
        Random r = new Random();
        for (Terrain terrain: Terrain.values()) {
            terrain.setIntensity(type.getTerrainIntensity(terrain) * (r.nextInt(5) + 10));
        }
        this.roadSides = roadSides;
        this.riverSides = riverSides;
        this.waterSides = waterSides;
        buildingsCount = (int) (type.getBuildings() * (r.nextDouble() + 0.5));
        buildingMaxSize = type.getBuildingMaxSize();

        mapXPoints = (int) ((double)widthM / Map.RESOLUTION_M);
        mapYPoints = (int) ((double)heightM / Map.RESOLUTION_M);
        gridGraph = new GridGraph(mapXPoints * 4, mapYPoints * 4);
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
        return roadSides[0] || roadSides[1] || roadSides[2] || roadSides[3];
    }

    public boolean[] getRoadSides() {
        return roadSides;
    }

    public boolean isWithRiver() {
        return roadSides[0] || riverSides[1] || riverSides[2] || riverSides[3];
    }

    public boolean[] getRiverSides() {
        return riverSides;
    }

    public boolean isWithWater() {
        return waterSides[0] || waterSides[1] || waterSides[2] || waterSides[3];
    }

    public boolean[] getWaterSides() {
        return waterSides;
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

    public GridGraph getGridGraph() {
        return gridGraph;
    }

    public void setGridGraph(GridGraph gridGraph) {
        this.gridGraph = gridGraph;
    }

    public boolean isDiscovered() {
        return discovered;
    }
}
