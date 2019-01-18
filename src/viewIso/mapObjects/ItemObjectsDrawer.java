package viewIso.mapObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import viewIso.IsoViewer;

import java.awt.*;

public class ItemObjectsDrawer {

    private static final double ITEM_GLOW_RATIO = .1;

    private static double itemGlow = 0;
    private static double itemGlowIncrement = ITEM_GLOW_RATIO;
    private static boolean itemGlowRaise = true;

    public static void drawItemObject(MapObjectSprite mapObjectSprite, Image image, Point screenPos) {
        GraphicsContext gc = IsoViewer.getCanvas().getGraphicsContext2D();
        glowItem(mapObjectSprite, gc);
        gc.drawImage(image,
                screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
        gc.setEffect(null);
    }

    private static void glowItem(MapObjectSprite itemMapObjectSprite, GraphicsContext gc) {
        if (itemGlowIncrement != 0) {
            itemGlow += itemGlowIncrement;
            itemGlowIncrement = 0;
        }
        if (itemMapObjectSprite.getObject().equals(MapObjectDrawer.getHoverObject()))
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
}
