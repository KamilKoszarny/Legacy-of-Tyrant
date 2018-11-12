package viewIso.mapObjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import model.map.Map;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.util.HashMap;

public class MapObjectDrawer {

    private Map map;
    private Canvas canvas;
    private static java.util.Map<Point, MapObjectSprite> mapObjectSpriteMap = new HashMap<>();
    private static java.util.Map<MapObject, Point> mapObjectPointMap = new HashMap<>();
    private static boolean cutView;

    public MapObjectDrawer(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        initSpriteMap();
    }

    public static void refreshSpriteMap(Point point, Map map) {
        MapPiece mapPiece = map.getPoints().get(point);
        MapObject mapObject = mapPiece.getObject();
        if (mapObject != null) {
            mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject(), mapObject.isCutable()));
            mapObjectPointMap.put(mapPiece.getObject(), point);
        }
    }

    private void initSpriteMap() {
        for (Point point: map.getPoints().keySet()) {
            MapPiece mapPiece = map.getPoints().get(point);
            MapObject mapObject = mapPiece.getObject();
            if (mapObject != null) {
                mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject(), mapObject.isCutable()));
                mapObjectPointMap.put(mapPiece.getObject(), point);
            }
        }
    }

    public void drawObject (Point point, boolean cutView) {
        this.cutView = cutView;
        MapObjectSprite mapObjectSprite = mapObjectSpriteMap.get(point);
        Image image = mapObjectSprite.getImage();
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        if (cutView && mapObjectSprite.isCutable()){
            int sourceX = (int) (image.getWidth() * .4);
            int sourceY = (int)(mapObjectSprite.getOffset().y - (image.getHeight() - mapObjectSprite.getOffset().y)/2);
            int sourceWidth = (int) (image.getWidth() * .2);
            int sourceHeight = (int)(image.getHeight() - mapObjectSprite.getOffset().y);
            canvas.getGraphicsContext2D().drawImage(image,
                    sourceX, sourceY,
                    sourceWidth, sourceHeight,
                    screenPos.x - mapObjectSprite.getOffset().x + sourceX, screenPos.y - mapObjectSprite.getOffset().y + sourceY,
                    sourceWidth, sourceHeight);
        } else
            canvas.getGraphicsContext2D().drawImage(image,
                screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
    }

    public static MapObject clickedObject(Point clickPoint) {
        if (cutView)
            return null;
        for (Point point: mapObjectSpriteMap.keySet()) {
            MapObjectSprite sprite =  mapObjectSpriteMap.get(point);
            Rectangle clickBox = calcClickBox(sprite, point);
            if (clickBox.contains(clickPoint)){
                if (sprite.getObject().getType().isClickable())
                    return sprite.getObject();
            }
        }
        return null;
    }

    private static Rectangle calcClickBox(MapObjectSprite sprite, Point spritePos) {
        Point spriteScreenPos = MapDrawCalculator.screenPositionWithHeight(spritePos);
        int width = (int) sprite.getImage().getWidth();
        int height = (int) sprite.getImage().getHeight();
        return new Rectangle(spriteScreenPos.x - width/2, (int) (spriteScreenPos.y - height*MapObjectSprite.Y_BASE_RATIO), width, height);
    }

    public static java.util.Map<MapObject, Point> getMapObjectPointMap() {
        return mapObjectPointMap;
    }
}
