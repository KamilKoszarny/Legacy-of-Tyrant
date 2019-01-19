package viewIso.mapObjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import model.Battle;
import model.map.MapPiece;
import model.map.mapObjects.ItemMapObject;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;
import viewIso.IsoViewer;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.util.HashMap;

import static javafx.scene.AccessibleRole.TOOLTIP;

public class MapObjectDrawer {

    private static java.util.Map<Point, MapObjectSprite> point2mapObjectSpriteMap = new HashMap<>();
    private static java.util.Map<MapObject, Point> mapObject2PointMap = new HashMap<>();
    private static boolean cutView = false;
    private static Tooltip canvasTooltip = null;
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

    public void drawObject (Point point) {
        MapObjectSprite mapObjectSprite = point2mapObjectSpriteMap.get(point);
        Image image = mapObjectSprite.getImage();
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        assert screenPos != null;
        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        if (cutView && mapObjectSprite.getObject().getType().isCutable()){
            drawCutImage(mapObjectSprite, image, screenPos, gc);
        } else {
            glow(mapObjectSprite, gc);
            if (mapObjectSprite.getObject().getType().equals(MapObjectType.ITEM))
                ItemObjectsDrawer.drawItemObject(mapObjectSprite, image, screenPos);
            else {
                gc.drawImage(image,
                    screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
            }
            gc.setEffect(null);
        }
    }

    private void drawCutImage(MapObjectSprite mapObjectSprite, Image image, Point screenPos, GraphicsContext gc) {
        if (mapObjectSprite.getObject().getType().hasCutImage())
            gc.drawImage(mapObjectSprite.getCutImage(),
                    screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
        else {
            int sourceX = (int) (image.getWidth() * .4);
            int sourceY = (int) (mapObjectSprite.getOffset().y - (image.getHeight() - mapObjectSprite.getOffset().y) / 2);
            int sourceWidth = (int) (image.getWidth() * .2);
            int sourceHeight = (int) (image.getHeight() - mapObjectSprite.getOffset().y);

            glow(mapObjectSprite, gc);
            gc.drawImage(image, sourceX, sourceY, sourceWidth, sourceHeight,
                    screenPos.x - mapObjectSprite.getOffset().x + sourceX, screenPos.y - mapObjectSprite.getOffset().y + sourceY,
                    sourceWidth, sourceHeight);
            gc.setEffect(null);
        }
    }


    private static void glow(MapObjectSprite mapObjectSprite, GraphicsContext gc) {
        if (hoverObject != null && mapObjectSprite.getObject().equals(hoverObject)) {
            gc.setEffect(new Glow(1));
        }
    }

    public static MapObject clickedObject(Point clickPoint) {
        for (MapObject itemObject: mapObject2PointMap.keySet()) {
            if(checkPointOnMapObject(clickPoint, itemObject)) {
                return itemObject;
            }
        }
        return null;
    }

    public static void checkHoverObject(Point hoverPoint){
        hoverObject = null;
        for (MapObject mapObject : mapObject2PointMap.keySet()) {
            if(checkPointOnMapObject(hoverPoint, mapObject)) {
                hoverObject = mapObject;
                tooltipCanvas(mapObject.getName());
            }
        }
        if (hoverObject == null)
            tooltipCanvas("");
    }

    private static boolean checkPointOnMapObject(Point mousePoint, MapObject mapObject) {
        if (!mapObject.getType().isClickable())
            return false;
        if (cutView && !(mapObject instanceof ItemMapObject))
            return false;

        Point mapPos = mapObject2PointMap.get(mapObject);
        MapObjectSprite mapObjectSprite = point2mapObjectSpriteMap.get(mapPos);

        Point screenPos = MapDrawCalculator.screenPositionWithHeight(mapPos);
        assert screenPos != null;

        Image image = mapObjectSprite.getImage();
        Point screenPosUpLeft = new Point(screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
        Point onImagePos = new Point(mousePoint.x - screenPosUpLeft.x, mousePoint.y - screenPosUpLeft.y);

        if (onImagePos.x > 0 && onImagePos.x < image.getWidth() && onImagePos.y > 0 && onImagePos.y < image.getHeight()) {
            PixelReader pixelReader = image.getPixelReader();
            int argb = pixelReader.getArgb(onImagePos.x, onImagePos.y);
            int alpha = argb & 0x00ffffff;
            return alpha > 0;
        }
        return false;
    }

    public static void removeObject(MapObject mapObject) {
        Point point = mapObject2PointMap.get(mapObject);
        point2mapObjectSpriteMap.remove(point);
        mapObject2PointMap.remove(mapObject);
        MapPiece mapPiece = Battle.getMap().getPoints().get(point);
        mapPiece.setObject(null);
    }

    public static void tooltipCanvas(String text){
        Canvas canvas = IsoViewer.getCanvas();
        if (text.equals("")) {
            if (canvasTooltip != null) {
                Tooltip.uninstall(canvas, canvasTooltip);
                canvasTooltip = null;
                canvas.getProperties().remove(TOOLTIP);
            }
        } else {
            if (canvasTooltip == null) {
                canvasTooltip = new Tooltip(text);
                Tooltip.install(canvas, canvasTooltip);
                canvas.getProperties().put(TOOLTIP, canvasTooltip);
            } else {
                Tooltip currentTooltip = (Tooltip) canvas.getProperties().get(TOOLTIP);
                if (!currentTooltip.getText().equals(text)) {
                    Tooltip.uninstall(canvas, canvasTooltip);
                    canvasTooltip = new Tooltip(text);
                    Tooltip.install(canvas, canvasTooltip);
                    canvas.getProperties().put(TOOLTIP, canvasTooltip);
                }
            }
        }
    }

    public static java.util.Map<MapObject, Point> getMapObject2PointMap() {
        return mapObject2PointMap;
    }

    public static MapObject getHoverObject() {
        return hoverObject;
    }

    public static void switchCutView() {
        cutView = !cutView;
    }
}
