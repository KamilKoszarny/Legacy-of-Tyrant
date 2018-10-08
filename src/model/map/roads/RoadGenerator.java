package model.map.roads;

import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RoadGenerator {

    static java.util.List<Point> roadMidPoints;
    static java.util.List<Point> roadPoints;

    Map map;

    public RoadGenerator(Map map) {
        this.map = map;
    }

    public void generateRoad(){
        roadMidPoints = generateMiddleOfRoad();
        roadPoints = generateRoadByMidPoints(roadMidPoints);
        setRoadTerrain();
    }


    private java.util.List<Point> generateMiddleOfRoad(){
        //N, E, S, W
        boolean[] sides = map.getRoadSides();
        Random r = new Random();
        java.util.List<Point> roadMidPoints = new ArrayList<>();

        if(sides[0] && !(sides[1] && sides[3])){
            Point start = new Point(r.nextInt(map.mapXPoints / 2) + map.mapXPoints / 4, 0);
            if(sides[2]) {
                guideRoad(roadMidPoints, start, 4);
                Point midPoint = roadMidPoints.get((int)(roadMidPoints.size() * (r.nextDouble() * 0.5 + 0.25)));
                if (sides[1]) {
                    guideRoad(roadMidPoints, midPoint, 2);
                }
                if (sides[3]) {
                    guideRoad(roadMidPoints, midPoint, 6);
                }
            } else if (sides[1] && !sides[3]){
                guideRoad(roadMidPoints, start, 3);
            } else if (sides[3] && !sides[1]){
                guideRoad(roadMidPoints, start, 5);
            }
        } else if (sides[1]){
            Point start = new Point(map.getWidth() - 1, r.nextInt(map.mapYPoints / 2) + map.mapYPoints / 4);
            if (sides[3]){
                guideRoad(roadMidPoints, start, 6);
                Point midPoint = roadMidPoints.get((int)(roadMidPoints.size() * (r.nextDouble() * 0.5 + 0.25)));
                if (sides[0]) {
                    guideRoad(roadMidPoints, midPoint, 8);
                }
                if (sides[2]) {
                    guideRoad(roadMidPoints, midPoint, 4);
                }
            } else if (sides[2]) {
                guideRoad(roadMidPoints, start, 5);
            }
        } else if (sides[2] && sides[3])
            guideRoad(roadMidPoints, new Point(r.nextInt(map.mapXPoints / 2) + map.mapXPoints / 4, map.getHeight() - 1), 7);
        return roadMidPoints;
    }

    private void guideRoad(java.util.List<Point> roadMidPoints, Point start, int direction) {
        java.util.Map<Integer, Double> sideProbabilities = new HashMap<>();
        setRoadDir(sideProbabilities, direction);
        Point currentPoint = start;
        while (map.isOnMapPoints(currentPoint)) {
            roadMidPoints.add(currentPoint);
            int side = shuffleSide(sideProbabilities);
            currentPoint = calcNextMidPoint(currentPoint, side);
        }
    }

    private static void setRoadDir(java.util.Map<Integer, Double> sideProbabilities, int dir){
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


    private java.util.List<Point> generateRoadByMidPoints(java.util.List<Point> midPoints){
        List<Point> roadPoints = new ArrayList<>();
        Random r = new Random();
        int width;
        int i = 0;
        List<Point> closePoints;
        int avgHeight;

        for (Point midPoint: midPoints) {
            width = r.nextInt((int)((15 / Map.RESOLUTION_M * Map.M_PER_POINT) + 10)) + 2;
            closePoints = pointsInRadius(midPoint, width / 2);
            roadPoints.addAll(closePoints);
            width = (int)(15 / Map.RESOLUTION_M * Map.M_PER_POINT) + 5;
            closePoints = pointsInRadius(midPoint, width / 2);
            avgHeight = avgHeight(closePoints);
            setHeights(avgHeight, closePoints);
            i++;
        }

        return roadPoints;
    }

    private List<Point> pointsInRadius(Point centerPoint, int radius){
        List<Point> pointsInRadius = new ArrayList<>();
        for(int i = -radius; i < radius; i++) {
            for (int j = -radius; j < radius; j++) {
                Point point = new Point(centerPoint.x + i, centerPoint.y + j);
                if (map.isOnMapPoints(point) && point.distance(centerPoint) < radius)
                    pointsInRadius.add(point);
            }
        }
        return pointsInRadius;
    }

    public static boolean isOnMidRoad(Point point){
        return roadMidPoints.contains(point);
    }

    private int avgHeight(List<Point> points) {
        int avgHeight, sumHeight = 0;
        for (Point point: points) {
            sumHeight += map.getPoints().get(point).getHeight();
        }
        avgHeight = sumHeight / points.size();
        return avgHeight;
    }

    private void setHeights(int height, List<Point> points) {
        for (Point point: points) {
            map.getPoints().get(point).setHeight(height);
        }
    }

    private void setRoadTerrain() {
        for (Point point: roadPoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(Terrain.GROUND);
        }
    }
}
