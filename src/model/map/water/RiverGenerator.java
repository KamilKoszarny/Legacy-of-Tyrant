package model.map.water;

import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RiverGenerator {

    final int DEPTH = 2000;
    int width = 25;

    static List<Point> riverMidPoints;
    static List<Point> riverPoints;

    Map map;

    public RiverGenerator(Map map) {
        this.map = map;
    }

    public void generateRiver(int width){
        riverMidPoints = generateMiddleOfStrip(map);
        riverPoints = generateRiverByMidPoints(riverMidPoints, map, -DEPTH, width);
        setRiverTerrain();
    }


    private static List<Point> generateMiddleOfStrip(Map map){
        //N, E, S, W
        boolean[] sides = map.getRiverSides();
        Random r = new Random();
        List<Point> roadMidPoints = new ArrayList<>();

        if(sides[0] && !(sides[1] && sides[3])){
            Point start = new Point(r.nextInt(map.mapXPoints / 2) + map.mapXPoints / 4, 0);
            if(sides[2]) {
                guideStrip(roadMidPoints, start, 4, map);
                Point midPoint = roadMidPoints.get((int)(roadMidPoints.size() * (r.nextDouble() * 0.5 + 0.25)));
                if (sides[1]) {
                    guideStrip(roadMidPoints, midPoint, 2, map);
                }
                if (sides[3]) {
                    guideStrip(roadMidPoints, midPoint, 6, map);
                }
            } else if (sides[1] && !sides[3]){
                guideStrip(roadMidPoints, start, 3, map);
            } else if (sides[3] && !sides[1]){
                guideStrip(roadMidPoints, start, 5, map);
            }
        } else if (sides[1]){
            Point start = new Point(map.getWidth() - 1, r.nextInt(map.mapYPoints / 2) + map.mapYPoints / 4);
            if (sides[3]){
                guideStrip(roadMidPoints, start, 6, map);
                Point midPoint = roadMidPoints.get((int)(roadMidPoints.size() * (r.nextDouble() * 0.5 + 0.25)));
                if (sides[0]) {
                    guideStrip(roadMidPoints, midPoint, 8, map);
                }
                if (sides[2]) {
                    guideStrip(roadMidPoints, midPoint, 4, map);
                }
            } else if (sides[2]) {
                guideStrip(roadMidPoints, start, 5, map);
            }
        } else if (sides[2] && sides[3])
            guideStrip(roadMidPoints, new Point(r.nextInt(map.mapXPoints / 2) + map.mapXPoints / 4, map.getHeight() - 1), 7, map);
        return roadMidPoints;
    }

    private static void guideStrip(List<Point> roadMidPoints, Point start, int direction, Map map) {
        java.util.Map<Integer, Double> sideProbabilities = new HashMap<>();
        setRiverDir(sideProbabilities, direction);
        Point currentPoint = start;
        while (map.isOnMapPoints(currentPoint)) {
            roadMidPoints.add(currentPoint);
            int side = shuffleSide(sideProbabilities);
            currentPoint = calcNextMidPoint(currentPoint, side);
        }
    }

    private static void setRiverDir(java.util.Map<Integer, Double> sideProbabilities, int dir){
        Random r = new Random();
        sideProbabilities.put(0, Math.pow(Math.abs(4 - dir), 2) * (.1 + r.nextDouble()));
        sideProbabilities.put(1, Math.pow(Math.abs(4 - Math.abs(2 - dir)), 2) * (.1 + r.nextDouble()));
        sideProbabilities.put(2, Math.pow((4 - Math.abs(4 - dir)), 2) * (.1 + r.nextDouble()));
        sideProbabilities.put(3, Math.pow(Math.abs(4 - Math.abs(6 - dir)), 2) * (.1 + r.nextDouble()));
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

    private static List<Point> generateRiverByMidPoints(List<Point> midPoints, Map map, int hightOffset, int width){
        List<Point> riverPoints = new ArrayList<>();
        Random r = new Random();
        int i = 0;
        List<Point> closePoints;

        for (Point midPoint: midPoints) {
            width = r.nextInt((int)((width / Map.RESOLUTION_M * Map.M_PER_POINT)));
            closePoints = GeomerticHelper.pointsInRadius(midPoint, width / 2, map);
            riverPoints.addAll(closePoints);
            width = (int)(width / Map.RESOLUTION_M * Map.M_PER_POINT) + 3;
            closePoints = GeomerticHelper.pointsInRadius(midPoint, width / 2, map);
            GeomerticHelper.flatten(closePoints, map, hightOffset);
            i++;
        }

        return riverPoints;
    }

    public static boolean isOnRiver(Point point){
        return riverPoints.contains(point);
    }

    private void setRiverTerrain() {
        for (Point point: riverPoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(Terrain.WATER);
            mapPiece.setWalkable(false);
        }
    }
}
