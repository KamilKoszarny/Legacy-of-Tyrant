package model.actions.movement;

import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import model.Battle;
import model.character.CharState;
import model.character.Character;
import model.map.Map;
import model.map.GridGrapCalculator;
import model.map.lights.VisibilityCalculator;
import viewIso.PathDrawer;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CharMover {

    private static double POINTS_TO_NEXT_FRAME = 1.6;

    public static void calcPathAndStartRun(Character character, Point destination) {
        List<Point2D> path = PathCalculator.calcPath(character, destination);
        if (path != null) {
            character.setPath(path);
            PathDrawer.createPathView(character);
        }
        startRun(character);
    }

    public static void startRun(Character character) {
        List<Point2D> path = character.getPath();
        if (path == null)
            return;
        Point destination = new Point((int)path.get(path.size() - 1).getX(), (int)path.get(path.size() - 1).getY());
        character.setState(CharState.RUN);
        character.setDestination(destination);
        character.setPathSection(0);
        character.getStats().setSpeed(character.getStats().getSpeedMax() * 2);
    }

    public static void updateCharacterMove(Character character, int ms) {
        List<Point2D> path = character.getPath();
        int pathSection = character.getPathSection();
        boolean last = (pathSection == path.size() - 1);
        moveStraight(character, character.getPath().get(character.getPathSection()), ms, last);
        VisibilityCalculator.setChange(true);
    }

    private static void moveStraight(Character character, Point2D next, int ms, boolean last) {
        Point2D pos = character.getPrecisePosition();
        double distToNext = pos.distance(next);
        Point2D step = step(character, ms);

        final double SHORT_PATH_CORRECTION = 2;
        if (distToNext > step.magnitude() * SHORT_PATH_CORRECTION) {
            character.setPosition(new Point2D(pos.getX() + step.getX(), pos.getY() + step.getY()));
            if (distToNext%POINTS_TO_NEXT_FRAME < GeomerticHelper.distTo0(step.getX(), step.getY()))
                CharsDrawer.nextFrame(character, 0);
        }
        else if (last) {
            character.setPosition(character.getDestination());
            stopCharacter(character);
        } else {
            character.setPathSection(character.getPathSection() + 1);
            turnChar(character, character.getPath().get(character.getPathSection()));
        }
    }

    public static void stopCharacter(Character character) {
        character.setDestination(null);
        character.setPath(null);
        character.setPathView(null);
        character.setState(CharState.IDLE);
        character.getStats().setSpeed(0);
        GridGrapCalculator.regenerateGridGraph(Battle.getMap(), Battle.getMap().getPoints().keySet(), Battle.getCharacters());
        pushCharToClosestWalkable(character, Battle.getMap(), Battle.getCharacters());
    }

    private static void turnChar(Character character, Point2D next) {
        CharTurner.turnCharacter(character, next, false);
        Point charPos = character.getPosition();
        double moveDir = ((Math.atan2(next.getY() - charPos.y, next.getX() - charPos.x) * 8 / 2. / Math.PI) + 7)%8;
        character.setPreciseDirection(moveDir);
    }

    private static Point2D step(Character character, int ms) {
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


}