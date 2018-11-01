package viewIso.mapObjects;

import javafx.scene.image.Image;
import model.map.mapObjects.MapObject;

import java.awt.*;

public class MapObjectSprite {

    private Image image;
    public static final double Y_BASE_RATIO = 5./6.;
    private Point offset = new Point();

    public MapObjectSprite(MapObject mapObject) {
        StringBuilder path = new StringBuilder("/sprites");
        path.append("/" + mapObject.getType());
        path.append("/size" + mapObject.getSize());
        path.append("/" + mapObject.getLook() + ".png");
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
}

