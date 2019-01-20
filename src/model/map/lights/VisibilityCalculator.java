package model.map.lights;

import model.character.Character;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class VisibilityCalculator {

    private static final int MIN_VISIBILITY_RADIUS_DAY = 20;

    public static Map<Point, Integer> calcLightMap(Character character) {
        Map<Point, Integer> point2lightMap = calcPoint2LightMap(character);

        return point2lightMap;
    }

    private static Map<Point, Integer> calcPoint2LightMap(Character character) {
        Map<Point, Integer> point2lightMap = new HashMap<>();
        int maxVisibilityRadius = MIN_VISIBILITY_RADIUS_DAY + character.getStats().getEye()/3;
        Point charPos = character.getPosition();
        for (int y = -MIN_VISIBILITY_RADIUS_DAY; y <= MIN_VISIBILITY_RADIUS_DAY; y++) {
            for (int x = -MIN_VISIBILITY_RADIUS_DAY; x <= MIN_VISIBILITY_RADIUS_DAY; x++) {
                Point point = new Point(charPos.x + x, charPos.y + y);
                int dist = (int) charPos.distance(point);
                if (MapDrawCalculator.isOnMap(point) && dist <= maxVisibilityRadius) {
                    point2lightMap.put(point, 100 - dist);
                }
            }
        }
        return point2lightMap;
    }


}
