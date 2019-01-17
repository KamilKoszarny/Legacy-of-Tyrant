package viewIso.mapObjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import model.Battle;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;
import viewIso.IsoViewer;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapObjectDrawer {

    private static java.util.Map<Point, MapObjectSprite> point2mapObjectSpriteMap = new HashMap<>();
    private static java.util.Map<MapObject, Point> mapObject2PointMap = new HashMap<>();
    private static boolean cutView;
    private static Tooltip canvasTooltip = null;


    public MapObjectDrawer() {
        initSpriteMap();
    }

    public static void refreshSpriteMap(Point point) {
        MapPiece mapPiece = Battle.getMap().getPoints().get(point);
        MapObject mapObject = mapPiece.getObject();
        if (mapObject != null) {
            point2mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject(), mapObject.isCutable()));
            mapObject2PointMap.put(mapPiece.getObject(), point);
        }
    }

    private void initSpriteMap() {
        for (Point point: Battle.getMap().getPoints().keySet()) {
            MapPiece mapPiece = Battle.getMap().getPoints().get(point);
            MapObject mapObject = mapPiece.getObject();
            if (mapObject != null) {
                point2mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject(), mapObject.isCutable()));
                mapObject2PointMap.put(mapPiece.getObject(), point);
            }
        }
    }

    public void drawObject (Point point, boolean cutView) {
        MapObjectDrawer.cutView = cutView;
        MapObjectSprite mapObjectSprite = point2mapObjectSpriteMap.get(point);
        Image image = mapObjectSprite.getImage();
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        assert screenPos != null;
        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        if (cutView && mapObjectSprite.isCutable()){
            drawCutImage(mapObjectSprite, image, screenPos, gc);
        } else if (mapObjectSprite.getObject().getType().equals(MapObjectType.ITEM))
            ItemObjectsDrawer.drawItemObject(mapObjectSprite, image, screenPos);
        else
            gc.drawImage(image,
                    screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
    }

    private void drawCutImage(MapObjectSprite mapObjectSprite, Image image, Point screenPos, GraphicsContext gc) {
        int sourceX = (int) (image.getWidth() * .4);
        int sourceY = (int)(mapObjectSprite.getOffset().y - (image.getHeight() - mapObjectSprite.getOffset().y)/2);
        int sourceWidth = (int) (image.getWidth() * .2);
        int sourceHeight = (int)(image.getHeight() - mapObjectSprite.getOffset().y);

        gc.drawImage(image, sourceX, sourceY, sourceWidth, sourceHeight,
                screenPos.x - mapObjectSprite.getOffset().x + sourceX, screenPos.y - mapObjectSprite.getOffset().y + sourceY,
                sourceWidth, sourceHeight);
    }

    public static MapObject clickedObject(Point clickPoint) {
        MapObject itemObject = ItemObjectsDrawer.clickedItem(clickPoint);
        if (itemObject != null)
            return itemObject;

        if (cutView)
            return null;

        for (Point point: point2mapObjectSpriteMap.keySet()) {
            MapObjectSprite sprite =  point2mapObjectSpriteMap.get(point);

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
        assert spriteScreenPos != null;
        int width = (int) sprite.getImage().getWidth();
        int height = (int) sprite.getImage().getHeight();
        return new Rectangle(spriteScreenPos.x - width/2, (int) (spriteScreenPos.y - height*MapObjectSprite.Y_BASE_RATIO), width, height);
    }

    public static void tooltipCanvas(String text){
        Canvas canvas = IsoViewer.getCanvas();
        if (text.equals("") && canvasTooltip != null) {
            Tooltip.uninstall(canvas, canvasTooltip);
            canvasTooltip = null;
        } else if (!text.equals("") && canvasTooltip == null){
            canvasTooltip = new Tooltip(text);
            Tooltip.install(canvas, canvasTooltip);
        }
    }

    public static void removeObject(MapObject mapObject) {
        Point point = mapObject2PointMap.get(mapObject);
        point2mapObjectSpriteMap.remove(point);
        mapObject2PointMap.remove(mapObject);
        MapPiece mapPiece = Battle.getMap().getPoints().get(point);
        mapPiece.setObject(null);
    }

    public static java.util.Map<MapObject, Point> getMapObject2PointMap() {
        return mapObject2PointMap;
    }

    public static Map<Point, MapObjectSprite> getPoint2mapObjectSpriteMap() {
        return point2mapObjectSpriteMap;
    }
}
