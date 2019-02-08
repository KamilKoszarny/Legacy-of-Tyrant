package model.actions.movement;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import model.character.Character;
import model.map.mapObjects.MapObject;
import viewIso.PathDrawer;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.List;

public class Char2ObjectMover {

    private static final int ACTION_DIST = 4;

    public static void calcPathAndStartRunToObject(Character character, MapObject mapObject) {
        Point objectPos = MapObjectDrawer.getMapObject2PointMap().get(mapObject);
        List<Point2D> path = PathCalculator.findPathToObject(objectPos, 2);
        if (path != null) {
            character.setPath(path);
            PathDrawer.createPathView(character, Color.WHITE);
        }
        CharMover.startMove(character);
    }

    public static boolean pathToObjectExists(Character character, MapObject mapObject) {
        Point objectPos = MapObjectDrawer.getMapObject2PointMap().get(mapObject);
        if (!closeToObject(character, mapObject)) {
            List<Point2D> path = PathCalculator.findPathToObject(objectPos, 2);
            if (path != null && !path.isEmpty()) {
                PathDrawer.showPathIfNotMoving(path, Color.WHITE);
                return true;
            }
        }
        return false;
    }

    public static boolean closeToObject(Character character, MapObject mapObject) {
        //TODO: dist by object
        Point objectPos = MapObjectDrawer.getMapObject2PointMap().get(mapObject);
        return objectPos.distance(character.getPosition()) < ACTION_DIST;
    }
}
