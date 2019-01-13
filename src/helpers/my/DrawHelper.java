package helpers.my;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;
import viewIso.map.MapDrawer;

public class DrawHelper {

    public static void drawPolygonOnCanvas(Canvas canvas, Polygon polygon, boolean fill, boolean relative) {
        int points = polygon.getPoints().size()/2;
        double[] xs = new double[points];
        double[] ys = new double[points];
        for (int i = 0; i < points; i++) {
            xs[i] = polygon.getPoints().get(i*2);
            ys[i] = polygon.getPoints().get(i*2 + 1);
            if (relative) {
                xs[i] += MapDrawer.getZeroScreenPosition().getX();
                ys[i] += MapDrawer.getZeroScreenPosition().getY();
            }
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(polygon.getFill());
        gc.setStroke(polygon.getStroke());

        if (fill)
            gc.fillPolygon(xs, ys, points);
        else
            gc.strokePolygon(xs, ys, points);
    }
}
