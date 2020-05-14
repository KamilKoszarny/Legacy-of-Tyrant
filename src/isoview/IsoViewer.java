package isoview;

import isoview.clickmenus.ClickMenusDrawer;
import isoview.map.MapDrawer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import main.App;
import model.Battle;

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
        App.resetTime(2);
        MapDrawer.drawMap();
        App.showAndResetTime("mapDraw", 2);
        PathDrawer.drawPaths(true);
        App.showAndResetTime("pathsDraw", 2);
        SpritesDrawer.drawVisibleSprites();
        App.showAndResetTime("spritesDraw", 2);
        PathDrawer.drawPaths(false);
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static Pane getPane() {
        return (Pane) canvas.getParent();
    }
}
