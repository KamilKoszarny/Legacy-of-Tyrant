package model.map.roads;

import helpers.my.CalcHelper;
import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;
import model.map.water.RiverGenerator;

import java.awt.*;
import java.util.Random;

public class RoadGenerator {

    static java.util.List<Point> roadMidPoints;
    static java.util.List<Point> roadPoints;

    Map map;

    public RoadGenerator(Map map) {
        this.map = map;
    }

    public void generateRoad(int width){
        roadMidPoints = GeomerticHelper.generateMiddleOfStrip(map, map.getRoadSides());
        if (map.isWithRiver()) {
            while (calcCreatedBridges() > calcPossibleBridges())
                roadMidPoints = GeomerticHelper.generateMiddleOfStrip(map, map.getRoadSides());
        }
        roadPoints = GeomerticHelper.generateStripByMidPoints(roadMidPoints, map, 0, width, 0);
        setRoadTerrain();
    }

    public static boolean isOnRoad(Point point){
        return roadPoints.contains(point);
    }

    private void setRoadTerrain() {
        for (Point point: roadPoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(Terrain.GROUND);
            mapPiece.setWalkable(true);
        }
    }

    private int calcPossibleBridges() {
        boolean[] riverSides = map.getRiverSides();
        boolean[] roadSides = map.getRoadSides();
        int riverSidesCount = CalcHelper.calcTrues(riverSides);
        int roadSidesCount = CalcHelper.calcTrues(roadSides);
        if (roadSidesCount + riverSidesCount == 4) {
            if (riverSides == roadSides)
                return 0;
            else
                return 1;
        } else if (roadSidesCount + riverSidesCount == 5)
            return 1;
        else if (roadSidesCount + riverSidesCount == 6)
            return 1;
        else if (roadSidesCount + riverSidesCount == 7)
            return new Random().nextInt(2) + 1;
        else if (roadSidesCount + riverSidesCount == 8)
            return 2;
        return 0;
    }

    private int calcCreatedBridges() {
        int numberOfPoints = 0;
        for (Point point: roadMidPoints) {
            if (RiverGenerator.isOnRiver(point))
                numberOfPoints++;
        }
        return numberOfPoints / RiverGenerator.getWidth();
    }
}
