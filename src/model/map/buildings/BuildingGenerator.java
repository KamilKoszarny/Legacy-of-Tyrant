package model.map.buildings;

import model.map.Map;
import model.map.roads.RoadGenerator;
import model.map.terrains.Terrain;
import model.map.water.RiverGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BuildingGenerator {

    model.map.Map map;
    static int minSize = 20;
    static List<Building> buildings = new ArrayList<>();
    private static Building touchedBuilding;
    private static final double MIN_SIZE_RATIO = 3;
    private static final int MIN_DIST_TO_BORDER = 10;
    private static final int SIZE_STEP = 5;


    public BuildingGenerator(Map map) {
        this.map = map;
    }

    public void generateAndDrawBuildings(int count, int max_size){
        for (int i = 0; i < count; i++){
            Building building = calcBuildingSizeAndPos(max_size);

            if(buildingIsOnRoadOrRiver(building, map))
               i--;
            else{
                if(touchesAnotherBuilding(building)) {
                    building = new Building(building, touchedBuilding, map);
                    buildings.remove(touchedBuilding);
                }
                buildings.add(building);
                putTerrainAndWalls(building);
                setBuildingHeight(building);
            }
        }
    }

    private Building calcBuildingSizeAndPos(int max_size) {
        Random r = new Random();
        int sizeX = r.nextInt(max_size - minSize) + minSize;
        int sizeY = r.nextInt(max_size - minSize) + minSize;
        while (sizeX > sizeY * MIN_SIZE_RATIO && sizeY > sizeX * MIN_SIZE_RATIO)
            sizeX = r.nextInt(max_size - minSize) + minSize;
        int posX = r.nextInt(map.getWidth() - sizeX - 2*MIN_DIST_TO_BORDER) + MIN_DIST_TO_BORDER;
        int posY = r.nextInt(map.getHeight() - sizeY - 2*MIN_DIST_TO_BORDER) + MIN_DIST_TO_BORDER;
        sizeX /= SIZE_STEP; sizeX *= SIZE_STEP;
        sizeY /= SIZE_STEP; sizeY *= SIZE_STEP;
        int wallThickness = 1;
//            int wallThickness = r.nextInt(5) + 1;
        return new Building(sizeX, sizeY, posX, posY, wallThickness, map);
    }

    private void putTerrainAndWalls(Building building){
        List<Point> wallPoints;
        for (int side = 0; side < 4; side++) {
            wallPoints = building.getWallPointsBySide(side);
            if (wallPoints != null)
                chooseAndPutWalls(wallPoints, side);
        }
        for (Point inPoint: building.getInPoints()) {
            map.getPoints().get(inPoint).setTerrain(Terrain.GROUND);
        }
    }

    private void chooseAndPutWalls(List<Point> wallPoints, int side) {
        int i = 0;
        for (Point wallPoint: wallPoints) {
            if (i%SIZE_STEP == SIZE_STEP/2)
                map.getPoints().get(wallPoint).setObject(new Wall(side));
            map.getPoints().get(wallPoint).setWalkable(false);
            i++;
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
}
