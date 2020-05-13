package helpers.my;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;
import viewIso.map.MapDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    public static BufferedImage resizeBufferedImage(BufferedImage src, double scale){
        int w = (int) (src.getWidth() * scale);
        int h = (int) (src.getHeight() * scale);
        int finalw = w;
        int finalh = h;
        double factor = 1.0d;
        if(src.getWidth() > src.getHeight()){
            factor = ((double)src.getHeight()/(double)src.getWidth());
            finalh = (int)(finalw * factor);
        }else{
            factor = ((double)src.getWidth()/(double)src.getHeight());
            finalw = (int)(finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }
}
