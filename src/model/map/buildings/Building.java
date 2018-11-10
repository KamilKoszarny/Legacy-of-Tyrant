package model.map.buildings;

import helpers.my.GeomerticHelper;
import model.map.Map;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Building {

    private List<Point> inPoints;
    private List<Point>[] wallPoints = new List[4];
    private int sizeX, sizeY, posX, posY;
    Map map;

    Building(int sizeX, int sizeY, int posX, int posY, Map map) {
        this.sizeX = sizeX; this.sizeY = sizeY; this.posX = posX; this.posY = posY;
        this.map = map;
        for (int i = 0; i < wallPoints.length; i++) {
            wallPoints[i] = new ArrayList<>();
        }

        inPoints = new ArrayList<>();
        setInAndWallPoints();
        for (int side = 0; side < 4; side++) {
            if (new Random().nextInt(3) == 0)
                extend(side);
        }
    }

    private void extend(int side) {
        Random r = new Random();
        int xs, ys, xl, yl;
        xs = r.nextInt(sizeX / 3 * 2);
        ys = r.nextInt(sizeY / 3 * 2);
        xl = Math.min(r.nextInt(sizeX / 3 * 2) + BuildingGenerator.SIZE_STEP * 2, sizeX - xs);
        yl = Math.min(r.nextInt(sizeY / 3 * 2) + BuildingGenerator.SIZE_STEP * 2, sizeY - ys);
        xs /= BuildingGenerator.SIZE_STEP; xs *= BuildingGenerator.SIZE_STEP; xs += 1;
        ys /= BuildingGenerator.SIZE_STEP; ys *= BuildingGenerator.SIZE_STEP; ys += 1;
        xl /= BuildingGenerator.SIZE_STEP; xl *= BuildingGenerator.SIZE_STEP;
        yl /= BuildingGenerator.SIZE_STEP; yl *= BuildingGenerator.SIZE_STEP;
        Point wallPoint;
        switch (side) {
            case 0: {
                for (int j = xs - 1; j <= xs + xl; j++) {
                    wallPoint = wallPoints[0].get(wallPoints[0].indexOf(new Point(posX + j, posY)));
                    wallPoint.y = wallPoint.y - yl;
                    for (int k = posY; k > posY - yl; k--) {
                        inPoints.add(new Point(posX + j ,k));
                    }
                }
                for (int j = posY; j >= posY - yl; j--) {
                    wallPoints[1].add(new Point(posX + (xs + xl), j));
                    wallPoints[3].add(new Point(posX + xs - 1, j));
                }
                if (xs - 1 != 0)
                    wallPoints[0].add(new Point(posX + xs - 1, posY));
                else
                    wallPoints[3].remove(new Point(posX + xs - 1, posY));
                if (xs + xl + 1 != sizeX)
                    wallPoints[0].add(new Point(posX + xs + xl, posY));
                else
                    wallPoints[1].remove(new Point(posX + xs + xl, posY));
            } break;
            case 1: {
                for (int j = ys - 1; j <= ys + yl; j++) {
                    wallPoint = wallPoints[1].get(wallPoints[1].indexOf(new Point(posX + sizeX - 1, posY + j)));
                    wallPoint.x = wallPoint.x + xl;
                    for (int k = posX + sizeX; k < posX + sizeX + xl; k++) {
                        inPoints.add(new Point(k, posY + j));
                    }
                }
                for (int j = posX + sizeX - 1; j < posX + sizeX + xl; j++) {
                    wallPoints[0].add(new Point(j, posY + ys - 1));
                    wallPoints[2].add(new Point(j, posY + (ys + yl)));
                }
                if (ys - 1 != 0)
                    wallPoints[1].add(new Point(posX + sizeX - 1, posY + ys - 1));
                else
                    wallPoints[0].remove(new Point(posX + sizeX - 1, posY + ys - 1));
                if (ys + yl + 1 != sizeY)
                    wallPoints[1].add(new Point(posX + sizeX - 1, posY + ys + yl));
                else
                    wallPoints[2].remove(new Point(posX + sizeX - 1, posY + ys + yl));
            } break;
            case 2: {
                for (int j = xs - 1; j <= xs + xl; j++) {
                    wallPoint = wallPoints[2].get(wallPoints[2].indexOf(new Point(posX + j, posY + sizeY - 1)));
                    wallPoint.y = wallPoint.y + yl;
                    for (int k = posY + sizeY; k < posY + sizeY + yl; k++) {
                        inPoints.add(new Point(posX + j ,k));
                    }
                }
                for (int j = posY + sizeY - 1; j < posY + sizeY + yl; j++) {
                    wallPoints[1].add(new Point(posX + (xs + xl), j));
                    wallPoints[3].add(new Point(posX + xs - 1, j));
                }
                if (xs - 1 != 0)
                    wallPoints[0].add(new Point(posX + xs - 1, posY + sizeY - 1));
                else
                    wallPoints[3].remove(new Point(posX + xs - 1, posY + sizeY - 1));
                if (xs + xl + 1 != sizeX)
                    wallPoints[0].add(new Point(posX + xs + xl, posY + sizeY - 1));
                else
                    wallPoints[1].remove(new Point(posX + xs + xl, posY + sizeY - 1));
            } break;
            case 3: {
                for (int j = ys - 1; j <= ys + yl; j++) {
                    wallPoint = wallPoints[3].get(wallPoints[3].indexOf(new Point(posX, posY + j)));
                    wallPoint.x = wallPoint.x - xl;
                    for (int k = posX; k > posX - xl; k--) {
                        inPoints.add(new Point(k, posY + j));
                    }
                }
                for (int j = posX; j >= posX - xl; j--) {
                    wallPoints[0].add(new Point(j, posY + ys - 1));
                    wallPoints[2].add(new Point(j, posY + (ys + yl)));
                }
                if (ys - 1 != 0)
                    wallPoints[3].add(new Point(posX, posY + ys - 1));
                else
                    wallPoints[0].remove(new Point(posX, posY + ys - 1));
                if (ys + yl + 1 != sizeY)
                    wallPoints[3].add(new Point(posX, posY + ys + yl));
                else
                    wallPoints[2].remove(new Point(posX, posY + ys + yl));
            } break;
        }
        wallPoints[0].sort(Comparator.comparingInt(p -> p.x));
        wallPoints[1].sort(Comparator.comparingInt(p -> p.y));
        wallPoints[2].sort(Comparator.comparingInt(p -> p.x));
        wallPoints[3].sort(Comparator.comparingInt(p -> p.y));
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
            if (y < posY + 1)
                wallPoints[0].add(new Point(x, y));
            if (x >= posX + sizeX - 1)
                wallPoints[1].add(new Point(x, y));
            if (y >= posY + sizeY - 1)
                wallPoints[2].add(new Point(x, y));
            if (x < posX + 1)
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
