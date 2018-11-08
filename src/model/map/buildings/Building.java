package model.map.buildings;

import helpers.my.GeomerticHelper;
import model.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Building {

    private List<Point> inPoints;
    private List<Point>[] wallPoints = new List[4];
    private int sizeX, sizeY, posX, posY, wallThickness;
    Map map;

    Building(int sizeX, int sizeY, int posX, int posY, int wallThickness, Map map) {
//        sizeX = (int) (sizeX / Map.RESOLUTION_M);
//        sizeY = (int) (sizeY / Map.RESOLUTION_M);
        sizeX /= BuildingGenerator.SIZE_STEP; sizeX *= BuildingGenerator.SIZE_STEP; sizeX += 2;
        sizeY /= BuildingGenerator.SIZE_STEP; sizeY *= BuildingGenerator.SIZE_STEP; sizeY += 2;
        this.sizeX = sizeX; this.sizeY = sizeY;
        posX /= BuildingGenerator.SIZE_STEP; posX *= BuildingGenerator.SIZE_STEP;
        posY /= BuildingGenerator.SIZE_STEP; posY *= BuildingGenerator.SIZE_STEP;
        this.posX = posX; this.posY = posY;
        this.wallThickness = wallThickness;
        this.map = map;
        for (int i = 0; i < wallPoints.length; i++) {
            wallPoints[i] = new ArrayList<>();
        }
//        wallPointsN = new ArrayList<>();
//        wallPointsE = new ArrayList<>();
//        wallPointsS = new ArrayList<>();
//        wallPointsW = new ArrayList<>();
        inPoints = new ArrayList<>();
        setInAndWallPoints();
    }

    Building(Building building1, Building building2, Map map){
        this.map = map;
        sizeX = Math.max(building1.getSizeX(), building2.getSizeX());
        sizeY = Math.max(building1.getSizeY(), building2.getSizeY());
        posX = Math.min(building1.getPosX(), building2.getPosX());
        posY = Math.min(building1.getPosY(), building2.getPosY());
        for (int i = 0; i < wallPoints.length; i++) {
            wallPoints[i] = new ArrayList<>();
        }
        inPoints = new ArrayList<>();

        if (building1.getWallThickness() < building2.getWallThickness())
            building1 = new Building(building1.getSizeX(), building1.getSizeY(),building1.getPosX(), building1.getPosY(),
                    building2.getWallThickness(), building1.getMap());
        else if (building1.getWallThickness() > building2.getWallThickness())
            building2 = new Building(building2.getSizeX(), building2.getSizeY(),building2.getPosX(), building2.getPosY(),
                    building1.getWallThickness(), building1.getMap());

        Set<Point> setInPoints = new LinkedHashSet<>();
        setInPoints.addAll(building1.inPoints);
        setInPoints.addAll(building2.inPoints);
        inPoints.addAll(setInPoints);

        for (int i = 0; i < wallPoints.length; i++) {
            for (Point wallP1: building1.getWallPoints(i)) {
                if (!building2.haveInside(wallP1))
                    wallPoints[i].add(wallP1);
            }
            for (Point wallP2: building2.getWallPoints(i)) {
                if (!building1.haveInside(wallP2))
                    wallPoints[i].add(wallP2);
            }
        }
    }

    private void setInAndWallPoints() {
        for (int x = posX; x < posX + sizeX; x++) {
            for (int y = posY; y < posY + sizeY; y++) {
                decideAndSetInOrWallPoint(x, y);
            }
        }
    }

    private void decideAndSetInOrWallPoint(int x, int y) {
        Point point = new Point(x, y);
        if (map.isOnMapPoints(point)) {
            if (y < posY + wallThickness)
                wallPoints[0].add(new Point(x, y));
            if (x >= posX + sizeX - wallThickness)
                wallPoints[1].add(new Point(x, y));
            if (y >= posY + sizeY - wallThickness)
                wallPoints[2].add(new Point(x, y));
            if (x < posX + wallThickness)
                wallPoints[3].add(new Point(x, y));
            else
                inPoints.add(new Point(x, y));
        }
    }

    @Override
    public String toString() {
        return "sizeX: " + sizeX + " sizeY: " + sizeY + " posX: " + posX + " posY: " + posY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean contains(Point point){
        if (inPoints.contains(point))
            return true;
        for (int i = 0; i < wallPoints.length; i++) {
            if (wallPoints[i].contains(point))
                return true;
        }
        return false;
    }

    public boolean haveInside(Point point){
        return inPoints.contains(point);
    }

    public int getWallThickness() {
        return wallThickness;
    }

    public List<Point> getAllPoints() {
        List<Point> buildingPoints = new ArrayList<>();
        buildingPoints.addAll(inPoints);
        for (int i = 0; i < wallPoints.length; i++) {
            buildingPoints.addAll(wallPoints[i]);
        }
        return buildingPoints;
    }

    public List<Point> getAllPointsPlusRadius(int radius) {
        List<Point> buildingPoints = new ArrayList<>();
        buildingPoints.addAll(inPoints);
        List<Point> wallPointPlusRadius = new ArrayList<>();
        for (int i = 0; i < wallPoints.length; i++) {
            for (Point point: wallPoints[i])
                wallPointPlusRadius.addAll(GeomerticHelper.pointsInRadius(point, radius, map));
        }
        buildingPoints.addAll(wallPointPlusRadius);
        return buildingPoints;
    }

    public List<Point> getAllWallPoints() {
        List<Point> allWallPoints = new ArrayList<>();
        for (int i = 0; i < wallPoints.length; i++) {
            allWallPoints.addAll(wallPoints[i]);
        }
        return allWallPoints;
    }

    public List<Point> getWallPoints(int side) {
        return wallPoints[side];
    }

    public List<Point> getInPoints() {
        return inPoints;
    }

    public Map getMap() {
        return map;
    }
}
