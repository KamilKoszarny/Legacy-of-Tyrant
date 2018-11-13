package model.map.water;

import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaterGenerator {

    private static final int MAX_BEACH_SLOPE = 3000;
    private final Random R = new Random();
    int minHeight = Integer.MAX_VALUE;
    static List<MapPiece> flatPieces = new ArrayList<>();

    Map map;

    public WaterGenerator(Map map) {
        this.map = map;
    }

    public void generateWater(){
        boolean[] waterSides = map.getWaterSides();
        for (int side = 0; side < waterSides.length; side++) {
            int[] shore;
            if (waterSides[side]) {
                shore = generateShore(side);
                fillWater(side, shore);
            }
        }
        setHeights();
    }

    private int[] generateShore(int side) {
        int[] shore;
        if (side%2 == 0) {
            shore = new int[map.mapXPoints];
            shore[0] = (int) (map.mapYPoints*.05 + R.nextInt(map.mapYPoints)*.2);
        } else {
            shore = new int[map.mapYPoints];
            shore[0] = (int) (map.mapXPoints*.05 + R.nextInt(map.mapXPoints)*.2);
        }

        for (int i = 1; i < shore.length; i++) {
            shore[i] = (int) (shore[i - 1] + ((R.nextDouble() - .3) * map.mapXPoints * .01));
            if (shore[i] < 0)
                shore[i] *= -1;
            if (shore[i] > map.mapXPoints)
                shore[i] = map.mapXPoints - 1;
        }
        return shore;
    }

    private void fillWater(int side, int[] shore) {
        MapPiece mapPiece;
        for (int waterCol = 0; waterCol < shore.length; waterCol++) {
            for (int waterRow = 0; waterRow < shore[waterCol]; waterRow++) {
                switch (side){
                    case 0: mapPiece = map.getPoints().get(new Point(waterCol, waterRow)); break;
                    case 1: mapPiece = map.getPoints().get(new Point(map.mapXPoints - 1 - waterRow, waterCol)); break;
                    case 2: mapPiece = map.getPoints().get(new Point(waterCol, map.mapYPoints - 1 - waterRow)); break;
                    case 3: mapPiece = map.getPoints().get(new Point(waterRow, waterCol)); break;
                    default: mapPiece = null;
                }

                int beachWidth = 2;
                if (waterRow < shore[waterCol] - beachWidth) {
                    mapPiece.setTerrain(Terrain.WATER);
                    mapPiece.setWalkable(false);
                }
                flatPieces.add(mapPiece);
                if(minHeight > mapPiece.getHeight())
                    minHeight = mapPiece.getHeight();
            }
        }
    }

    public void setHeights() {
        for (MapPiece waterPiece: flatPieces) {
            waterPiece.setHeight(minHeight);
        }
    }

    public static boolean isOnWater(Point point, Map map){
        return flatPieces.contains(map.getPoints().get(point));
    }

    public void createSwampsAndSands() {
        MapPiece mapPiece;
        boolean nearWater;
        for (Point point: map.getPoints().keySet()) {
            nearWater = false;
            mapPiece = map.getPoints().get(point);
            if (mapPiece.getSlopeSize() < MAX_BEACH_SLOPE && !mapPiece.getTerrain().equals(Terrain.WATER) && !mapPiece.getTerrain().equals(Terrain.SWAMP)) {
                for (Point closePoint : GeomerticHelper.pointsInRadius(point, 6, map)) {
                    mapPiece = map.getPoints().get(closePoint);
                    if (mapPiece.getTerrain().equals(Terrain.WATER)) {
                        nearWater = true;
                        mapPiece.setTerrain(Terrain.SWAMP);
                    } else if (nearWater
                            && !mapPiece.getTerrain().equals(Terrain.GROUND)
                            && !mapPiece.getTerrain().equals(Terrain.WATER)
                            && !mapPiece.getTerrain().equals(Terrain.SWAMP)
                            && mapPiece.getSlopeSize() < MAX_BEACH_SLOPE) {
                        mapPiece.setTerrain(Terrain.SAND);
                    }
                }
            }
        }
    }
}
