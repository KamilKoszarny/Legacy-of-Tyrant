package viewIso.map;

import helpers.my.ColorHelper;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MapImage {

    private WritableImage image;
    public static final int EXTRA_X = 10, EXTRA_Y = 100;
    private int width, height, xShift, yShift;

    public MapImage(int width, int height, int xShift, int yShift) {
        this.width = width;
        this.height = height;
        this.xShift = xShift;
        this.yShift = yShift;
        image = new WritableImage(width, height);
    }

    public void setPixelColor(int x, int y, Color color) {
        int argb = ColorHelper.colorToInt(color);
    }

    public WritableImage getImage() {
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxShift() {
        return xShift;
    }

    public int getyShift() {
        return yShift;
    }
}
