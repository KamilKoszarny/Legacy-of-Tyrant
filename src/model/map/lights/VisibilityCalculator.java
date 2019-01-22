package model.map.lights;

import model.Battle;
import model.character.Character;

import java.awt.*;
import java.util.List;

public class VisibilityCalculator {

    private static final int MIN_VISIBILITY_RADIUS_DAY = 20;

    public static void calcCharView(Character character) {
        Point pos = character.getPosition();
        int dir = character.getDirection();
        List<Double> viewCoords = character.getView().getPoints();
        viewCoords.clear();
        viewCoords.add(pos.getX());
        viewCoords.add(pos.getY());

        int viewDistMax = 50 + character.getStats().getEye()/2;
        int viewAngleRange = 90 + character.getStats().getEye(); // to add helmet
        int viewAngleStart = ((1 - dir) * 360 / 8 - viewAngleRange/2);


        for (int angle = viewAngleStart; angle < viewAngleStart + viewAngleRange; angle += 2) {
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

            viewCoords.add(x);
            viewCoords.add(y);
        }
    }
}
