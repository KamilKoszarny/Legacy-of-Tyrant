package model.character.movement;

import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import model.character.CharState;
import model.character.Character;
import model.map.Map;
import model.map.MapGridCalc;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CharMover {

    private static double POINTS_TO_NEXT_FRAME = 1.6;
    private static ArrayList<Character> characters;
    private static Set<int[]> charGridPositions;
    private static List<int[]> objectGridPositions = new ArrayList<>();

    public static void startRunCharacter(Character character, Point movePoint, Map map) {
        MapGridCalc.regenerateGridGraph(map, map.getPoints().keySet(), characters);
        MapGridCalc.clearGridGraphForChar(map, character);
        List<Point2D> path = PathFinder.calcPath(character.getPrecisePosition(), movePoint, map, true);
        character.setPath(path);
        character.setState(CharState.RUN);
        character.setDestination(movePoint);
        character.setPathSection(0);
        character.setCurrentSpeed(character.getDoubleSpeed() * 2);
    }

    public static void updateCharacterMove(Character character, int ms, Map map) {
        List<Point2D> path = character.getPath();
        int pathSection = character.getPathSection();
        boolean last = (pathSection == path.size() - 1);
        moveStraight(character, character.getPath().get(character.getPathSection()), ms, last, map);
    }

    private static void moveStraight(Character character, Point2D next, int ms, boolean last, Map map) {
        Point2D pos = character.getPrecisePosition();
        double distToNext = pos.distance(next);
        Point2D step = step(character, ms);

        if (distToNext >= step.magnitude()) {
            character.setPosition(new Point2D(pos.getX() + step.getX(), pos.getY() + step.getY()));
            if (distToNext%POINTS_TO_NEXT_FRAME <
                    GeomerticHelper.distTo0(step.getX(), step.getY()))
                CharsDrawer.nextFrame(character);
        }
        else if (last) {
            character.setPosition(character.getDestination());
            stopCharacter(character, map);
        } else {
            character.setPathSection(character.getPathSection() + 1);
            turnChar(character, character.getPath().get(character.getPathSection()));
        }
    }

    public static void stopCharacter(Character character, Map map) {
        character.setDestination(null);
        character.setState(CharState.IDLE);
        character.setCurrentSpeed(0);
        MapGridCalc.regenerateGridGraph(map, map.getPoints().keySet(), characters);
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
                character.getCurrentSpeed() * ms/1000 / Map.M_PER_POINT;
        changeY = Math.sin(((character.getPreciseDirection() + 1) / 8) * 2 * Math.PI) *
                character.getCurrentSpeed() * ms/1000 / Map.M_PER_POINT;
        return new Point2D(changeX, changeY);
    }

    public static void setCharacters(ArrayList<Character> characters) {
        CharMover.characters = characters;
    }

    public static void pushCharsToClosestWalkable(Map map){
        for (Character character: characters) {
            Point pos = character.getPosition();
            while (!map.getPoints().get(pos).isWalkable()) {
                int newX = Math.min(Math.max(pos.x + new Random().nextInt(3) - 1, 0), map.getWidth());
                int newY = Math.min(Math.max(pos.y + new Random().nextInt(3) - 1, 0), map.getHeight());
                character.setPosition(new Point(newX, newY));
                pos = character.getPosition();
            }
        }
    }
}