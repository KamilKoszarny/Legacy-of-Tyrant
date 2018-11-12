package viewIso.mapObjects;

import javafx.scene.image.Image;
import model.map.mapObjects.MapObject;

import java.awt.*;

public class MapObjectSprite {

    private Image image;
    public static final double Y_BASE_RATIO = .85;
    private Point offset = new Point();
    private MapObject object;

    public MapObjectSprite(MapObject mapObject) {
        object = mapObject;
        StringBuilder path = new StringBuilder("/sprites");
        path.append("/" + mapObject.getType());
        int size = mapObject.getSize();
        if (size > 0)
            path.append("/size" + size);
        path.append("/" + mapObject.getLook() + ".png");
        System.out.println(path);
        image = new Image(path.toString());
        offset.x = (int) (image.getWidth() / 2);
        offset.y = (int) (image.getHeight() * Y_BASE_RATIO);
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
}

