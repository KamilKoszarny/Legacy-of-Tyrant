package model.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Building {

    private List<Point> inPoints;
    private List<Point> wallPoints;
    private int sizeX, sizeY, posX, posY, wallThickness;

    public Building(int sizeX, int sizeY, int posX, int posY, int wallThickness) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.wallThickness = wallThickness;
        wallPoints = new ArrayList<>();
        inPoints = new ArrayList<>();
        for (int x = posX; x < posX + sizeX; x++)
            for (int y = posY; y < posY + sizeY; y++)
                if (MapGenerator.isOnMap(new Point(x, y)))
                    if (x < posX + wallThickness || x >= posX + sizeX - wallThickness ||
                            y < posY + wallThickness || y >= posY + sizeY - wallThickness)
                        wallPoints.add(new Point(x, y));
                    else
                        inPoints.add(new Point(x, y));
    }

    public Building(Building building1, Building building2){
        sizeX = Math.max(building1.getSizeX(), building2.getSizeX());
        sizeY = Math.max(building1.getSizeY(), building2.getSizeY());
        posX = Math.min(building1.getPosX(), building2.getPosX());
        posY = Math.min(building1.getPosY(), building2.getPosY());
        wallPoints = new ArrayList<>();
        inPoints = new ArrayList<>();

        if (building1.getWallThickness() < building2.getWallThickness())
            building1 = new Building(building1.getSizeX(), building1.getSizeY(),building1.getPosX(), building1.getPosY(),
                    building2.getWallThickness());
        else if (building1.getWallThickness() > building2.getWallThickness())
            building2 = new Building(building2.getSizeX(), building2.getSizeY(),building2.getPosX(), building2.getPosY(),
                    building1.getWallThickness());

        Set<Point> setInPoints = new LinkedHashSet<>();
        setInPoints.addAll(building1.inPoints);
        setInPoints.addAll(building2.inPoints);
        inPoints.addAll(setInPoints);

        for (Point wallP1: building1.getWallPoints()) {
            if (!building2.haveInside(wallP1))
                wallPoints.add(wallP1);
        }
        for (Point wallP2: building2.getWallPoints()) {
            if (!building1.haveInside(wallP2))
                wallPoints.add(wallP2);
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

//    public List<Point> getCorners(){
//        List<Point> corners = new ArrayList<>();
//        corners.add(new Point(posX, posY));
//        corners.add(new Point(posX + sizeX, posY));
//        corners.add(new Point(posX + sizeX, posY + sizeY));
//        corners.add(new Point(posX, posY + sizeY));
//        return corners;
//    }

    public boolean haveInsideWithWalls(Point point){
        return inPoints.contains(point) || wallPoints.contains(point);
    }

    public boolean haveInside(Point point){
        return inPoints.contains(point);
    }

    public int getWallThickness() {
        return wallThickness;
    }

    public List<Point> getInPoints() {
        return inPoints;
    }

    public List<Point> getWallPoints() {
        return wallPoints;
    }
}
