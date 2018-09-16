package model.map;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class MapGenerator {

    private static Map map;

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
            generateRoad();

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

    private static void generateRoad(){

        List<Point> roadMidPoints = generateMiddleOfRoad();
        List<Point> roadPoints = generateRoadByMidPoints(roadMidPoints);

        for (Point point: roadPoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(Terrain.GROUND);
        }
    }

    private static List<Point> generateMiddleOfRoad(){
        //N, E, S, W
        boolean[] sides = map.getRoadSides();
        Random r = new Random();
        List<Point> roadMidPoints = new ArrayList<>();
        java.util.Map<Integer, Double> sideProbabilities = new HashMap<>();
        Point currentPoint;

        if(sides[0]){
            currentPoint = new Point(r.nextInt(300) + 100, 0);
            roadMidPoints.add(currentPoint);
            if(sides[2]) {
                setRoadS(sideProbabilities);

                while (currentPoint.y < map.getHeight() - 1) {
                    int side = shuffleSide(sideProbabilities);
                    currentPoint = calcNextMidPoint(currentPoint, side);
                    roadMidPoints.add(currentPoint);
                }
                if (sides[1]) {
                    currentPoint = roadMidPoints.get((int)(roadMidPoints.size() * r.nextDouble()));
                    setRoadE(sideProbabilities);
                    while (currentPoint.x < map.getWidth() - 1) {
                        int side = shuffleSide(sideProbabilities);
                        currentPoint = calcNextMidPoint(currentPoint, side);
                        roadMidPoints.add(currentPoint);
                    }
                }
            } else if(sides[1]) {
                setRoadSE(sideProbabilities);

                while (currentPoint.x < map.getWidth() - 1) {
                    int side = shuffleSide(sideProbabilities);
                    currentPoint = calcNextMidPoint(currentPoint, side);
                    roadMidPoints.add(currentPoint);
                }
            }
        }
        return roadMidPoints;
    }

    private static int shuffleSide(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        double result = r.nextDouble();

        for (Integer side: sideProbabilities.keySet()){
            if (result < sideProbabilities.get(side))
                return side;
        }
        return 0;
    }

    private static Point calcNextMidPoint(Point currentPoint, int side){
        if (side == 0)
            currentPoint = new Point(currentPoint.x, currentPoint.y - 1);
        else if (side == 1)
            currentPoint = new Point(currentPoint.x + 1, currentPoint.y);
        else if (side == 2)
            currentPoint = new Point(currentPoint.x, currentPoint.y + 1);
        else
            currentPoint = new Point(currentPoint.x - 1, currentPoint.y);

        return currentPoint;
    }

    private static List<Point> generateRoadByMidPoints(List<Point> midPoints){
        List<Point> roadPoints = new ArrayList<>();
        Random r = new Random();
        int width;

        for (Point midPoint: midPoints) {
            if (midPoint.x % (r.nextInt(3)+2) == 0 && midPoint.y % (r.nextInt(3)+2) == 0 ) {
                width = r.nextInt(15) + 10;
                roadPoints.addAll(pointsInRadius(midPoint, width / 2));
            }
        }

        return roadPoints;
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

    private static List<Point> pointsInRadius(Point centerPoint, int radius){
        List<Point> pointsInRadius = new ArrayList<>();
        for(int i = -radius; i < radius; i++) {
            for (int j = -radius; j < radius; j++) {
                Point point = new Point(centerPoint.x + i, centerPoint.y + j);
                if (pointIsOnMap(point) && point.distance(centerPoint) < radius)
                    pointsInRadius.add(point);
            }
        }
        return pointsInRadius;
    }

    private static boolean pointIsOnMap(Point point){
        if (point.x < 0)
            return false;
        if (point.y < 0)
            return false;
        if (point.x >= map.getWidth())
            return false;
        if (point.y >= map.getHeight())
            return false;
        return true;
    }


    private static void setRoadN(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .7 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .125 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .05 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .125 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadNE(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .4 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .4 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .1 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .1 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadNW(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .4 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .1 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .1 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .4 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadE(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .125 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .7 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .125 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .05 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadS(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .05 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .125 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .7 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .125 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadSE(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .1 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .4 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .4 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .1 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadSW(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .1 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .1 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .4 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .4 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void setRoadW(java.util.Map<Integer, Double> sideProbabilities){
        Random r = new Random();
        sideProbabilities.put(0, .125 * (1 + r.nextDouble()));
        sideProbabilities.put(1, .05 * (1 + r.nextDouble()));
        sideProbabilities.put(2, .125 * (1 + r.nextDouble()));
        sideProbabilities.put(3, .7 * (1 + r.nextDouble()));
        recalcSideProbabilities(sideProbabilities);
    }

    private static void recalcSideProbabilities(java.util.Map<Integer, Double> sideProbabilities){
        double sumSideProbablity = 0;


        for (Double sideProbablity: sideProbabilities.values()) {
            sumSideProbablity += sideProbablity;
        }

        int i = 0;
        for (Double sideProbablity: sideProbabilities.values()) {
            sideProbabilities.put(i, sideProbablity / sumSideProbablity);
            i++;
        }

        i = 0;
        sumSideProbablity = 0;
        for (Double sideProbablity: sideProbabilities.values()) {
            sumSideProbablity += sideProbablity;
            sideProbabilities.put(i, sumSideProbablity);
            i++;
        }
    }
}
