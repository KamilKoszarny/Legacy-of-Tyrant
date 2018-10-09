package viewIso;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import model.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickMenusDrawer {

    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapDrawer mapDrawer;
    private MapPieceDrawer mPDrawer;
    List<ClickMenuButton> char2PointMenu;

    ClickMenusDrawer(MapDrawer mapDrawer) {
        this.mapDrawer = mapDrawer;
        map = mapDrawer.getMap();
        canvas = mapDrawer.getCanvas();
        gc = canvas.getGraphicsContext2D();
        mPDrawer = mapDrawer.getmPDrawer();

        initChar2PointMenu();
    }

    public void initChar2PointMenu() {
        char2PointMenu = new ArrayList<>();
        char2PointMenu.add(new ClickMenuButton("Look", "look"));
        char2PointMenu.add(new ClickMenuButton("Walk", "walk"));
        char2PointMenu.add(new ClickMenuButton("Run", "run"));
        char2PointMenu.add(new ClickMenuButton("Sneak", "sneak"));

        ClickMenuButton.groupButtons(char2PointMenu);
        ClickMenuButton.shapeButtons(char2PointMenu);
        for (ClickMenuButton button: char2PointMenu) {
            Shape shape = button.getShape();
            shape.setVisible(false);
            Label label = button.getLabel();
            label.setVisible(false);
            Pane pane = (Pane) canvas.getParent();
            pane.getChildren().addAll(shape, label);
        }
    }

    public void drawChar2PointMenu(Point clickPoint) {
        for (ClickMenuButton button: char2PointMenu) {
            drawButton(button, clickPoint);
        }
    }

    private void drawButton(ClickMenuButton button, Point clickPoint) {
        Shape shape = button.getShape();
        shape.setTranslateX(clickPoint.x - canvas.getWidth()/2 + ClickMenuButton.RADIUS_OUT/2);
        shape.setTranslateY(clickPoint.y - canvas.getHeight()/2 + ClickMenuButton.RADIUS_OUT/2);
        shape.setVisible(true);

        Label label = button.getLabel();
        label.setTranslateX(button.getLabelVertex().x + clickPoint.x - canvas.getWidth()/2);
        label.setTranslateY(button.getLabelVertex().y + clickPoint.y - canvas.getHeight()/2);
        label.toFront();

        System.out.println(label.getTranslateX());
        label.setVisible(true);
    }
}
