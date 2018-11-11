package model.map.buildings;

import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;
import model.map.roads.RoadGenerator;
import model.map.terrains.Terrain;
import model.map.water.RiverGenerator;
import model.map.water.WaterGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BuildingGenerator {

    model.map.Map map;
    static List<Building> buildings = new ArrayList<>();
    private static Building touchedBuilding;
    private static final double MAX_SIZE_RATIO = 3;
    static final int SIZE_STEP = 3;
    private static final int MIN_SIZE = 3 * SIZE_STEP;
    private static final int FLATTEN_PLUS_RADIUS = 8;


    public BuildingGenerator(Map map) {
        this.map = map;
    }

    public void generateAndDrawBuildings(int count, int max_size){

//        count = 1;

        for (int i = 0; i < count; i++){
            Building building = calcBuildingSizeAndPos(max_size);

            if(buildingIsOnRoadOrWater(building, map) || touchesAnotherBuilding(building))
               i--;
            else{
                putTerrainAndWalls(building);
                GeomerticHelper.flatten(building.getAllPointsPlusRadius(FLATTEN_PLUS_RADIUS), map, 0);
                buildings.add(building);
            }
        }
    }

    public void reflattenBuildings() {
        while (!allFlat()) {
            for (Building building : buildings) {
                List<Point> pointsToFlatten = building.getAllPointsPlusRadius(1);
                GeomerticHelper.flatten(pointsToFlatten, map, 0);
                for (Point point: pointsToFlatten) {
                    map.getPoints().get(point).setTerrain(Terrain.GROUND);
                }
            }
        }
    }

    private Building calcBuildingSizeAndPos(int max_size) {
        Random r = new Random();
        int sizeX = r.nextInt(max_size - MIN_SIZE) + MIN_SIZE;
        int sizeY = r.nextInt(max_size - MIN_SIZE) + MIN_SIZE;
        while (sizeX > sizeY * MAX_SIZE_RATIO || sizeY > sizeX * MAX_SIZE_RATIO)
            sizeX = r.nextInt(max_size - MIN_SIZE) + MIN_SIZE;
        int posX = r.nextInt(map.getWidth() - sizeX - 2*max_size) + max_size;
        int posY = r.nextInt(map.getHeight() - sizeY - 2*max_size) + max_size;
        sizeX /= SIZE_STEP; sizeX *= SIZE_STEP; sizeX += 2;
        sizeY /= SIZE_STEP; sizeY *= SIZE_STEP; sizeY += 2;
        posX /= BuildingGenerator.SIZE_STEP; posX *= BuildingGenerator.SIZE_STEP;
        posY /= BuildingGenerator.SIZE_STEP; posY *= BuildingGenerator.SIZE_STEP;
        return new Building(sizeX, sizeY, posX, posY, map);
    }

    private void putTerrainAndWalls(Building building){
        for (Point inPoint: building.getInPoints()) {
            map.getPoints().get(inPoint).setTerrain(Terrain.GROUND);
        }
        List<Point> wallPoints;
        boolean[] doorSides = shuffleDoorSides();
        for (int side = 0; side < 4; side++) {
            wallPoints = building.getWallPoints(side);
            if (wallPoints != null)
                chooseAndPutWalls(wallPoints, side, doorSides[side]);
        }
        int side;
        for (List<Point> inWPoints: building.getInnerWallsPoints().keySet()) {
            if (!inWPoints.isEmpty()) {
                side = building.getInnerWallsPoints().get(inWPoints);
                chooseAndPutWalls(inWPoints, side, true);
            }
        }
        for (List<Point> inWPoints: building.getExtraInnerWallsPoints().keySet()) {
            if (!inWPoints.isEmpty()) {
                side = building.getExtraInnerWallsPoints().get(inWPoints);
                chooseAndPutWalls(inWPoints, side, false);
            }
        }
        chooseAndPutWallCorners(building);
    }

    private boolean[] shuffleDoorSides() {
        boolean[] doorSides = new boolean[4];
        int countDoors = 0;
        Random r = new Random();
        while (countDoors == 0) {
            for (int i = 0; i < 4; i++) {
                if (r.nextInt(4) == 0) {
                    doorSides[i] = true;
                    countDoors++;
                }
                if (countDoors == 2)
                    return doorSides;
            }
        }
        return doorSides;
    }

    private void chooseAndPutWalls(List<Point> wallPoints, int side, boolean door) {
        int i = 0;
        MapPiece mapPiece;
        int prevX = wallPoints.get(0).x, prevY = wallPoints.get(0).y;
        List<MapPiece> wallSpritePieces = new ArrayList<>();
        for (Point wallPoint : wallPoints) {
            mapPiece = map.getPoints().get(wallPoint);
            if (((side % 2 == 1 && wallPoint.x != prevX && wallPoint.y == prevY) ||
                    (side % 2 == 0 && wallPoint.y != prevY && wallPoint.x == prevX))) {
                i--;
            }
            prevX = wallPoint.x;
            prevY = wallPoint.y;
            if (i % SIZE_STEP == SIZE_STEP / 2 + 1) {
                mapPiece.setObject(new Wall(side, WallType.WOOD));
                wallSpritePieces.add(mapPiece);
            }
//            mapPiece.setTerrain(Terrain.GROUND);
            mapPiece.setTerrain(Terrain.SPECIAL);
            List<Point> nonWalkablePoints = GeomerticHelper.pointsInRadius(wallPoint, 3, map);
            for (Point point : nonWalkablePoints) {
                mapPiece = map.getPoints().get(point);
                mapPiece.setWalkable(false);
            }
            i++;
        }
        if (door) {
            int doorIndex = new Random().nextInt(wallSpritePieces.size());
            wallSpritePieces.get(doorIndex).setObject(new Door(side, WallType.WOOD));
        }
    }

    private void chooseAndPutWallCorners(Building building) {
        for (Point wallPoint: building.getAllOutWallPoints()) {
            for (int i = 0; i < 4; i++) {
                if (building.getWallPoints(i).contains(wallPoint) && building.getWallPoints((i + 1)%4).contains(wallPoint))
                    map.getPoints().get(wallPoint).setObject(new Wall(i, (i + 1)%4, WallType.WOOD));
            }
        }
    }

    private static boolean buildingIsOnRoadOrWater(Building building, Map map){
        if (map.isWithRoad()) {
            for (Point point : building.getAllOutWallPoints()) {
                if (RoadGenerator.isOnRoad(point))
                    return true;
            }
        }
        if (map.isWithRiver()) {
            for (Point point : building.getAllOutWallPoints()) {
                if (RiverGenerator.isOnRiver(point))
                    return true;
            }
        }
        if (map.isWithWater()) {
            for (Point point : building.getAllOutWallPoints()) {
                if (WaterGenerator.isOnWater(point))
                    return true;
            }
        }
        return false;
    }

    private static boolean touchesAnotherBuilding(Building building){
        for (Building anotherBuilding: buildings) {
            for (Point wallPoint: building.getAllPointsPlusRadius(5)) {
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
