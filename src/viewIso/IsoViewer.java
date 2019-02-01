package viewIso;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import model.Battle;
import viewIso.map.MapDrawer;

import java.awt.*;

public class IsoViewer {

    private static Canvas canvas;

    public IsoViewer(Canvas canvas) {
        IsoViewer.canvas = canvas;
        new MapDrawer(Battle.getMap(), canvas);
        new SpritesDrawer();
        new ClickMenusDrawer();
    }

    public static void draw() {
        MapMover.tryMoveMap();
        MapDrawer.drawMap();
        PathDrawer.drawPaths(true);
        SpritesDrawer.drawVisibleSprites();
        PathDrawer.drawPaths(false);
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static Pane getPane() {
        return (Pane) canvas.getParent();
    }
}
