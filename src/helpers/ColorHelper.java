package helpers;

import javafx.scene.paint.Color;

public class ColorHelper {

    public static int colorToInt(Color c) {
        int r = (int) Math.round(c.getRed() * 255);
        int g = (int) Math.round(c.getGreen() * 255);
        int b = (int) Math.round(c.getBlue() * 255);
        return (r << 16) | (g << 8) | b;
    }

    public static java.awt.Color fx2AwtColor(Color colorFX) {
        return new java.awt.Color((float) colorFX.getRed(),
                (float) colorFX.getGreen(),
                (float) colorFX.getBlue(),
                (float) colorFX.getOpacity());
    }
}
