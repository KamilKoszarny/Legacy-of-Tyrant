package model.map.visibility;

import helpers.my.PolygonsHelper;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import main.App;
import model.Battle;
import model.character.Character;
import model.map.MapPiece;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;
import viewIso.panel.MinimapViewer;

import java.awt.*;
import java.util.List;

public class VisibilityCalculator {

    private static final int MIN_VISIBILITY_RADIUS_DAY = 50;
    private static final int ANGLE_RESOLUTION = 1;
    private static boolean change = true;

    public static void updateViews() {
        if (change) {
            for (Character character : Battle.getCharacters()) {
                App.resetTime(3);
                calcCharView(character);
                App.showAndResetTime("calcCharView", 3);
                if (!Battle.getMap().isDiscovered()) {
                    updateExploredView(character);
                    App.showAndResetTime("updateExpView", 3);
                }
            }
        }
        change = false;
    }

    private static void calcCharView(Character character) {
        Point pos = character.getPosition();
        double dir = character.getPreciseDirection();
        List<Double> viewCoords = character.getView().getPoints();
        viewCoords.clear();
        viewCoords.add(pos.getX());
        viewCoords.add(pos.getY());

        int viewDistMax = MIN_VISIBILITY_RADIUS_DAY + character.getStats().getEye()/2;
        int viewAngleRange = 90 + character.getStats().getEye(); // to add helmet
        int viewAngleBase = (int) ((1 - dir) * 360 / 8);

        for (int angle = viewAngleBase - viewAngleRange/2; angle < viewAngleBase + viewAngleRange/2; angle += ANGLE_RESOLUTION) {
            int viewDist = calcViewDist(viewDistMax, Math.abs(angle - viewAngleBase)/(double)viewAngleRange);
            Point2D viewPoint = calcViewPoint(pos, viewDist, angle);
            viewCoords.add(viewPoint.getX());
            viewCoords.add(viewPoint.getY());
        }
    }

    private static int calcViewDist(int viewDistMax, double angleDeviationRatio) {
        return (int) (viewDistMax * (1 - Math.pow(angleDeviationRatio,2 )));
    }

    private static Point2D calcViewPoint(Point pos, int viewDist, int angle) {
        double stepX = Math.sin(angle * Math.PI / 180);
        double stepY = Math.cos(angle * Math.PI / 180);
        Point2D stepVector = new Point2D(stepX, stepY);
        Point2D viewPoint = leadViewRay(pos, stepVector, viewDist);
        viewPoint = reduceToMap(viewPoint, pos);

        return viewPoint;
    }

    private static Point2D leadViewRay(Point pos, Point2D stepVector, double viewDist) {
        double x = pos.getX(), y = pos.getY();
        for (int i = 0; i <= viewDist; i++) {
            Point point = new Point((int)x, (int)y);
            if(MapDrawCalculator.isOnMap(point)) {
                MapPiece mapPiece = Battle.getMap().getPoints().get(point);
                viewDist *= mapPiece.getTransparency();
            }
            x = pos.getX() + i*stepVector.getX();
            y = pos.getY() + i*stepVector.getY();
        }
        return new Point2D(x, y);
    }

    private static Point2D reduceToMap(Point2D viewPoint, Point pos) {
        double x = viewPoint.getX();
        double y = viewPoint.getY();
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

    private static void updateExploredView(Character character) {
        if (character.getColor().equals(Battle.getPlayerColor())) {
            List<Polygon> exploredView = MapDrawer.getMapImage().getExploredView();
            List<Polygon> holesInView = MapDrawer.getMapImage().getHolesInView();

            App.resetTime(5);
            PolygonsHelper.mergePolygon2Polygons(exploredView, character.getView());
            App.showAndResetTime("mergePolygon2Polygons", 5);
            PolygonsHelper.smoothPolygons(exploredView, 8);
            App.showAndResetTime("smoothPolygons", 5);
            PolygonsHelper.findHolesInPolygons(exploredView, holesInView);
            App.showAndResetTime("findHolesInPolygons", 5);
            PolygonsHelper.reduceHoles(holesInView, character.getView());
            PolygonsHelper.splitHoles(holesInView);
            App.showAndResetTime("reduceAndSplitHoles", 5);
//            PolygonsHelper.removeSmall(holesInView);
            PolygonsHelper.smoothPolygons(holesInView, 5);
            App.showAndResetTime("smoothHoles", 5);

//            PanelViewer.setMinimapImg(MapImageGenerator.updateMinimapImageWithFog());
            MinimapViewer.refreshMinimapFog(exploredView.get(0), holesInView);
            App.showAndResetTime("updateMinimap", 5);

//            soutPolygons(exploredView, holesInView);
        }
    }

    private static void soutPolygons(List<Polygon> exploredView, List<Polygon> holesInView) {
        System.out.println(exploredView.get(0).getPoints().size()/2);
        System.out.println(exploredView.get(0).getPoints());
        for (Polygon hole: holesInView) {
                System.out.println("hole: " + holesInView.indexOf(hole) + " " + hole.getPoints());
        }
    }

    public static void setChange(boolean change) {
        VisibilityCalculator.change = change;
    }

}
