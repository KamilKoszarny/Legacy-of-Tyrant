package model.actions;

import helpers.my.GeomerticHelper;
import model.Battle;
import model.actions.movement.CharMover;
import model.map.Map;
import model.map.MapGridCalc;
import model.map.MapPiece;
import model.map.buildings.Door;
import model.map.lights.VisibilityCalculator;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.List;

public class DoorActioner {

    public static void openDoor(MapObject object) {
        Map map = Battle.getMap();
        Door door = (Door) object;
        door.switchOpen();

        Point objectPoint = MapObjectDrawer.getMapObject2PointMap().get(door);

        int newLook = door.getLook() + 1;
        Point newPoint = null;
        switch (newLook%4) {
            case 0:{
                newLook -= 4;
                newPoint = new Point(objectPoint.x - 1, objectPoint.y + 1); break;
            }
            case 1: newPoint = new Point(objectPoint.x + 1, objectPoint.y + 1); break;
            case 2: newPoint = new Point(objectPoint.x + 1, objectPoint.y - 1); break;
            case 3: newPoint = new Point(objectPoint.x - 1, objectPoint.y - 1); break;
        }
        door.setLook(newLook);
        refreshMapObjects(map, door, objectPoint, newPoint);
        refreshWalkables(map, objectPoint, newLook, newPoint, true);
        CharMover.pushCharsToClosestWalkable(Battle.getMap());
        MapGridCalc.regenerateGridGraph(Battle.getMap(), GeomerticHelper.pointsInSquare(objectPoint, 4, map), Battle.getCharacters());
        VisibilityCalculator.setChange(true);
    }

    public static void closeDoor(MapObject object) {
        Map map = Battle.getMap();
        Door door = (Door) object;
        door.switchOpen();

        Point objectPoint = MapObjectDrawer.getMapObject2PointMap().get(door);

        int newLook = door.getLook() - 1;
        Point newPoint = null;
        switch (newLook%4) {
            case 0: newPoint = new Point(objectPoint.x - 1, objectPoint.y - 1); break;
            case 1: newPoint = new Point(objectPoint.x - 1, objectPoint.y + 1); break;
            case 2: newPoint = new Point(objectPoint.x + 1, objectPoint.y + 1); break;
            case 3: {
                newLook += 4;
                newPoint = new Point(objectPoint.x + 1, objectPoint.y - 1); break;
            }
        }
        door.setLook(newLook);
        refreshMapObjects(map, door, objectPoint, newPoint);
        refreshWalkables(map, objectPoint, newLook, newPoint, false);
        CharMover.pushCharsToClosestWalkable(Battle.getMap());
        MapGridCalc.regenerateGridGraph(Battle.getMap(), GeomerticHelper.pointsInSquare(objectPoint, 4, map), Battle.getCharacters());
        VisibilityCalculator.setChange(true);
    }

    private static void refreshWalkables(Map map, Point objectPoint, int newLook, Point newPoint, boolean open) {
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

    private static void refreshMapObjects(Map map, Door door, Point objectPoint, Point newPoint) {
        MapPiece newMapPiece;
        MapPiece mapPiece = Battle.getMap().getPoints().get(objectPoint);
        newMapPiece = Battle.getMap().getPoints().get(newPoint);
        mapPiece.setObject(null);
        MapObjectDrawer.refreshSpriteMap(objectPoint);
        newMapPiece.setObject(door);
        MapObjectDrawer.refreshSpriteMap(newPoint);
    }
}
