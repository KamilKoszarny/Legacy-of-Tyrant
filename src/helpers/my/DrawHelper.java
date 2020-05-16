package helpers.my;


import isoview.IsoViewer;
import isoview.map.MapDrawer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Transparency.TRANSLUCENT;

public class DrawHelper {

    private DrawHelper() {
    }

    public static void drawPolygonOnCanvas(Polygon polygon, boolean fill, boolean relative) {
        Canvas canvas = IsoViewer.getCanvas();
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

    public static void drawAPLabel(Point2D point, Integer ap, int offsetX, int offsetY, boolean relative) {
        Canvas canvas = IsoViewer.getCanvas();
        if (relative) {
            point = new Point2D(
                    point.getX() + MapDrawer.getZeroScreenPosition().getX(),
                    point.getY() + MapDrawer.getZeroScreenPosition().getY());
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.5);
        gc.fillText("AP: " + ap.toString(), point.getX() + offsetX, point.getY() + offsetY);
        gc.strokeText("AP: " + ap.toString(), point.getX() + offsetX, point.getY() + offsetY);
    }

    public static BufferedImage resizeBufferedImage(BufferedImage src, double scale){
        int w = (int) (src.getWidth() * scale);
        int h = (int) (src.getHeight() * scale);
        int finalw = w;
        int finalh = h;
        double factor;
        if(src.getWidth() > src.getHeight()){
            factor = ((double)src.getHeight()/(double)src.getWidth());
            finalh = (int)(finalw * factor);
        }else{
            factor = ((double)src.getWidth()/(double)src.getHeight());
            finalw = (int)(finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }
}
