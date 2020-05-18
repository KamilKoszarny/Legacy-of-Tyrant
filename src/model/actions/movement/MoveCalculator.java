package model.actions.movement;

import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import model.Battle;
import model.character.Character;
import model.character.CharacterType;
import model.character.Stats;
import model.map.Map;

import java.awt.*;
import java.util.List;
import java.util.*;

public class MoveCalculator {

    public static final float RUN_START_AP_COST = 2.0f;
    private static final double AP_PER_TIME_UNIT = 5;

    private MoveCalculator() {
    }

    static double calcSpeed(Character character) {
        double speed = character.getStats().getSpeedMax();
        if(character.isRunning())
            speed *= 2;
        if(character.isSneaking())
            speed /= 2;
        return speed;
    }

    static void updateRunStartVigorAndActionPoints(Character character) {
        character.getStats().subtractActionPoints(RUN_START_AP_COST);
    }

    static void updateVigorAndActionPoints(Character character, Point2D posBefore, Point2D posAfter) {
        final double VIGOR_PER_TIME_UNIT = .3;
        Stats stats = character.getStats();
        double time = posAfter.distance(posBefore) / stats.getSpeed();
        double costAP = time * AP_PER_TIME_UNIT;
        double costVigor = time * VIGOR_PER_TIME_UNIT;
        if(character.isRunning()) {
            costVigor *= 2;
        }
        stats.subtractActionPoints(costAP);
        stats.subtractVigor(costVigor);
    }

    public static float calPathAPCost(Character character) {
        Point2D posBefore = character.getPrecisePosition();
        float costAP = 0;
        for (Point2D posAfter: character.getPath()) {
            costAP += posAfter.distance(posBefore) / character.getStats().getSpeedMax() * AP_PER_TIME_UNIT;
        }
        float initialTurnCost = CharTurner.calcTurnAPCost(character, GeomerticHelper.point2DtoPoint(character.getPath().get(1)));
        return (float) ((costAP / 2.) - 1) + RUN_START_AP_COST + initialTurnCost; //todo: as speed, why - 1
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

    static Point2D calcStep(Character character, int ms) {
        double changeX, changeY;
        changeX = Math.cos(((character.getPreciseDirection() + 1) / 8) * 2 * Math.PI) *
                character.getStats().getSpeed() * ms/1000 / Map.M_PER_POINT;
        changeY = Math.sin(((character.getPreciseDirection() + 1) / 8) * 2 * Math.PI) *
                character.getStats().getSpeed() * ms/1000 / Map.M_PER_POINT;
        return new Point2D(changeX, changeY);
    }

    public static void pushCharsToClosestWalkable(){
        pushCharsToClosestWalkable(Battle.getMap(), Battle.getCharacters());
    }

    public static void pushCharsToClosestWalkable(Map map, List<Character> characters){
        for (Character character: characters)
            pushCharToClosestWalkable(character, map, characters);
    }

    public static void pushCharToClosestWalkable(Character character, Map map, List<Character> characters){
        Point pos = character.getPosition();
        while (!map.getPoints().get(pos).isWalkable() || pointOccupiedByOther(character, characters)) {
            int newX = Math.min(Math.max(pos.x + new Random().nextInt(3) - 1, 0), map.getWidth());
            int newY = Math.min(Math.max(pos.y + new Random().nextInt(3) - 1, 0), map.getHeight());
            character.setPosition(new Point(newX, newY));
            pos = character.getPosition();
        }
    }

    public static boolean pointOccupiedByOther(Character character, List<Character> characters) {
        Point point = character.getPosition();
        for (Character otherChar : characters) {
            if (!otherChar.equals(character) && otherChar.getPosition().equals(point))
                return true;
        }
        return false;
    }

//    public static void moveCharacter(Character character, Point destination, Map map) {
//        Point start = character.getPosition();
//
//        double xFactor, yFactor, sum;
//        double speed = calcSpeed(character);
//
//        sum = Math.abs(destination.x - start.x) + Math.abs(destination.y - start.y);
//        xFactor = (destination.x - start.x) / sum;
//        yFactor = (destination.y - start.y) / sum;
//
//        Point stop = new Point();
//        stop.x = start.x + (int) (xFactor * speed * character.getStats().getActionPoints() / 1000);
//        stop.y = start.y + (int) (yFactor * speed * character.getStats().getActionPoints() / 1000);
//
//        Set<Point> pointsUnderMove = new HashSet<>();
//        for (double i = 0; i < start.distance(stop); i += character.getType().getSize() / Map.RESOLUTION_M){
//            character.setPosition(new Point((int)(start.x + i*xFactor), (int)(start.y + i*yFactor)));
//            pointsUnderMove.addAll(calcPointsUnder(character, map));
//        }
//
//        double terrainFactor = calcTerrainFactor(pointsUnderMove, map);
//        stop.x = start.x + (int)((stop.x - start.x) * terrainFactor);
//        stop.y = start.y + (int)((stop.y - start.y) * terrainFactor);
//
//        if (start.distance(stop) > start.distance(destination)) {
//            character.setPosition(destination);
//            character.getStats().setActionPoints(character.getStats().getActionPoints() - (start.distance(destination) / speed * 1000));
//            if (character.isRunning())
//                character.getStats().setVigor((int) (character.getStats().getVigor() - start.distance(destination) / speed * 2));
//        } else {
//            character.setPosition(stop);
//            character.getStats().setActionPoints(0);
//            if (character.isRunning())
//                character.getStats().setVigor((int) (character.getStats().getVigor() - start.distance(stop) / speed * 2));
//        }
//    }
}
