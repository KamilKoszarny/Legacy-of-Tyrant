package model.actions.movement;

import helpers.my.GeomerticHelper;
import isoview.PathDrawer;
import isoview.characters.CharsDrawer;
import javafx.geometry.Point2D;
import model.Battle;
import model.character.CharState;
import model.character.Character;
import model.map.GridGraphCalculator;
import model.map.visibility.VisibilityCalculator;

import java.awt.*;
import java.util.List;

public class CharMover {

    private static final double POINTS_TO_NEXT_FRAME = 1.6;

    private CharMover() {
    }

    public static void calcPathAndStartMove(Character character, Point destination) {
        List<Point2D> path = PathCalculator.calcPath(character, destination);
        if (path != null) {
            character.setPath(path);
            PathDrawer.createPathView(character, character.getColor());
        }
        startMove(character);
    }

    public static void startMove(Character character) {
        List<Point2D> path = character.getPath();
        if (path == null)
            return;
        Point destination = new Point((int)path.get(path.size() - 1).getX(), (int)path.get(path.size() - 1).getY());
        character.setState(CharState.RUN);
        character.setDestination(destination);
        character.setPathSection(0);
        character.getStats().setSpeed(MoveCalculator.calcSpeed(character));
        MoveCalculator.updateRunStartVigorAndActionPoints(character);
        CharTurner.turnStandingCharacter(character, GeomerticHelper.point2DtoPoint(character.getPath().get(1)), false);
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
        Point2D step = MoveCalculator.calcStep(character, ms);

        final double SHORT_PATH_CORRECTION = 2;
        if (distToNext > step.magnitude() * SHORT_PATH_CORRECTION) {
            character.setPosition(new Point2D(pos.getX() + step.getX(), pos.getY() + step.getY()));
            if (distToNext%POINTS_TO_NEXT_FRAME < GeomerticHelper.distTo0(step.getX(), step.getY()))
                CharsDrawer.nextFrame(character, 0);
            MoveCalculator.updateVigorAndActionPoints(character, pos, character.getPrecisePosition());
            if (Battle.isTurnMode() && character.getStats().getActionPoints() <= 0)
                haltCharacter(character);
        }
        else if (last) {
            character.setPosition(character.getDestination());
            stopCharacter(character);
        } else {
            character.setPathSection(character.getPathSection() + 1);
            CharTurner.turnMovingChar(character, character.getPath().get(character.getPathSection()));
        }
    }

    public static void stopCharacter(Character character) {
        character.setDestination(null);
        character.setPath(null);
        character.setPathView(null);
        character.setState(CharState.IDLE);
        haltCharacter(character);
    }

    public static void haltAllChars() {
        for (Character character: Battle.getCharacters()) {
            haltCharacter(character);
        }
    }

    private static void haltCharacter(Character character) {
        character.getStats().setSpeed(0);
        GridGraphCalculator.regenerateGridGraph(Battle.getMap(), Battle.getMap().getPoints().keySet(), Battle.getCharacters());
        MoveCalculator.pushCharToClosestWalkable(character, Battle.getMap(), Battle.getCharacters());
    }


}
