package viewIso;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickMenuButton {

    private static final int RADIUS_IN = 5;
    private static final int RADIUS_OUT = 20;
    private static final int SPACING = 2;
    private static final javafx.scene.paint.Color COLOR = Color.BEIGE;

    private String name;
    private int menuButtonsCount;
    private int number;
    private Polygon shape = new Polygon();

    public ClickMenuButton(String name, int menuButtonsCount, int number) {
        this.name = name;
        this.menuButtonsCount = menuButtonsCount;
        this.number = number;

        calcShape();
    }

    private void calcShape() {
        double circumference = RADIUS_IN * 2 * Math.PI;
        Double angle = SPACING / 2 / circumference * 2 * Math.PI;
        shape.getPoints().addAll(Math.tan(angle) * RADIUS_IN, 1/Math.tan(angle) * RADIUS_IN);
        shape.getPoints().addAll(Math.tan(angle) * RADIUS_OUT, 1/Math.tan(angle) * RADIUS_OUT);
        angle = Math.PI * 2 / menuButtonsCount - (SPACING / 2) / circumference * 2 * Math.PI;
        shape.getPoints().addAll(Math.tan(angle) * RADIUS_OUT, 1/Math.tan(angle) * RADIUS_OUT);
        shape.getPoints().addAll(Math.tan(angle) * RADIUS_IN, 1/Math.tan(angle) * RADIUS_IN);

        Rotate rotate = new Rotate(360 / menuButtonsCount * number, 0, 0);
        shape.getTransforms().add(rotate);

        shape.setFill(COLOR);
    }

    public Polygon getShape() {
        return shape;
    }
}
