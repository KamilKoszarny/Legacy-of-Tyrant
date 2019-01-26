package model.map.lights;

import helpers.my.PolygonsHelper;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import model.Battle;
import model.character.Character;
import viewIso.map.MapDrawer;

import java.awt.*;
import java.util.List;

public class VisibilityCalculator {

    private static final int MIN_VISIBILITY_RADIUS_DAY = 50;

    public static void calcCharView(Character character) {
        Point pos = character.getPosition();
        double dir = character.getPreciseDirection();
        List<Double> viewCoords = character.getView().getPoints();
        viewCoords.clear();
        viewCoords.add(pos.getX());
        viewCoords.add(pos.getY());

        int viewDistMax = MIN_VISIBILITY_RADIUS_DAY + character.getStats().getEye()/2;
        int viewAngleRange = 90 + character.getStats().getEye(); // to add helmet
        int viewAngleStart = (int) ((1 - dir) * 360 / 8 - viewAngleRange/2);


        for (int angle = viewAngleStart; angle < viewAngleStart + viewAngleRange; angle += 2) {
            Point2D viewPoint = calcViewPoint(pos, viewDistMax, angle);
            viewCoords.add(viewPoint.getX());
            viewCoords.add(viewPoint.getY());
        }

        updateExploredView(character);
    }

    private static void updateExploredView(Character character) {
        if (character.getColor().equals(Battle.getPlayerColor())) {
            List<Polygon> exploredView = MapDrawer.getMapImage().getExploredView();
            List<Polygon> holesInView = MapDrawer.getMapImage().getHolesInView();
            PolygonsHelper.reduceHoles(holesInView, character.getView());
            if (PolygonsHelper.mergePolygons(exploredView, character.getView())) {
                PolygonsHelper.smoothPolygons(exploredView);
                PolygonsHelper.findHolesInPolygons(exploredView, holesInView);
                PolygonsHelper.smoothPolygons(holesInView);
                PolygonsHelper.removeSmall(holesInView);
                PolygonsHelper.splitHoles(holesInView);
                int zeros = 0;
                for (Double value: exploredView.get(0).getPoints()) {
                    if (value == 0)
                        zeros++;
                }
                System.out.println("exp points: " + exploredView.get(0).getPoints().size()/2);
                System.out.println("zeros: " + zeros);
//                System.out.println(Battle.getMap().getPoints().get(character.getPosition()).getHeight());
//                System.out.println("holes: " + holesInView.size());
            }
        }
    }

    private static Point2D calcViewPoint(Point pos, int viewDistMax, int angle) {
        double x = pos.getX() + viewDistMax * Math.sin(angle * Math.PI / 180);
        double y = pos.getY() + viewDistMax * Math.cos(angle * Math.PI / 180);
        int mapLimit = Battle.getMap().mapXPoints - 1;
        if (x < 0) {
            double leftPart = x/(x - pos.getX());
            x = 0;
            y = y + (pos.getY() - y) * leftPart;
        }
        if (x > mapLimit) {
            double leftPart = (mapLimit - pos.getX())/(x - pos.getX());
            x = mapLimit;
            y = pos.getY() + (y - pos.getY()) * leftPart;
        }
        if (y < 0) {
            double leftPart = y/(y - pos.getY());
            y = 0;
            x = x + (pos.getX() - x) * leftPart;
        }
        if (y > mapLimit) {
            double leftPart = (mapLimit - pos.getY())/(y - pos.getY());
            y = mapLimit;
            x = pos.getX() + (x - pos.getX()) * leftPart;
        }

        return new Point2D(x, y);
    }


}
