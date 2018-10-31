package viewIso.mapObjects;

import javafx.scene.image.Image;
import model.map.mapObjects.MapObject;

import java.awt.*;

public class MapObjectSprite {

    private Image image;
    private static final int SIZE_Y_START = 200;
    private static final int SIZE_Y_STEP = 100;
    private static final double Y_BASE_RATIO = 5./6.;
    private Point offset = new Point();

    public MapObjectSprite(MapObject mapObject) {
        StringBuilder path = new StringBuilder("/sprites");
        path.append("/" + mapObject.getType());
        path.append("/size" + mapObject.getSize());
        path.append("/" + mapObject.getLook() + ".png");
        image = new Image(path.toString());
        offset.x = (int) (image.getWidth() / 2);
        offset.y = (int) (SIZE_Y_START + SIZE_Y_STEP * mapObject.getSize() * Y_BASE_RATIO);
    }

    public Image getImage() {
        return image;
    }

    public Point getOffset() {
        return offset;
    }
}

