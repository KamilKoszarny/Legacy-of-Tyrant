package model.map.buildings;

import helpers.my.GeomerticHelper;
import model.map.Map;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Building {

    private final int MIN_ROOM_SIZE = 3;

    private Map map;
    private int sizeX, sizeY, posX, posY;
    private List<Point> inPoints;
    private List<Point>[] wallPoints = new List[4];
    private java.util.Map<List<Point>, Integer> innerWallsPoints = new HashMap<>();
    private java.util.Map<List<Point>, Integer> extraInnerWallsPoints = new HashMap<>();

    Building(int sizeX, int sizeY, int posX, int posY, Map map) {
        this.sizeX = sizeX; this.sizeY = sizeY; this.posX = posX; this.posY = posY;
        this.map = map;
        inPoints = new ArrayList<>();
        for (int i = 0; i < wallPoints.length; i++) {
            wallPoints[i] = new ArrayList<>();
        }

        setInAndWallPoints();
        for (int side = 0; side < 4; side++) {
            if (new Random().nextInt(3) == 0)
                extend(side);
        }
        createInnerWalls();
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
        switch (side) {
            case 0: {
                extentN(xs, xl, yl);
            } break;
            case 1: {
                extendE(ys, xl, yl);
            } break;
            case 2: {
                extentS(xs, xl, yl);
            } break;
            case 3: {
                extendW(ys, xl, yl);
            } break;
        }
        wallPoints[0].sort(Comparator.comparingInt(p -> p.x));
        wallPoints[1].sort(Comparator.comparingInt(p -> p.y));
        wallPoints[2].sort(Comparator.comparingInt(p -> p.x));
        wallPoints[3].sort(Comparator.comparingInt(p -> p.y));
    }

    private void extendW(int ys, int xl, int yl) {
        Point wallPoint;
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
    }

    private void extentS(int xs, int xl, int yl) {
        Point wallPoint;
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
    }

    private void extendE(int ys, int xl, int yl) {
        Point wallPoint;
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
    }

    private void extentN(int xs, int xl, int yl) {
        Point wallPoint;
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
    }

    private void createInnerWalls() {
        createInnerWallsN();
        completeInnerWallSN();
        createInnerWallsW();
        completeInnerWallSW();
    }

    private void createInnerWallsN() {
        boolean end;
        Point p;
        for (Point wallPointN: wallPoints[0]) {
            end = false;
            if ((wallPointN.x - posX)% BuildingGenerator.SIZE_STEP != 1 && new Random().nextInt(3) == 0){
                List<Point> inWPoints = new ArrayList<>();
                inWPoints.add(wallPointN);
                for (int y = wallPointN.y + 1; !end; y++) {
                    p = new Point(wallPointN.x, y);
                    if(isCloseToSideWallX(p))
                        end = true;
                    if (isOnWall(p))
                        end = true;
                    if (!end || inWPoints.size() > 1)
                        inWPoints.add(p);
                }
                if (inWPoints.size() > 1)
                    innerWallsPoints.put(inWPoints, 1);
            }
        }
    }

    private void createInnerWallsW() {
        boolean end;
        Point p;
        for (Point wallPoint : wallPoints[3]) {
            end = false;
            if ((wallPoint.y - posY)%BuildingGenerator.SIZE_STEP != 1 && new Random().nextInt(3) == 0){
                List<Point> inWPoints = new ArrayList<>();
                inWPoints.add(wallPoint);
                for (int x = wallPoint.x + 1; !end; x++) {
                    p = new Point(x, wallPoint.y);
                    if(isCloseToSideWallY(p))
                        end = true;
                    if (isOnWall(p))
                        end = true;
                    if (!end || inWPoints.size() > 1)
                        inWPoints.add(p);
                }
                if (inWPoints.size() > 1)
                    innerWallsPoints.put(inWPoints, 2);
            }
        }
    }

    private void completeInnerWallSN() {
        for (List<Point> inWallPoint: innerWallsPoints.keySet()) {
            Point lastP = inWallPoint.get(inWallPoint.size() - 1);
            Point sideP = closeSideWallPointX(lastP);
            List<Point> allOtherWallPoints = new ArrayList<>(getAllWallPoints());
            allOtherWallPoints.remove(lastP);
            if (!allOtherWallPoints.contains(lastP) && sideP != null) {
                List<Point> inWPoints = new ArrayList<>();
                for (int x = Math.min(lastP.x, sideP.x) - 1; x <= Math.max(lastP.x, sideP.x); x++) {
                    inWPoints.add(new Point(x, lastP.y));
                }
                if (inWPoints.size() > 1)
                    extraInnerWallsPoints.put(inWPoints, 0);
            }
        }
    }

    private void completeInnerWallSW() {
        for (List<Point> inWallPoint: innerWallsPoints.keySet()) {
            Point lastP = inWallPoint.get(inWallPoint.size() - 1);
            Point sideP = closeSideWallPointY(lastP);
            List<Point> allOtherWallPoints = new ArrayList<>(getAllWallPoints());
            allOtherWallPoints.remove(lastP);
            if (!allOtherWallPoints.contains(lastP) && sideP != null) {
                List<Point> inWPoints = new ArrayList<>();
                for (int y = Math.min(lastP.y, sideP.y) - 1; y <= Math.max(lastP.y, sideP.y); y++) {
                    inWPoints.add(new Point(lastP.x, y));
                }
                if (inWPoints.size() > 1)
                    extraInnerWallsPoints.put(inWPoints, 1);
            }
        }
    }

    private boolean isOnWall(Point point) {
        boolean end = false;
        if (getAllWallPoints().contains(point)) {
            end = true;
        }
        return end;
    }

    private boolean isCloseToSideWallX(Point point) {
        if (closeSideWallPointX(point) != null)
            return true;
        return false;
    }

    private boolean isCloseToSideWallY(Point point) {
        if (closeSideWallPointY(point) != null)
            return true;
        return false;
    }

    private Point closeSideWallPointX(Point point) {
        int sideSpace = BuildingGenerator.SIZE_STEP*MIN_ROOM_SIZE;
        Point besideP, closestBesideP = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        boolean pointFound = false;
        for (int xBeside = -sideSpace + 1; xBeside < sideSpace - 1; xBeside++) {
            besideP = new Point(point.x + xBeside, point.y);
            if (getAllWallPoints().contains(besideP) && xBeside != 0) {
                if (point.distance(besideP) < point.distance(closestBesideP))
                    closestBesideP = besideP;
                pointFound = true;
            }
        }
        if (pointFound)
            return closestBesideP;
        return null;
    }

    private Point closeSideWallPointY(Point point) {
        int sideSpace = BuildingGenerator.SIZE_STEP*MIN_ROOM_SIZE;
        Point besideP, closestBesideP = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        boolean pointFound = false;
        for (int yBeside = -sideSpace + 1; yBeside < sideSpace - 1; yBeside++) {
            besideP = new Point(point.x, point.y + yBeside);
            if (getAllWallPoints().contains(besideP) && yBeside != 0) {
                if (point.distance(besideP) < point.distance(closestBesideP))
                    closestBesideP = besideP;
                pointFound = true;
            }
        }
        if (pointFound)
            return closestBesideP;
        return null;
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

    public List<Point> getWallPoints(int side) {
        return wallPoints[side];
    }

    public List<Point> getAllOutWallPoints() {
        List<Point> allWallPoints = new ArrayList<>();
        for (int i = 0; i < wallPoints.length; i++) {
            allWallPoints.addAll(wallPoints[i]);
        }
        return allWallPoints;
    }

    public List<Point> getAllWallPoints() {
        List<Point> allWallPoints = new ArrayList<>();
        for (int i = 0; i < wallPoints.length; i++) {
            allWallPoints.addAll(wallPoints[i]);
        }
        for (List<Point> inPoints : innerWallsPoints.keySet()) {
            allWallPoints.addAll(inPoints);
        }
        return allWallPoints;
    }

    public List<Point> getInPoints() {
        return inPoints;
    }

    public java.util.Map<List<Point>, Integer> getInnerWallsPoints() {
        return innerWallsPoints;
    }

    public java.util.Map<List<Point>, Integer> getExtraInnerWallsPoints() {
        return extraInnerWallsPoints;
    }

    public Map getMap() {
        return map;
    }
}
