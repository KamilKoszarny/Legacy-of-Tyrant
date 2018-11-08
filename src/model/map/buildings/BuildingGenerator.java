package model.map.buildings;

import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.roads.RoadGenerator;
import model.map.terrains.Terrain;
import model.map.water.RiverGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BuildingGenerator {

    model.map.Map map;
    static List<Building> buildings = new ArrayList<>();
    private static Building touchedBuilding;
    private static final double MAX_SIZE_RATIO = 3;
    private static final int MIN_DIST_TO_BORDER = 10;
    static final int SIZE_STEP = 3;
    private static final int MIN_SIZE = 3 * SIZE_STEP;
    private static final int FLATTEN_PLUS_RADIUS = 8;


    public BuildingGenerator(Map map) {
        this.map = map;
    }

    public void generateAndDrawBuildings(int count, int max_size){
        for (int i = 0; i < count; i++){
            Building building = calcBuildingSizeAndPos(max_size);
            System.out.println(building);

            if(buildingIsOnRoadOrRiver(building, map))
               i--;
            else{
                if(touchesAnotherBuilding(building)) {
                    building = new Building(building, touchedBuilding, map);
                    buildings.remove(touchedBuilding);
                }
                buildings.add(building);
                putTerrainAndWalls(building);
                GeomerticHelper.flatten(building.getAllPointsPlusRadius(FLATTEN_PLUS_RADIUS), map, 0);
                setBuildingHeight(building);
            }
        }
    }

    public void reflattenBuildings() {
        while (!allFlat()) {
            for (Building building : buildings) {
                GeomerticHelper.flatten(building.getAllPointsPlusRadius(1), map, 0);
            }
        }
    }

    private Building calcBuildingSizeAndPos(int max_size) {
        Random r = new Random();
        int sizeX = r.nextInt(max_size - MIN_SIZE) + MIN_SIZE;
        int sizeY = r.nextInt(max_size - MIN_SIZE) + MIN_SIZE;
        while (sizeX > sizeY * MAX_SIZE_RATIO || sizeY > sizeX * MAX_SIZE_RATIO)
            sizeX = r.nextInt(max_size - MIN_SIZE) + MIN_SIZE;
        int posX = r.nextInt(map.getWidth() - sizeX - 2*MIN_DIST_TO_BORDER) + MIN_DIST_TO_BORDER;
        int posY = r.nextInt(map.getHeight() - sizeY - 2*MIN_DIST_TO_BORDER) + MIN_DIST_TO_BORDER;
        sizeX /= SIZE_STEP; sizeX *= SIZE_STEP; sizeX += 2;
        sizeY /= SIZE_STEP; sizeY *= SIZE_STEP; sizeY += 2;
//        int sizeX = 122, sizeY = 152, posX = 20, posY = 30;
        int wallThickness = 1;
//            int wallThickness = r.nextInt(5) + 1;
        return new Building(sizeX, sizeY, posX, posY, wallThickness, map);
    }

    private void putTerrainAndWalls(Building building){
        List<Point> wallPoints;
        for (int side = 0; side < 4; side++) {
            wallPoints = building.getWallPoints(side);
            if (wallPoints != null)
                chooseAndPutWalls(wallPoints, side);
        }

        for (Point inPoint: building.getInPoints()) {
            map.getPoints().get(inPoint).setTerrain(Terrain.GROUND);
        }
        chooseAndPutWallCorners(building);
    }

    private void chooseAndPutWalls(List<Point> wallPoints, int side) {
        int i = 0;
        MapPiece mapPiece;
        for (Point wallPoint: wallPoints) {
            mapPiece = map.getPoints().get(wallPoint);
            if (i%SIZE_STEP == SIZE_STEP/2 + 1)
                mapPiece.setObject(new Wall(side, WallType.WOOD));
            mapPiece.setTerrain(Terrain.GROUND);
            List<Point> nonWalkablePoints = GeomerticHelper.pointsInRadius(wallPoint, 2, map);
            for (Point point: nonWalkablePoints) {
                mapPiece = map.getPoints().get(point);
                mapPiece.setWalkable(false);
            }
            i++;
        }
    }

    private void chooseAndPutWallCorners(Building building) {
        for (Point wallPoint: building.getAllWallPoints()) {
            for (int i = 0; i < 4; i++) {
                if (building.getWallPoints(i).contains(wallPoint) && building.getWallPoints((i + 1)%4).contains(wallPoint))
                    map.getPoints().get(wallPoint).setObject(new Wall(i, (i + 1)%4, WallType.WOOD));
            }
        }
    }

    private void setBuildingHeight(Building building){
        int avgHeight, sumHeight = 0;
        List<Point> buildingPointsPlusRadius = building.getAllPointsPlusRadius(4);
        for (Point bPoint : buildingPointsPlusRadius) {
            sumHeight += map.getPoints().get(bPoint).getHeight();
        }
        avgHeight = sumHeight / buildingPointsPlusRadius.size();
        for (Point bPoint: buildingPointsPlusRadius) {
            map.getPoints().get(bPoint).setHeight(avgHeight);
        }
    }

    private static boolean buildingIsOnRoadOrRiver(Building building, Map map){
        if (map.isWithRoad()) {
            for (Point point : building.getAllWallPoints()) {
                if (RoadGenerator.isOnRoad(point))
                    return true;
            }
        }
        if (map.isWithRiver()) {
            for (Point point : building.getAllWallPoints()) {
                if (RiverGenerator.isOnRiver(point))
                    return true;
            }
        }
        return false;
    }

    private static boolean touchesAnotherBuilding(Building building){
        for (Building anotherBuilding: buildings) {
            for (Point wallPoint: building.getAllPointsPlusRadius(2)) {
                if (anotherBuilding.contains(wallPoint)) {
                    touchedBuilding = anotherBuilding;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean allFlat() {
        for (Building building: buildings) {
            if (!GeomerticHelper.allFlat(building.getAllPoints(), map))
                return false;
        }
        return true;
    }
}
