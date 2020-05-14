package model.map.visibility;

import isoview.map.MapDrawer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Battle;
import model.character.Character;

import java.awt.*;
import java.util.List;

public class VisibilityChecker {
    public static boolean isExplored(Point point) {
        Point2D point2D = new Point2D(point.x, point.y);
        List<Polygon> holesInView = MapDrawer.getMapImage().getHolesInView();
        for (Polygon hole : holesInView) {
            if (hole.contains(point2D))
                return false;
        }
        List<Polygon> exploredView = MapDrawer.getMapImage().getExploredView();
        for (Polygon polygon : exploredView) {
            if (polygon.contains(point2D))
                return true;
        }
        return false;
    }

    public static boolean isInChosenCharView(Point point) {
        Character chosenCharacter = Battle.getChosenCharacter();
        if (chosenCharacter == null)
            return false;
        Polygon view = chosenCharacter.getView();
        return view.contains(new Point2D(point.x, point.y));
    }

    public static boolean isInPlayerCharsView(Point point) {
        return isInCharsView(point, Battle.getPlayerCharacters());
    }

    public static boolean isInCharsView(Point point, List<Character> characters) {
        for (Character character : characters) {
            if (character.getView() != null && character.getView().contains(new Point2D(point.x, point.y)))
                return true;
        }
        return false;
    }

    public static boolean isInTeamView(Point point, Color color) {
        return isInTeamView(point, Battle.getCharacters(), color);
    }

    public static boolean isInTeamView(Point point, List<Character> characters, Color color) {
        for (Character character : characters) {
            if (character.getColor().equals(color) && character.getView() != null &&
                    character.getView().contains(new Point2D(point.x, point.y)))
                return true;
        }
        return false;
    }
}
