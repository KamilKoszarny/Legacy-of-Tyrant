package viewIso;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.map.Map;

import java.awt.*;

public class ClickMenusDrawer {

    private static final int CHAR2POINTMENU_RAD = 20;
    private static final int CHAR2POINTMENU_COUNT = 5;
    private static final Color CHAR2POINTMENU_COLOR = Color.BEIGE;

    private Map map;
    private Canvas canvas;
    private GraphicsContext gc;
    private MapDrawer mapDrawer;
    private MapPieceDrawer mPDrawer;

    ClickMenusDrawer(MapDrawer mapDrawer) {
        this.mapDrawer = mapDrawer;
        map = mapDrawer.getMap();
        canvas = mapDrawer.getCanvas();
        gc = canvas.getGraphicsContext2D();
        mPDrawer = mapDrawer.getmPDrawer();
    }

    public void drawChar2PointMenu(Point clickPoint) {
        ClickMenuButton menuButtonLook = new ClickMenuButton("Look", 4, 0);
        ClickMenuButton menuButtonWalk = new ClickMenuButton("Walk", 4, 1);
        ClickMenuButton menuButtonRun = new ClickMenuButton("Run", 4, 2);
        ClickMenuButton menuButtonSneak = new ClickMenuButton("Sneak", 4, 3);

        drawButton(menuButtonLook, clickPoint);
        drawButton(menuButtonWalk, clickPoint);
        drawButton(menuButtonRun, clickPoint);
        drawButton(menuButtonSneak, clickPoint);
    }

    private void drawButton(ClickMenuButton clickMenuButton, Point clickPoint) {
        Shape shape = clickMenuButton.getShape();
        shape.setTranslateX(clickPoint.x - canvas.getWidth()/2);
        shape.setTranslateY(clickPoint.y - canvas.getHeight()/2);
        Pane pane = (Pane) canvas.getParent();
        pane.getChildren().add(shape);
    }
}
