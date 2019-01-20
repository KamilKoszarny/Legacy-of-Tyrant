package viewIso.map;

import helpers.my.ColorHelper;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MapImage {

    private WritableImage image;
    public static final int EXTRA_X = 10, EXTRA_Y = 100;
    private int width, height, xShift, yShift;
    private List<List<Point>> viewEdges, viewAllEdges, exploredEdges;

    public MapImage(int width, int height, int xShift, int yShift) {
        this.width = width;
        this.height = height;
        this.xShift = xShift;
        this.yShift = yShift;
        image = new WritableImage(width, height);

        exploredEdges = Arrays.asList(Arrays.asList(new Point(1, 2), new Point(100, 200), new Point(200, 249)));
        viewEdges = Arrays.asList(Arrays.asList(new Point(10, 20), new Point(30, 40), new Point(40, 60)));
        viewAllEdges = Arrays.asList(Arrays.asList(new Point(4, 30), new Point(32, 45), new Point(20, 75)));
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

    public List<List<Point>> getViewEdges() {
        return viewEdges;
    }

    public List<List<Point>> getViewAllEdges() {
        return viewAllEdges;
    }

    public List<List<Point>> getExploredEdges() {
        return exploredEdges;
    }
}
