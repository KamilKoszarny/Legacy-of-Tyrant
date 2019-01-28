package helpers.my;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import viewIso.map.MapDrawCalculator;

import java.awt.*;

public class MouseHelper {
    public static Boolean mouseOnImage(Point mousePoint, Point mapPos, Image image, Point offset) {
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(mapPos);
        assert screenPos != null;

        Point screenPosUpLeft = new Point((int)(screenPos.x - offset.getX()), (int)(screenPos.y - offset.getY()));
        Point onImagePos = new Point(mousePoint.x - screenPosUpLeft.x, mousePoint.y - screenPosUpLeft.y);

        if (onImagePos.x > 0 && onImagePos.x < image.getWidth() && onImagePos.y > 0 && onImagePos.y < image.getHeight()) {
            PixelReader pixelReader = image.getPixelReader();
            int argb = pixelReader.getArgb(onImagePos.x, onImagePos.y);
            int alpha = argb & 0x00ffffff;
            return alpha > 0;
        }
        return null;
    }
}
