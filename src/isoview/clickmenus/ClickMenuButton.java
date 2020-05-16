package isoview.clickmenus;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import model.actions.attack.AttackCalculator;
import model.actions.attack.BodyPart;

import java.awt.*;
import java.util.List;

public enum ClickMenuButton {

    LOOK("Look", "look"),
    WALK("Walk", "walk"),
    RUN("Run", "run"),
    SNEAK("Sneak", "sneak"),

    DOOR_LOOK("Look", "look"),
    DOOR_OPEN("Open", "doorOpen"),
    DOOR_CLOSE("Close", "doorClose"),

    CHEST_LOOK("Look", "look"),
    CHEST_OPEN("Open", "doorOpen"),
    CHEST_CLOSE("Close", "doorClose"),

    ATTACK_HEAD("Attack Head", "head"),
    ATTACK_BODY("Attack Body", "body"),
    ATTACK_ARMS("Attack Arms", "arm"),
    ATTACK_LEGS("Attack Legs", "leg"),
    ENEMY_LOOK("Look", "look"),
    ;

    private static final int RADIUS_IN = 5;
    private static final int RADIUS_OUT = 36;
    private static final double SPACING = 0.5;
    private static final int ICON_XY = 16;
    private static final javafx.scene.paint.Color COLOR = Color.GOLD;
    private static final javafx.scene.paint.Color COLOR_GRAYED = Color.GRAY;

    private String name;
    private Image icon;
    private Label label = new Label();
    private Point labelVertex;
    private int menuButtonsCount;
    private int number;
    private Polygon shape = new Polygon();
    private boolean clicked = false;
    private boolean grayed = false;
    private boolean hovered = false;

    ClickMenuButton(String name, String iconName) {
        this.name = name;
        icon = new Image("/icons/canvas/" + iconName + ".png");
        initTooltip();
        initMouseEvents();
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

    static void colorButtons(List<ClickMenuButton> clickMenuButtons) {
        for (ClickMenuButton button: clickMenuButtons) {
            if (button.grayed)
                button.getShape().setFill(COLOR_GRAYED);
            else
                button.getShape().setFill(COLOR);
        }
    }

    private void initTooltip() {
        Tooltip tooltip = new Tooltip(name);
        Tooltip.install(shape, tooltip);
        Tooltip.install(label, tooltip);
    }

    private void initMouseEvents() {
        initMouseEvents(shape);
        initMouseEvents(label);
    }

    private void initMouseEvents(Node node) {
        node.setOnMouseClicked(mouseEvent -> {
            if (!grayed)
                clicked = true;
        });
        node.setOnMouseEntered(mouseEvent -> {
            hovered = true;
            shape.setStrokeWidth(3.0);
            AttackCalculator.setCurrentCharAttackAPCost();
        });
        node.setOnMouseExited(mouseEvent -> {
            hovered = false;
            shape.setStrokeWidth(0.0);
            AttackCalculator.clearCurrentCharAttackAPCost();
        });
    }

    private void calcShape() {
        double circumference = RADIUS_IN * 2 * Math.PI;
        double angle = SPACING / 2 / circumference * 2 * Math.PI;
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_IN, Math.cos(angle) * RADIUS_IN);
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_OUT, Math.cos(angle) * RADIUS_OUT);
        angle = Math.PI * 2 / menuButtonsCount - (SPACING / 2) / circumference * 2 * Math.PI;
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_OUT, Math.cos(angle) * RADIUS_OUT);
        shape.getPoints().addAll(Math.sin(angle) * RADIUS_IN, Math.cos(angle) * RADIUS_IN);

        Rotate rotate = new Rotate(-360. / menuButtonsCount * number + 180. / menuButtonsCount, 0, 0);
        shape.getTransforms().add(rotate);
        shape.setStroke(Color.BLACK);
    }

    private void calcLabel() {
        label.setGraphic(new ImageView(icon));
        double angle = Math.PI * 2 / menuButtonsCount * number;
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

    public boolean isHovered() {
        return hovered;
    }

    void setGrayed(boolean grayed) {
        this.grayed = grayed;
    }

    void setTooltipText(String text) {
        Tooltip tooltip = new Tooltip(text);
        Tooltip.install(shape, tooltip);
        Tooltip.install(label, tooltip);
    }

    public static ClickMenuButton byBodyPart(BodyPart bodyPart) {
        switch (bodyPart) {
            case HEAD: return ATTACK_HEAD;
            case BODY: return ATTACK_BODY;
            case ARMS: return ATTACK_ARMS;
            case LEGS: return ATTACK_LEGS;
        }
        return ATTACK_BODY;
    }

    public static BodyPart getBodyPart(ClickMenuButton button) {
        for (BodyPart bodyPart: BodyPart.values()) {
            if (byBodyPart(bodyPart).equals(button))
                return bodyPart;
        }
        return null;
    }

    public boolean isAttackButton() {
        return this.equals(ClickMenuButton.ATTACK_HEAD)
                || this.equals(ClickMenuButton.ATTACK_BODY)
                || this.equals(ClickMenuButton.ATTACK_ARMS)
                || this.equals(ClickMenuButton.ATTACK_LEGS);
    }
}
