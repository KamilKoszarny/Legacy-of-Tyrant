package model.character.movement;

import helpers.downloaded.pathfinding.algorithms.datatypes.Point;
import helpers.downloaded.pathfinding.main.utility.Utility;
import javafx.geometry.Point2D;
import model.map.Map;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    static List<Point2D> calcPath(Point2D start, java.awt.Point end, Map map) {
        List<Point2D> mapPath = new ArrayList<>();
        int[] startGridPoint = gridPointByMapPoint(start);
        int[] endGridPoint = gridPointByMapPoint(end);
        int[][] gridPath = Utility.computeOptimalPathOnline(map.getGridGraph(),
                startGridPoint[0], startGridPoint[1], endGridPoint[0], endGridPoint[1]);
        for (int i = 0; i < gridPath.length; i++) {
            mapPath.add(mapPointByGridPoint(gridPath[i]));
        }
        return mapPath;
    }

    private static Point2D mapPointByGridPoint(int[] gridPoint) {
        double x = gridPoint[0] / 4.;
        double y = gridPoint[1] / 4.;
        return new Point2D(x, y);
    }

    private static int[] gridPointByMapPoint(Point2D mapPoint) {
        int[] gridPoint = new int[2];
        gridPoint[0] = (int) (mapPoint.getX() * 4);
        gridPoint[1] = (int) (mapPoint.getY() * 4);
        return gridPoint;
    }

    private static int[] gridPointByMapPoint(java.awt.Point mapPoint) {
        int[] gridPoint = new int[2];
        gridPoint[0] = (int) (mapPoint.x * 4);
        gridPoint[1] = (int) (mapPoint.y * 4);
        return gridPoint;
    }
}
