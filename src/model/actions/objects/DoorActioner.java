package model.actions.objects;

import helpers.my.GeomerticHelper;
import model.Battle;
import model.actions.movement.MoveCalculator;
import model.character.Character;
import model.map.Map;
import model.map.GridGraphCalculator;
import model.map.MapPiece;
import model.map.buildings.Door;
import model.map.lights.VisibilityCalculator;
import model.map.mapObjects.MapObjectType;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.List;

public class DoorActioner {

    public static void openDoor(Character character, Door door) {
        Map map = Battle.getMap();
        Point objectPoint = MapObjectDrawer.getMapObject2PointMap().get(door);

        door.switchOpen();

        int newLook = 1000 + (door.getLook() + 1)%4;
        Point newPoint = calcNewPoint(objectPoint, newLook, true);
        door.setLook(newLook);
        refreshMapObjects(door, objectPoint, newPoint);
        refreshWalkables(objectPoint, newLook, newPoint, true);
        MoveCalculator.pushCharsToClosestWalkable();
        GridGraphCalculator.regenerateGridGraph(map, GeomerticHelper.pointsInSquare(objectPoint, 4, map), Battle.getCharacters());
        VisibilityCalculator.setChange(true);
    }

    public static void main(String[] args) {
        System.out.println(-1%4);
    }

    public static void closeDoor(Character character, Door door) {
        Map map = Battle.getMap();
        Point objectPoint = MapObjectDrawer.getMapObject2PointMap().get(door);

        door.switchOpen();

        int newLook = 1000 + (door.getLook() + 3)%4;
        Point newPoint = calcNewPoint(objectPoint, newLook, false);
        door.setLook(newLook);
        refreshMapObjects(door, objectPoint, newPoint);
        refreshWalkables(objectPoint, newLook, newPoint, false);
        MoveCalculator.pushCharsToClosestWalkable();
        GridGraphCalculator.regenerateGridGraph(Battle.getMap(), GeomerticHelper.pointsInSquare(objectPoint, 4, map), Battle.getCharacters());
        VisibilityCalculator.setChange(true);
    }

    private static Point calcNewPoint(Point objectPoint, int newLook, boolean open) {
        final int[] OPEN_COORDS = new int[]{-1, 1, 1, 1, 1, -1, -1, -1};
        final int[] CLOSE_COORDS = new int[]{-1, -1, -1, 1, 1, 1, 1, -1};
        int[] coords = open ? OPEN_COORDS : CLOSE_COORDS;

        Point newPoint = null;
        switch (newLook%4) {
            case 0: newPoint = new Point(objectPoint.x + coords[0], objectPoint.y + coords[1]); break;
            case 1: newPoint = new Point(objectPoint.x + coords[2], objectPoint.y + coords[3]); break;
            case 2: newPoint = new Point(objectPoint.x + coords[4], objectPoint.y + coords[5]); break;
            case 3: newPoint = new Point(objectPoint.x + coords[6], objectPoint.y + coords[7]); break;
        }
        return newPoint;
    }

    private static void refreshWalkables(Point objectPoint, int newLook, Point newPoint, boolean open) {
        Map map = Battle.getMap();
        MapPiece mapPiece;
        List<Point> walkablePoints;
        if (open)
            walkablePoints = GeomerticHelper.pointsInSquare(objectPoint, 1, map);
        else
            walkablePoints = GeomerticHelper.pointsInRect(objectPoint, 1 - newLook%2, newLook%2, map);
        for (Point point: walkablePoints) {
            mapPiece = Battle.getMap().getPoints().get(point);
            mapPiece.setWalkable(true);
            mapPiece.setTransparency(1);
        }
        for (Point point: GeomerticHelper.pointsInRect(newPoint, 1 - newLook%2, newLook%2, map)) {
            mapPiece = Battle.getMap().getPoints().get(point);
            mapPiece.setWalkable(false);
            mapPiece.setTransparency(MapObjectType.DOOR.getTransparency());
        }
    }

    private static void refreshMapObjects(Door door, Point objectPoint, Point newPoint) {
        MapPiece newMapPiece;
        MapPiece mapPiece = Battle.getMap().getPoints().get(objectPoint);
        newMapPiece = Battle.getMap().getPoints().get(newPoint);
        mapPiece.setObject(null);
        MapObjectDrawer.refreshSpriteMap(objectPoint);
        newMapPiece.setObject(door);
        MapObjectDrawer.refreshSpriteMap(newPoint);
    }
}
