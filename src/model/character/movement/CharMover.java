package model.character.movement;

import helpers.downloaded.pathfinding.grid.GridGraph;
import helpers.my.GeomerticHelper;
import javafx.geometry.Point2D;
import model.character.CharState;
import model.character.Character;
import model.map.Map;
import model.map.MapPiece;
import viewIso.characters.CharDrawer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CharMover {

    private static double POINTS_TO_NEXT_FRAME = 1.6;
    private static ArrayList<Character> characters;
    private static Set<int[]> charGridPositions;
    private static List<int[]> objectGridPositions = new ArrayList<>();

    public static void startRunCharacter(Character character, Point movePoint, Map map) {
        fillCharGridPositions(character, map);
        List<Point2D> path = PathFinder.calcPath(character.getPrecisePosition(), movePoint, map, true);
        clearCharGridPositions(map);
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
        blockPiecesWithChar(false, map);
        moveStraight(character, character.getPath().get(character.getPathSection()), ms, last);
        blockPiecesWithChar(true, map);
    }

    private static void moveStraight(Character character, Point2D next, int ms, boolean last) {
        Point2D pos = character.getPrecisePosition();
        double distToNext = pos.distance(next);
        Point2D step = step(character, ms);

        if (distToNext >= step.magnitude()) {
            character.setPosition(new Point2D(pos.getX() + step.getX(), pos.getY() + step.getY()));
            if (distToNext%POINTS_TO_NEXT_FRAME <
                    GeomerticHelper.distTo0(step.getX(), step.getY()))
                CharDrawer.nextFrame(character);
        }
        else if (last) {
            character.setPosition(character.getDestination());
            character.setDestination(null);
            character.setState(CharState.IDLE);
            character.setCurrentSpeed(0);
        } else {
            character.setPathSection(character.getPathSection() + 1);
            turnChar(character, character.getPath().get(character.getPathSection()));
        }
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

    @SuppressWarnings("PointlessArithmeticExpression")
    private static void fillCharGridPositions(Character character, Map map) {
        GridGraph gridGraph = map.getGridGraph();
        charGridPositions = new HashSet<>();
        List<Character> otherCharacters = (List<Character>) characters.clone();
        otherCharacters.remove(character);
        for (Character otherChar : otherCharacters) {
            List<Point> charClosePoints = GeomerticHelper.pointsInRadius(otherChar.getPosition(), 2, map);
            for (Point point: charClosePoints) {
                int[] gridPoint = PathFinder.gridPointByMapPoint(point);
                charGridPositions.add(new int[]{gridPoint[0] - 1, gridPoint[1] + 2});
                charGridPositions.add(new int[]{gridPoint[0] + 0, gridPoint[1] + 1});
                charGridPositions.add(new int[]{gridPoint[0] + 0, gridPoint[1] + 2});
                charGridPositions.add(new int[]{gridPoint[0] + 1, gridPoint[1] - 1});
                charGridPositions.add(new int[]{gridPoint[0] + 1, gridPoint[1] + 0});
                charGridPositions.add(new int[]{gridPoint[0] + 1, gridPoint[1] + 1});
                charGridPositions.add(new int[]{gridPoint[0] + 1, gridPoint[1] + 2});
                charGridPositions.add(new int[]{gridPoint[0] + 1, gridPoint[1] + 3});
                charGridPositions.add(new int[]{gridPoint[0] + 2, gridPoint[1] + 0});
                charGridPositions.add(new int[]{gridPoint[0] + 2, gridPoint[1] + 1});
                charGridPositions.add(new int[]{gridPoint[0] + 2, gridPoint[1] + 2});
                charGridPositions.add(new int[]{gridPoint[0] + 2, gridPoint[1] + 3});
                charGridPositions.add(new int[]{gridPoint[0] + 2, gridPoint[1] + 4});
                charGridPositions.add(new int[]{gridPoint[0] + 3, gridPoint[1] + 1});
                charGridPositions.add(new int[]{gridPoint[0] + 3, gridPoint[1] + 2});
                charGridPositions.add(new int[]{gridPoint[0] + 4, gridPoint[1] + 1});
            }
        }
        for (int[] gridPoint: charGridPositions) {
            if (gridGraph.isBlocked(gridPoint[0], gridPoint[1])) {
                objectGridPositions.add(gridPoint);
            } else
                gridGraph.setBlocked(gridPoint[0], gridPoint[1], true);
        }
        charGridPositions.removeAll(objectGridPositions);
    }

    private static void clearCharGridPositions(Map map) {
        GridGraph gridGraph = map.getGridGraph();
        for (int[] gridPoint: charGridPositions) {
            gridGraph.setBlocked(gridPoint[0], gridPoint[1], false);
        }
    }

    public static void setCharacters(ArrayList<Character> characters) {
        CharMover.characters = characters;
    }

    public static void blockPiecesWithChar(boolean block, Map map) {
        for (Character character: characters) {
            List<Point> closePoints = GeomerticHelper.pointsInRadius(character.getPosition(), 2, map);
            MapPiece mapPiece;
            for (Point point : closePoints) {
                mapPiece = map.getPoints().get(point);
                mapPiece.setWalkable(!block);
            }
        }
    }
}