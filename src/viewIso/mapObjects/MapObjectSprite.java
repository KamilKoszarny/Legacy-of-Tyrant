package viewIso.mapObjects;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.items.ItemWithSprite;
import model.items.ItemsLoader;
import model.map.mapObjects.ItemMapObject;
import model.map.mapObjects.MapObject;
import viewIso.characters.CharPose;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MapObjectSprite {

    private Image image;
    public static final double Y_BASE_RATIO = .85;
    private Point offset = new Point();
    private MapObject object;
    private boolean cutable;

    public MapObjectSprite(MapObject mapObject, boolean cutable) {
        object = mapObject;
        this.cutable = cutable;
        findImage(mapObject);
        offset.x = (int) (image.getWidth() / 2);
        offset.y = (int) (image.getHeight() * Y_BASE_RATIO);
    }

    private void findImage(MapObject mapObject) {
        if (mapObject instanceof ItemMapObject && ((ItemMapObject) mapObject).getItem() instanceof ItemWithSprite) {
            cutItemImage(((ItemMapObject) mapObject).getItemWithSprite());
            return;
        }
        String path = buildPath(mapObject);
        image = new Image(path);
    }

    private void cutItemImage(ItemWithSprite itemWithSprite) {
        BufferedImage bufferedImage = ItemsLoader.loadSpriteSheetForItemOnly(itemWithSprite);
        int spanX = (int) (CharsDrawer.SPRITES_SPAN.getWidth()/CharsDrawer.SCALING);
        int spanY = (int) (CharsDrawer.SPRITES_SPAN.getHeight()/CharsDrawer.SCALING);
        int offX = (int) (CharsDrawer.SPRITE_OFFSET.getWidth()/CharsDrawer.SCALING);
        int offY = (int) (CharsDrawer.SPRITE_OFFSET.getHeight()/CharsDrawer.SCALING);
        int sizeX = (int) (CharsDrawer.SPRITE_SIZE.getWidth()/CharsDrawer.SCALING);
        int sizeY = (int) (CharsDrawer.SPRITE_SIZE.getHeight()/CharsDrawer.SCALING);
        Random r = new Random();
        int direction = r.nextInt(8);
        bufferedImage = bufferedImage.getSubimage(
                spanX * CharPose.DEAD.getStartFrame() + offX, spanY * direction + offY, sizeX, sizeY);
        image = SwingFXUtils.toFXImage(bufferedImage, null);
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

    public Image getImage() {
        return image;
    }

    public Point getOffset() {
        return offset;
    }

    public MapObject getObject() {
        return object;
    }

    public boolean isCutable() {
        return cutable;
    }
}

