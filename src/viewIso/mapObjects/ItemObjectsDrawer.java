package viewIso.mapObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import model.Battle;
import model.character.Character;
import model.map.mapObjects.ItemMapObject;
import model.map.mapObjects.MapObject;
import viewIso.IsoViewer;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;

import java.awt.*;

public class ItemObjectsDrawer {

    private static final double ITEM_GLOW_RATIO = .1;

    private static double itemGlow = 0;
    private static double itemGlowIncrement = ITEM_GLOW_RATIO;
    private static boolean itemGlowRaise = true;
    private static MapObject hoverItemObject = null;

    public static void drawItemObject(MapObjectSprite mapObjectSprite, Image image, Point screenPos) {
        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        glow(mapObjectSprite, gc);
        gc.drawImage(image,
                screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
        gc.setEffect(null);
    }

    private static void glow(MapObjectSprite mapObjectSprite, GraphicsContext gc) {
        if (itemGlowIncrement != 0) {
            itemGlow += itemGlowIncrement;
            itemGlowIncrement = 0;
        }
        if (mapObjectSprite.getObject().equals(hoverItemObject))
            gc.setEffect(new Glow(1));
        else
            gc.setEffect(new Glow(itemGlow));
    }

    public static void resetItemGlowIncrement() {
        if (itemGlow >= .6)
            itemGlowRaise = false;
        if (itemGlow <= 0)
            itemGlowRaise = true;
        if (itemGlowRaise)
            ItemObjectsDrawer.itemGlowIncrement = ITEM_GLOW_RATIO;
        else
            ItemObjectsDrawer.itemGlowIncrement = ITEM_GLOW_RATIO * -1;
    }

    public static void checkHoverItem(Point hoverPoint){
        hoverItemObject = null;
        for (MapObject itemObject: MapObjectDrawer.getMapObjectPointMap().keySet()) {
            if(checkPointOnItem(hoverPoint, itemObject)) {
                hoverItemObject = itemObject;
                MapObjectDrawer.tooltipCanvas(itemObject.getName());
            }
        }
        if (hoverItemObject == null)
            MapObjectDrawer.tooltipCanvas("");
    }

    private static boolean checkPointOnItem(Point hoverPoint, MapObject itemObject) {
        Point mapPos = MapObjectDrawer.getMapObjectPointMap().get(itemObject);
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(mapPos);
        assert screenPos != null;

        MapObjectSprite mapObjectSprite = MapObjectDrawer.getMapObjectSpriteMap().get(mapPos);
        Image image = mapObjectSprite.getImage();
        Point screenPosUpLeft = new Point(screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
        Point onImagePos = new Point(hoverPoint.x - screenPosUpLeft.x, hoverPoint.y - screenPosUpLeft.y);

        if (onImagePos.x > 0 && onImagePos.x < image.getWidth() && onImagePos.y > 0 && onImagePos.y < image.getHeight()) {
            PixelReader pixelReader = image.getPixelReader();
            int argb = pixelReader.getArgb(onImagePos.x, onImagePos.y);
            int alpha = argb & 0x00ffffff;
            return alpha > 0;
        }
        return false;
    }

    private static Rectangle calcClickBox(MapObject itemObject) {
        Point mapPos = MapObjectDrawer.getMapObjectPointMap().get(itemObject);
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(mapPos);
        assert screenPos != null;
        return new Rectangle(screenPos.x - CharsDrawer.SPRITE_BASE.width/2, screenPos.y - CharsDrawer.SPRITE_BASE.height*2/3,
                CharsDrawer.SPRITE_SIZE.width/2, CharsDrawer.SPRITE_SIZE.height*2/3);
    }
}
