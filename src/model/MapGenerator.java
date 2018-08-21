package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MapGenerator {

    private static java.util.Map<Terrain, ArrayList<Point>> terrainsBasePoints = new HashMap<>();
    private static Map map;

    public static Map generateMap(int widthM, int heightM, java.util.Map<Terrain, Double> terrainIntensity){
        map = new Map(widthM, heightM);

        recalcTerrainIntensity(terrainIntensity);

        ArrayList<Point> points = new ArrayList<>(map.getPoints().keySet());
        Collections.shuffle(points);

        Random r = new Random();
        for (Terrain t: terrainIntensity.keySet()) {
            ArrayList<Point> terrainBasePoints = new ArrayList<>();
            Point p = new Point(r.nextInt(map.getWidth()), r.nextInt(map.getHeight()));
            terrainBasePoints.add(p);
            for(int i = 0; i < terrainIntensity.get(t) * 10; i++) {
//                Point p2 = new Point(p.x + )
                terrainBasePoints.add(new Point(p.x + r.nextInt(map.getWidth()) - r.nextInt(map.getWidth()), p.y + r.nextInt(map.getHeight()) - r.nextInt(map.getHeight())));
            }
            terrainsBasePoints.put(t, terrainBasePoints);
        }


        for (Point point : points) {
            MapPiece mapPiece = map.getPoints().get(point);

            mapPiece.setTerrain(chooseTerrain(point, terrainIntensity));
        }
        return map;
    }

    private static java.util.Map<Terrain, Double> recalcTerrainIntensity(java.util.Map<Terrain, Double> terrainIntensity){
        double sumIntensity = 0;
        for (Terrain terrain: terrainIntensity.keySet()) {
            sumIntensity += terrainIntensity.get(terrain);
        }

        for (Terrain terrain: terrainIntensity.keySet()) {
            terrainIntensity.put(terrain, terrainIntensity.get(terrain) / sumIntensity);
        }

        return terrainIntensity;
    }

    private static Terrain chooseTerrain(Point point,java.util.Map<Terrain, Double> terrainIntensity){

        terrainIntensity = recalcTerrainIntensityBasedOnSector(point, terrainIntensity);
//        terrainIntensity = recalcTerrainIntensityBasedOnSurrounding(point, terrainIntensity);
        terrainIntensity = recalcTerrainIntensity(terrainIntensity);

        Random rand = new Random();
        double n = rand.nextDouble();
        double t = 0;

        for (Terrain terrain: terrainIntensity.keySet()){
            t += terrainIntensity.get(terrain);
            if (n < t)
                return terrain;
        }
        return null;
    }

    private static java.util.Map<Terrain, Double> recalcTerrainIntensityBasedOnSurrounding(Point point, java.util.Map<Terrain, Double> terrainIntensity){
        Point surroundPoint;
        Terrain terrain;

        for (int i = -5; i < 5; i += 1)
            for (int j = -5; j < 5; j += 1){
                surroundPoint = new Point(point.x + i, point.y + j);

                if (surroundPoint.x >= 0 && surroundPoint.y >= 0 && surroundPoint.x < map.getWidth() && surroundPoint.y < map.getHeight()) {
                    terrain = map.getPoints().get(surroundPoint).getTerrain();
                    if (terrain != null)
                        terrainIntensity.put(terrain, terrainIntensity.get(terrain) + 10/point.distance(surroundPoint));
                }
            }
        return terrainIntensity;
    }

    private static java.util.Map<Terrain, Double> recalcTerrainIntensityBasedOnSector(Point point, java.util.Map<Terrain, Double> terrainIntensity){
       for (Terrain t: terrainIntensity.keySet()) {
           for (Point p: terrainsBasePoints.get(t)) {
               if (!point.equals(p))
                   terrainIntensity.put(t, terrainIntensity.get(t) + 25/point.distance(p));
           }
       }

       return terrainIntensity;
    }
}
