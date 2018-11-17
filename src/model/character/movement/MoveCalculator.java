package model.character.movement;

import model.character.Character;
import model.character.CharacterType;
import model.map.Map;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MoveCalculator {

    public static void moveCharacter(Character character, Point destination, Map map) {
        Point start = character.getPosition();

        double xFactor, yFactor, sum;
        double speed = character.getCurrentSA().speed / Map.RESOLUTION_M;

        if(character.isRunning())
            speed *= 2;
        if(character.isSneaking())
            speed /= 2;

        sum = Math.abs(destination.x - start.x) + Math.abs(destination.y - start.y);
        xFactor = (destination.x - start.x) / sum;
        yFactor = (destination.y - start.y) / sum;

        Point stop = new Point();
        stop.x = start.x + (int) (xFactor * speed * character.getMsLeft() / 1000);
        stop.y = start.y + (int) (yFactor * speed * character.getMsLeft() / 1000);

        Set<Point> pointsUnderMove = new HashSet<>();
        for (double i = 0; i < start.distance(stop); i += character.getType().getSize() / Map.RESOLUTION_M){
            character.setPosition(new Point((int)(start.x + i*xFactor), (int)(start.y + i*yFactor)));
            pointsUnderMove.addAll(calcPointsUnder(character, map));
        }

        double terrainFactor = calcTerrainFactor(pointsUnderMove, map);
        stop.x = start.x + (int)((stop.x - start.x) * terrainFactor);
        stop.y = start.y + (int)((stop.y - start.y) * terrainFactor);

        if (start.distance(stop) > start.distance(destination)) {
            character.setPosition(destination);
            character.setMsLeft(character.getMsLeft() - (int) (start.distance(destination) / speed * 1000));
            if (character.isRunning())
                character.setVigor((int) (character.getVigor() - start.distance(destination) / speed * 2));
        } else {
            character.setPosition(stop);
            character.setMsLeft(0);
            if (character.isRunning())
                character.setVigor((int) (character.getVigor() - start.distance(stop) / speed * 2));
        }
    }

    public static Set<Point> calcRelativePointsUnder(CharacterType characterType){
        Set<Point> relativePointsUnder = new LinkedHashSet<>();
        Point zeroPoint = new Point(0,0);
        Point p;
        double charRadiusPix = characterType.getSize() / 2;
        for (double i = -charRadiusPix; i < charRadiusPix; i += Map.RESOLUTION_M) {
            for (double j = -charRadiusPix; j < charRadiusPix; j += Map.RESOLUTION_M) {
                p = new Point((int)i, (int)j);
                if (p.distance(zeroPoint) < charRadiusPix)
                    relativePointsUnder.add(p);
            }
        }
        return relativePointsUnder;
    }

    public static Set<Point> calcPointsUnder(Character character, Map map){
        Set<Point> pointsUnder = new HashSet<>();
        for (Point rp: character.getType().getRelativePointsUnder()) {
            Point point = new Point(character.getPosition().x + rp.x, character.getPosition().y + rp.y);
            if (map.isOnMapPoints(point))
                pointsUnder.add(point);
        }
        return pointsUnder;
    }

    public static double calcTerrainFactor(Set<Point> points, Map map){
        double terrainFactor = 0;
        for (Point p: points) {
            terrainFactor += map.getPoints().get(p).getTerrain().getMoveFactor();
        }
        return terrainFactor/points.size();
    }
}
