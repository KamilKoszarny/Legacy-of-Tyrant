package viewIso.map;

import helpers.my.ColorHelper;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Arrays;
import java.util.List;

public class MapImage {

    private WritableImage image;
    public static final int EXTRA_X = 10, EXTRA_Y = 100;
    private int width, height, xShift, yShift;
    private List<Polygon> exploredEdges;

    public MapImage(int width, int height, int xShift, int yShift) {
        this.width = width;
        this.height = height;
        this.xShift = xShift;
        this.yShift = yShift;
        image = new WritableImage(width, height);

        exploredEdges = Arrays.asList(new Polygon(new double[]{1,1,30,1,1,60}),
                new Polygon(new double[]{10,20,30,40,50,60}),
                new Polygon(new double[]{19,29,39,49,59,69}));
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

    public List<Polygon> getExploredEdges() {
        return exploredEdges;
    }
}
