package isoview.map.objects;

import helpers.my.MouseHelper;
import isoview.IsoViewer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import model.Battle;
import model.map.MapPiece;
import model.map.mapObjects.ItemMapObject;
import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

import java.awt.*;

import static javafx.scene.AccessibleRole.TOOLTIP;

public class MapObjectController {

    private static Tooltip canvasTooltip = null;

    public static MapObject clickedObject(Point clickPoint) {
        for (MapObject itemObject : MapObjectDrawer.getMapObject2PointMap().keySet()) {
            if (checkPointOnMapObject(clickPoint, itemObject)) {
                return itemObject;
            }
        }
        return null;
    }

    public static MapObject checkHoverObject(Point hoverPoint) {
        MapObjectDrawer.setHoverObject(null);
        for (MapObject mapObject : MapObjectDrawer.getMapObject2PointMap().keySet()) {
            if (checkPointOnMapObject(hoverPoint, mapObject)) {
                MapObjectDrawer.setHoverObject(mapObject);
                tooltipCanvas(mapObject.getName());
            }
        }
        if (MapObjectDrawer.getHoverObject() == null)
            tooltipCanvas("");
        return MapObjectDrawer.getHoverObject();
    }

    private static boolean checkPointOnMapObject(Point mousePoint, MapObject mapObject) {
        if (!mapObject.getType().isClickable())
            return false;
        if (mapObject.getType().equals(MapObjectType.FURNITURE) && !mapObject.getFurnitureType().isClickable())
            return false;
        if (MapObjectDrawer.isCutView() && !(mapObject instanceof ItemMapObject || mapObject.getType().equals(MapObjectType.FURNITURE)))
            return false;

        Point mapPos = MapObjectDrawer.getMapObject2PointMap().get(mapObject);
        MapObjectSprite mapObjectSprite = MapObjectDrawer.getPoint2mapObjectSpriteMap().get(mapPos);
        Image image = mapObjectSprite.getImage();

        Boolean alpha = MouseHelper.mouseOnImage(mousePoint, mapPos, image, mapObjectSprite.getOffset());
        if (alpha != null) return alpha;
        return false;
    }

    public static void removeObject(MapObject mapObject) {
        Point point = MapObjectDrawer.getMapObject2PointMap().get(mapObject);
        MapObjectDrawer.getPoint2mapObjectSpriteMap().remove(point);
        MapObjectDrawer.getMapObject2PointMap().remove(mapObject);
        MapPiece mapPiece = Battle.getMap().getPoints().get(point);
        mapPiece.setObject(null);
    }

    private static void tooltipCanvas(String text) {
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
}
