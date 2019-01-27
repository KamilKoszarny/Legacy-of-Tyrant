package helpers.my;

import javafx.geometry.Point2D;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class PolygonsHelper {
    public static boolean mergePolygons(List<Polygon> polygons, Polygon view) {
        for (int i = 0; i < polygons.size(); i++) {
            Polygon exploredPolygon = polygons.get(i);
            if (protrude(view, exploredPolygon)) {
                if (layOn(view, exploredPolygon)) {
                    Path path = (Path) Polygon.union(exploredPolygon, view);
                    exploredPolygon = path2Polygon(path);
                    polygons.set(i, exploredPolygon);
                    return true;
                }
            }
        }
        return false;
    }

    public static void reduceHoles(List<Polygon> holes, Polygon view) {
        for (int i = 0; i < holes.size(); i++) {
            Polygon hole = holes.get(i);
            if (layOn(view, hole)) {
                Path path = (Path) Polygon.subtract(hole, view);
                if (path.getElements().size() > 0) {
                    hole = path2Polygon(path);
                    holes.set(i, hole);
                } else {
                    holes.remove(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        Polygon polygon1 = new Polygon(0,0,1,0,2,0,3,0,3,1,0,1);
        Polygon polygon2 = new Polygon(1,0,2,0,2,2,1,2);
        Path path = (Path) Polygon.union(polygon1, polygon2);
        Polygon result = path2Polygon(path);
        polygon1 = new Polygon(1,0,2,0,2,3,1,3);
        polygon2 = new Polygon(0,1,3,1,3,2,0,2);
        path = (Path) Polygon.subtract(polygon1, polygon2);
        result = path2Polygon(path);
        List<Polygon> holes = splitHole(result);
    }

    private static boolean layOn(Polygon polygon1, Polygon polygon2){
        Path intersection = (Path) Shape.intersect(polygon1, polygon2);
        return intersection.getElements().size() > 0;
    }

    private static boolean protrude(Polygon polygon1, Polygon polygon2){
        Path subtraction = (Path) Shape.subtract(polygon1, polygon2);
        return subtraction.getElements().size() > 0;
    }

    public static void smoothPolygons(List<Polygon> polygons, int smoothLimit) {
        for (Polygon polygon: polygons) {
            if (polygon.getPoints().size() > 400)
                smoothLimit *= 2;
            smoothPolygon(polygon, smoothLimit);
        }
    }

    public static void smoothPolygon(Polygon polygon, int smoothLimit) {
        List<Double> coords = polygon.getPoints();
        for (int i = 0; i < coords.size()/2 - 2; i++) {
            if (coords.get(i*2) != null && coords.get(i*2+1) != null && coords.get(i*2+2) != null &&
                    coords.get(i*2+3) != null && coords.get(i*2+4) != null && coords.get(i*2+5) != null) {
                Point2D point1 = new Point2D(coords.get(i * 2), coords.get(i * 2 + 1));
                Point2D point2 = new Point2D(coords.get(i * 2 + 2), coords.get(i * 2 + 3));
                Point2D point3 = new Point2D(coords.get(i * 2 + 4), coords.get(i * 2 + 5));

                if (point1.distance(point2) < smoothLimit && point2.distance(point3) < smoothLimit
                        || point1.distance(point3) < smoothLimit/2) {
                    coords.remove(i * 2 + 2);
                    coords.remove(i * 2 + 2);
                }
            }
        }
    }

    public static void findHolesInPolygons(List<Polygon> polygons, List<Polygon> holes) {
        for (Polygon polygon: polygons) {
            List<Polygon> foundHoles = findHolesInPolygon(polygon);
            holes.addAll(foundHoles);
        }
    }

    public static List<Polygon> findHolesInPolygon(Polygon polygon) {
        List<Double> coords = polygon.getPoints();
        List<Polygon> holes = new ArrayList<>();
        int afterLastNull = 0;
        for (int i = 0; i < coords.size(); i++) {
            if (coords.get(i) == null) {
                Polygon hole = new Polygon();
                hole.getPoints().addAll(polygon.getPoints().subList(afterLastNull, i));
                i += 2;
                afterLastNull = i;
                holes.add(hole);
            }
        }
        if (afterLastNull < polygon.getPoints().size() - 10)
            polygon.getPoints().remove(0, afterLastNull);
        return holes;
    }

    public static void splitHoles(List<Polygon> holes) {
        List<Polygon> newHoles = new ArrayList<>();
        List<Polygon> holesToRemove = new ArrayList<>();
        for (Polygon hole: holes) {
            List<Polygon> splitedHoles = splitHole(hole);
            if (splitedHoles.size() > 1) {
                holesToRemove.add(hole);
                newHoles.addAll(splitedHoles);
            }
        }
        holes.removeAll(holesToRemove);
        holes.addAll(newHoles);
    }

    public static List<Polygon> splitHole(Polygon hole) {
        List<Polygon> holes = new ArrayList<>();
        List<Double> holePoints = hole.getPoints();
        int nullIndex = holePoints.indexOf(null);

        Polygon lastHole = null;
        while (nullIndex != -1) {
            Polygon hole1 = new Polygon();
            hole1.getPoints().addAll(holePoints.subList(0, nullIndex));
            holes.add(hole1);
            Polygon hole2 = new Polygon();
            hole2.getPoints().addAll(holePoints.subList(nullIndex + 2, holePoints.size()));
            holePoints = hole2.getPoints();
            nullIndex = holePoints.indexOf(null);
            lastHole = hole2;
        }
        if (lastHole != null)
            holes.add(lastHole);

        return holes;
    }

    public static void removeSmall(List<Polygon> polygons) {
        final int POINTS_LIMIT = 4, AREA_LIMIT = 3;
        List<Polygon> polygonsToRemove = new ArrayList<>();
        for (Polygon polygon: polygons) {
            if (polygon.getPoints().size() < POINTS_LIMIT || polygon.computeAreaInScreen() < AREA_LIMIT)
                polygonsToRemove.add(polygon);
        }
        polygons.removeAll(polygonsToRemove);
    }

    private static Polygon path2Polygon(Path path) {
        Double[] points = new Double[(path.getElements().size() - 1)*2];
        int i = 0;
        for(PathElement el : path.getElements()){
            if(el instanceof MoveTo){
                MoveTo mt = (MoveTo) el;
                points[i] = mt.getX();
                points[i+1] = mt.getY();
            }
            if(el instanceof LineTo){
                LineTo lt = (LineTo) el;
                points[i] = lt.getX();
                points[i+1] = lt.getY();
            }
            i += 2;
        }
        Polygon newPolygon = new Polygon();
        newPolygon.getPoints().addAll(points);
        return newPolygon;
    }
}
