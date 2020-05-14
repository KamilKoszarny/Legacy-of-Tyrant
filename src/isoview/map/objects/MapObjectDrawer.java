package isoview.map.objects;

import isoview.IsoViewer;
import isoview.map.MapDrawCalculator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import model.Battle;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapObjectDrawer {

    private static java.util.Map<Point, MapObjectSprite> point2mapObjectSpriteMap = new HashMap<>();
    private static java.util.Map<MapObject, Point> mapObject2PointMap = new HashMap<>();
    private static boolean cutView = false;
    private static MapObject hoverObject = null;


    public MapObjectDrawer() {
        initSpriteMap();
    }

    public static void refreshSpriteMap(Point point) {
        MapPiece mapPiece = Battle.getMap().getPoints().get(point);
        MapObject mapObject = mapPiece.getObject();
        if (mapObject != null) {
            point2mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject()));
            mapObject2PointMap.put(mapPiece.getObject(), point);
        }
    }

    private void initSpriteMap() {
        for (Point point: Battle.getMap().getPoints().keySet()) {
            MapPiece mapPiece = Battle.getMap().getPoints().get(point);
            MapObject mapObject = mapPiece.getObject();
            if (mapObject != null) {
                point2mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject()));
                mapObject2PointMap.put(mapPiece.getObject(), point);
            }
        }
    }

    public static void drawObject (Point point) {
        MapObjectSprite mapObjectSprite = point2mapObjectSpriteMap.get(point);
        Image image = mapObjectSprite.getImage();
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        assert screenPos != null;
        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        if (cutView && mapObjectSprite.getObject().getType().isCutable()){
            drawCutImage(mapObjectSprite, image, screenPos, gc);
        } else {
            glowObject(mapObjectSprite, gc);
            if (mapObjectSprite.getObject().getType().equals(MapObjectType.ITEM))
                ItemObjectsDrawer.drawItemObject(mapObjectSprite, image, screenPos);
            else {
                gc.drawImage(image,
                    screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
            }
            gc.setEffect(null);
        }
    }

    private static void drawCutImage(MapObjectSprite mapObjectSprite, Image image, Point screenPos, GraphicsContext gc) {
        if (mapObjectSprite.getObject().getType().hasCutImage())
            gc.drawImage(mapObjectSprite.getCutImage(),
                    screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
        else {
            int sourceX = (int) (image.getWidth() * .4);
            int sourceY = (int) (mapObjectSprite.getOffset().y - (image.getHeight() - mapObjectSprite.getOffset().y) / 2);
            int sourceWidth = (int) (image.getWidth() * .2);
            int sourceHeight = (int) (image.getHeight() - mapObjectSprite.getOffset().y);

            glowObject(mapObjectSprite, gc);
            gc.drawImage(image, sourceX, sourceY, sourceWidth, sourceHeight,
                    screenPos.x - mapObjectSprite.getOffset().x + sourceX, screenPos.y - mapObjectSprite.getOffset().y + sourceY,
                    sourceWidth, sourceHeight);
            gc.setEffect(null);
        }
    }


    private static void glowObject(MapObjectSprite mapObjectSprite, GraphicsContext gc) {
        if (hoverObject != null && mapObjectSprite.getObject().equals(hoverObject)) {
            gc.setEffect(new Glow(1));
        }
    }

    public static java.util.Map<MapObject, Point> getMapObject2PointMap() {
        return mapObject2PointMap;
    }

    public static MapObject getHoverObject() {
        return hoverObject;
    }

    public static void setHoverObject(MapObject hoverObject) {
        MapObjectDrawer.hoverObject = hoverObject;
    }

    public static void switchCutView() {
        cutView = !cutView;
    }

    public static Map<Point, MapObjectSprite> getPoint2mapObjectSpriteMap() {
        return point2mapObjectSpriteMap;
    }

    public static boolean isCutView() {
        return cutView;
    }
}
