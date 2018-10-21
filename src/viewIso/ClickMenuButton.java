package viewIso;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.List;

public enum ClickMenuButton {

    LOOK("Look", "look"),
    WALK("Walk", "walk"),
    RUN("Run", "run"),
    SNEAK("Sneak", "sneak");

    private static final int RADIUS_IN = 5;
    private static final int RADIUS_OUT = 36;
    private static final double SPACING = 0.5;
    private static final int ICON_XY = 16;
    private static final javafx.scene.paint.Color COLOR = Color.GOLD;

    private String name;
    private Image icon;
    private Label label = new Label();
    private Point labelVertex;
    private int menuButtonsCount;
    private int number;
    private Polygon shape = new Polygon();
    private boolean clicked = false;

    ClickMenuButton(String name, String iconName) {
        this.name = name;
        icon = new Image("/icons/canvas/" + iconName + ".png");
        initTooltip();
        initClick();
    }

    static void groupButtons(List<ClickMenuButton> clickMenuButtons) {
        for (ClickMenuButton button: clickMenuButtons) {
            button.menuButtonsCount = clickMenuButtons.size();
            button.number = clickMenuButtons.indexOf(button) + 1;
        }
    }

    static void shapeButtons(List<ClickMenuButton> clickMenuButtons) {
        for (ClickMenuButton button: clickMenuButtons) {
            button.calcShape();
            button.calcLabel();
        }
    }

    private void initTooltip() {
        Tooltip tooltip = new Tooltip(name);
        Tooltip.install(shape, tooltip);
        Tooltip.install(label, tooltip);
    }

    private void initClick() {
        initClickForNode(shape);
        initClickForNode(label);
    }

    private void initClickForNode(Node node) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clicked = true;
            }
        });
    }

    private void calcShape() {
        double circumference = RADIUS_IN * 2 * Math.PI;
        Double angle = SPACING / 2 / circumference * 2 * Math.PI;
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_IN, Math.cos(angle) * RADIUS_IN);
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_OUT, Math.cos(angle) * RADIUS_OUT);
        angle = Math.PI * 2 / menuButtonsCount - (SPACING / 2) / circumference * 2 * Math.PI;
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_OUT, Math.cos(angle) * RADIUS_OUT);
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_IN, Math.cos(angle) * RADIUS_IN);

        Rotate rotate = new Rotate(-360 / menuButtonsCount * number + 180 / menuButtonsCount, 0, 0);
        shape.getTransforms().add(rotate);

        shape.setFill(COLOR);
    }

    private void calcLabel() {
        label.setGraphic(new ImageView(icon));
        Double angle = Math.PI * 2 / menuButtonsCount * number;
        labelVertex = new Point((int)((Math.sin(angle)) * (RADIUS_IN + RADIUS_OUT - 2)/2),
                (int)((Math.cos(angle)) * (RADIUS_IN + RADIUS_OUT - 2)/2));
        labelVertex.x -= ICON_XY/2;
        labelVertex.y -= ICON_XY/2;
    }

    public Polygon getShape() {
        return shape;
    }

    public Label getLabel() {
        return label;
    }

    public Point getLabelVertex() {
        return labelVertex;
    }

    public boolean wasClicked() {
        boolean wasClicked = clicked;
        clicked = false;
        return wasClicked;
    }
}