package model.map;

import java.awt.*;
import java.util.*;

public class MapGenerator {

    static Map map;

    public static Map generateMap(int widthM, int heightM){
        map = new Map(widthM, heightM);
        map.setWithRoad(true);
        map.setRoadSides(new boolean[]{true, true, true, false});

        recalcTerrainIntensity();

        ArrayList<Point> points = new ArrayList<>(map.getPoints().keySet());
        Collections.shuffle(points);
        connectPoints();

        for (Point point : points) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(chooseTerrain(point));
        }

        if(map.isWithRoad())
            RoadGenerator.generateRoad();

        BuildingGenerator.generateAndDrawBuildings(10, 250);

        return map;
    }

    private static void recalcTerrainIntensity(){
        double sumIntensity = 0;
        for (Terrain terrain: Terrain.values()) {
            sumIntensity += terrain.getIntensity();
        }

        for (Terrain terrain: Terrain.values()) {
            terrain.setIntensity(terrain.getIntensity() / sumIntensity);
        }
    }

    private static void connectPoints(){
        Random r = new Random();
        for (Terrain t: Terrain.values()) {
            ArrayList<Point> terrainFocalPoints = new ArrayList<>();
            Point p = new Point(r.nextInt(map.getWidth()), r.nextInt(map.getHeight()));
            for(int i = 1; i < t.getIntensity() * 10; i++) {
                terrainFocalPoints.add(nearPoint(p));
            }
            t.setFocalPoints(terrainFocalPoints);
        }
    }

    private static Terrain chooseTerrain(Point point){

//        recalcTerrainIntensityBasedOnSector(point);
//        recalcTerrainIntensityBasedOnSurrounding(point);
        recalcTerrainIntensity();

        Random rand = new Random();
        double n = rand.nextDouble();
        double ti = 0;

        for (Terrain terrain: Terrain.values()){
            ti += terrain.getIntensity();
            if (n < ti)
                return terrain;
        }
        return null;
    }

    private static void recalcTerrainIntensityBasedOnSurrounding(Point point){
        Point surroundPoint;
        Terrain terrain;

        for (int i = -5; i < 5; i += 1)
            for (int j = -5; j < 5; j += 1){
                surroundPoint = new Point(point.x + i, point.y + j);

                if (surroundPoint.x >= 0 && surroundPoint.y >= 0 && surroundPoint.x < map.getWidth() && surroundPoint.y < map.getHeight()) {
                    terrain = map.getPoints().get(surroundPoint).getTerrain();
                    if (terrain != null)
                        terrain.setIntensity(terrain.getIntensity() + 10/point.distance(surroundPoint));
                }
            }
    }

    private static void recalcTerrainIntensityBasedOnSector(Point point){
       for (Terrain t: Terrain.values()) {
           for (Point p: t.getFocalPoints()) {
               if (!point.equals(p))
                   t.setIntensity(t.getIntensity() + 25/point.distance(p));
           }
       }
    }

    private static Point nearPoint(Point p){
        Random r = new Random();
//        Point nearPoint = new Point(p.x + (int)Math.pow(r.nextInt((int)Math.pow(map.getWidth(),4)), .25) * (r.nextInt(1)*2 - 1),
//                p.y + (int)Math.pow(r.nextInt((int)Math.pow(map.getHeight(),4)), .25) * (r.nextInt(1)*2 - 1));
//
        Point nearPoint = new Point(p.x + (int)(10*map.getWidth()/((r.nextDouble()-0.5)*map.getWidth())), p.y + (int)(10*map.getHeight()/((r.nextDouble()-0.5)*map.getHeight())));
        System.out.println(nearPoint);
//        return new Point(p.x + 100 * map.getWidth()/(r.nextInt(map.getWidth())*2 - map.getWidth()), p.y + 100 * map.getHeight()/(r.nextInt(map.getHeight())*2 - map.getHeight()));
        return nearPoint;
    }

    static boolean isOnMapM(Point p){
        return p.x >= 0 && p.y >= 0 && p.x < map.getWidth() && p.y < map.getHeight();
    }

    public static boolean isOnMapPix(Point p){
        return p.x >= 0 && p.y >= 0 && p.x < Map.mapXPoints && p.y < Map.mapYPoints;
    }

}
