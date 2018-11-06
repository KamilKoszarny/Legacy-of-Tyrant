package model.map.buildings;

import helpers.my.GeomerticHelper;
import model.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Building {

    private List<Point> inPoints, wallPointsN, wallPointsE, wallPointsS, wallPointsW;
    private int sizeX, sizeY, posX, posY, wallThickness;
    Map map;

    Building(int sizeX, int sizeY, int posX, int posY, int wallThickness, Map map) {
        this.sizeX = (int) (sizeX * Map.RESOLUTION_M);
        this.sizeY = (int) (sizeY * Map.RESOLUTION_M);
        this.posX = posX;
        this.posY = posY;
        this.wallThickness = wallThickness;
        this.map = map;
        wallPointsN = new ArrayList<>();
        wallPointsE = new ArrayList<>();
        wallPointsS = new ArrayList<>();
        wallPointsW = new ArrayList<>();
        inPoints = new ArrayList<>();
        setInAndWallPoints();
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
                wallPointsN.add(new Point(x, y));
            if (x >= posX + sizeX - wallThickness)
                wallPointsE.add(new Point(x, y));
            if (y >= posY + sizeY - wallThickness)
                wallPointsS.add(new Point(x, y));
            if (x < posX + wallThickness)
                wallPointsW.add(new Point(x, y));
            else
                inPoints.add(new Point(x, y));
        }
    }

    Building(Building building1, Building building2, Map map){
        this.map = map;
        sizeX = Math.max(building1.getSizeX(), building2.getSizeX());
        sizeY = Math.max(building1.getSizeY(), building2.getSizeY());
        posX = Math.min(building1.getPosX(), building2.getPosX());
        posY = Math.min(building1.getPosY(), building2.getPosY());
        wallPointsN = new ArrayList<>();
        wallPointsE = new ArrayList<>();
        wallPointsS = new ArrayList<>();
        wallPointsW = new ArrayList<>();
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

        for (Point wallP1: building1.getAllPointsPlusRadius(0)) {
            if (!building2.haveInside(wallP1))
                wallPointsN.add(wallP1);
        }
        for (Point wallP2: building2.getAllPointsPlusRadius(0)) {
            if (!building1.haveInside(wallP2))
                wallPointsN.add(wallP2);
        }
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
        return inPoints.contains(point) || wallPointsN.contains(point);
    }

    public boolean haveInside(Point point){
        return inPoints.contains(point);
    }

    public int getWallThickness() {
        return wallThickness;
    }

    public List<Point> getAllPointsPlusRadius(int radius) {
        List<Point> buildingPoints = new ArrayList<>();
        buildingPoints.addAll(inPoints);
        List<Point> wallPointPlusRadius = new ArrayList<>();
        for (Point point: wallPointsN)
            wallPointPlusRadius.addAll(GeomerticHelper.pointsInRadius(point, radius, map));
        for (Point point: wallPointsE)
            wallPointPlusRadius.addAll(GeomerticHelper.pointsInRadius(point, radius, map));
        for (Point point: wallPointsS)
            wallPointPlusRadius.addAll(GeomerticHelper.pointsInRadius(point, radius, map));
        for (Point point: wallPointsW)
            wallPointPlusRadius.addAll(GeomerticHelper.pointsInRadius(point, radius, map));
        buildingPoints.addAll(wallPointPlusRadius);
        return buildingPoints;
    }

    public List<Point> getAllWallPoints() {
        List<Point> wallPoints = new ArrayList<>();
        wallPoints.addAll(wallPointsN);
        wallPoints.addAll(wallPointsE);
        wallPoints.addAll(wallPointsS);
        wallPoints.addAll(wallPointsW);
        return wallPoints;
    }

    public List<Point> getInPoints() {
        return inPoints;
    }

    public List<Point> getWallPointsBySide(int side) {
        if (side == 0)
            return wallPointsN;
        if (side == 1)
            return wallPointsE;
        if (side == 2)
            return wallPointsS;
        if (side == 3)
            return wallPointsW;
        else
            return null;
    }

    public Map getMap() {
        return map;
    }
}
