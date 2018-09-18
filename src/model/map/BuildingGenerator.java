package model.map;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BuildingGenerator {

    static Map map = MapGenerator.map;
    static int minSize = 20;
    static List<Building> buildings = new ArrayList<>();
    private static Building touchedBuilding;

    static void generateAndDrawBuildings(int count, int max_size){
        Random r = new Random();
        for (int i = 0; i < count; i++){
            Building building;
            int sizeX = r.nextInt(max_size - minSize) + minSize;
            int sizeY = r.nextInt(max_size - minSize) + minSize;
            int posX = r.nextInt(map.getWidth());
            int posY = r.nextInt(map.getHeight());
            int wallThickness = r.nextInt(5) + 1;
            building = new Building(sizeX, sizeY, posX, posY, wallThickness);

            if(buildingIsOnRoad(building))
               i--;
            else{
                if(touchesAnotherBuilding(building)) {
                    building = new Building(building, touchedBuilding);
                    buildings.remove(touchedBuilding);
                }
                buildings.add(building);
                drawBuilding(building);
            }
        }
    }

    private static void drawBuilding(Building building){
        for (Point wallPoint: building.getWallPoints()) {
            map.getPoints().get(wallPoint).setTerrain(Terrain.WALL);
        }
        for (Point inPoint: building.getInPoints()) {
            map.getPoints().get(inPoint).setTerrain(Terrain.GROUND);
        }
    }

    private static boolean buildingIsOnRoad(Building building){
        for (Point point: building.getWallPoints()) {
            if (RoadGenerator.isOnMidRoad(point))
                return true;
        }
        return false;
    }

    private static boolean touchesAnotherBuilding(Building building){
        for (Building anotherBuilding: buildings) {
            for (Point wallPoint: building.getWallPoints()) {
                if (anotherBuilding.haveInsideWithWalls(wallPoint)) {
                    touchedBuilding = anotherBuilding;
                    return true;
                }
            }
        }
        return false;
    }
}
