package helpers.my;

import model.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GeomerticHelper {
    public static double distTo0(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static java.util.List<Point> pointsInRadius(Point centerPoint, int radius, Map map){
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

    public static void flatten(List<Point> points, Map map, int offset) {
        int avgHeight = avgHeight(points, map, offset);
        setHeights(avgHeight, points, map);
    }

    public static int avgHeight(List<Point> points, Map map, int offset) {
        int avgHeight, sumHeight = 0;
        for (Point point: points) {
            sumHeight += map.getPoints().get(point).getHeight();
        }
        avgHeight = sumHeight / points.size() + offset;
        return avgHeight;
    }

    public static void setHeights(int height, List<Point> points, Map map) {
        for (Point point: points) {
            map.getPoints().get(point).setHeight(height);
        }
    }
}
