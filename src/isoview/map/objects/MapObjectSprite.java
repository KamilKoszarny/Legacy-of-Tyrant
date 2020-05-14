package isoview.map.objects;

import helpers.my.DrawHelper;
import isoview.characters.CharPose;
import isoview.characters.CharsDrawer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import model.items.ItemImagesLoader;
import model.items.ItemWithSprite;
import model.map.mapObjects.ItemMapObject;
import model.map.mapObjects.MapObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MapObjectSprite {

    private static final int VISIBILITY_PIXELS_LIMIT = 100, VISIBILITY_IMPROVE_TRIES = 5;
    private static int visiblePixels, visiblePixelsMax, bestVisibleDirection;

    private Image image, cutImage;
    private static final double Y_BASE_RATIO = .85;
    private Point offset = new Point();
    private MapObject object;

    MapObjectSprite(MapObject mapObject) {
        object = mapObject;
        findImage(mapObject);
        offset.x = (int) (image.getWidth() / 2);
        offset.y = (int) (image.getHeight() * Y_BASE_RATIO);
    }

    private void findImage(MapObject mapObject) {
        if (mapObject instanceof ItemMapObject && ((ItemMapObject) mapObject).getItem() instanceof ItemWithSprite) {
            makeDroppedItemImage(((ItemMapObject) mapObject).getItemWithSprite());
            return;
        }
        String path = buildPath(mapObject);
        image = new Image(path);
        if (mapObject.getType().hasCutImage()) {
            path = pathToCutImage(path);
            cutImage = new Image(path);
        }
    }

    private void makeDroppedItemImage(ItemWithSprite itemWithSprite) {
        BufferedImage sourceImage = ItemImagesLoader.loadSpriteSheetForItemOnly(itemWithSprite);
        BufferedImage resultImage;
        int spanX = (int) (CharsDrawer.SPRITES_SPAN.getWidth()/CharsDrawer.SCALING);
        int spanY = (int) (CharsDrawer.SPRITES_SPAN.getHeight()/CharsDrawer.SCALING);
        int offX = (int) (CharsDrawer.SPRITE_OFFSET.getWidth()/CharsDrawer.SCALING);
        int offY = (int) (CharsDrawer.SPRITE_OFFSET.getHeight()/CharsDrawer.SCALING);
        int sizeX = (int) (CharsDrawer.SPRITE_SIZE.getWidth()/CharsDrawer.SCALING);
        int sizeY = (int) (CharsDrawer.SPRITE_SIZE.getHeight()/CharsDrawer.SCALING);
        Random r = new Random();
        int direction, i = 0;
        do {
            direction = r.nextInt(8);
            resultImage = sourceImage.getSubimage(
                    spanX * CharPose.DEAD.getStartFrame() + offX, spanY * direction + offY, sizeX, sizeY);
            resultImage = DrawHelper.resizeBufferedImage(resultImage, CharsDrawer.SCALING);
            image = SwingFXUtils.toFXImage(resultImage, null);
            i++;
        }
        while (!checkVisibility(image, direction) && i < VISIBILITY_IMPROVE_TRIES);

        if (i == VISIBILITY_IMPROVE_TRIES) {
            visiblePixels = visiblePixelsMax = 0;
            direction = bestVisibleDirection;
            resultImage = sourceImage.getSubimage(
                    spanX * CharPose.DEAD.getStartFrame() + offX, spanY * direction + offY, sizeX, sizeY);
            resultImage = DrawHelper.resizeBufferedImage(resultImage, CharsDrawer.SCALING);
            image = SwingFXUtils.toFXImage(resultImage, null);
        }
    }

    private boolean checkVisibility(Image image, int direction) {
        PixelReader pixelReader = image.getPixelReader();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = pixelReader.getArgb(x, y);
                int alpha = argb & 0x00ffffff;
                if (alpha > 0)
                    visiblePixels++;
            }
        }
        if (visiblePixels > visiblePixelsMax) {
            visiblePixelsMax = visiblePixels;
            bestVisibleDirection = direction;
        }
        System.out.println(visiblePixels);
        return visiblePixels > VISIBILITY_PIXELS_LIMIT;
    }

    private String buildPath(MapObject mapObject) {
        StringBuilder path = new StringBuilder("");
        path.append("/sprites");
        path.append("/").append(mapObject.getType());
        int size = mapObject.getSize();
        if (size > 0)
            path.append("/size").append(size);
        path.append("/").append(mapObject.getLook()).append(".png");
        return path.toString();
    }

    private String pathToCutImage(String path){
        int indexOfDot = path.lastIndexOf(".");
        String pathToCutImage = path.substring(0, indexOfDot) + "cut.png";
        return pathToCutImage;
    }

    public Image getImage() {
        return image;
    }

    public Image getCutImage() {
        return cutImage;
    }

    public Point getOffset() {
        return offset;
    }

    public MapObject getObject() {
        return object;
    }
}

