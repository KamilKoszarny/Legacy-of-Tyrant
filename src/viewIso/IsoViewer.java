package viewIso;

import javafx.scene.canvas.Canvas;
import model.Battle;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;

import java.awt.*;

public class IsoViewer {

    private static MapDrawer mapDrawer;
    private static ClickMenusDrawer clickMenusDrawer;
    private static SpritesDrawer spritesDrawer;
    private static boolean cutView = false;
    private static Point mapMove = new Point(0, 0);
    private static Canvas canvas;

    public IsoViewer(Canvas canvas) {
        IsoViewer.canvas = canvas;
        mapDrawer = new MapDrawer(Battle.getMap(), canvas);
        spritesDrawer = new SpritesDrawer();
        clickMenusDrawer = new ClickMenusDrawer();
    }

    public static void draw() {
        if (!mapMove.equals(new Point(0, 0)))
            moveMap();
        mapDrawer.drawMap();
        spritesDrawer.drawVisibleSprites(cutView);
    }

    private static void moveMap() {
        MapDrawer.changeZeroScreenPosition(mapMove);
        if (MapDrawCalculator.mapOnScreen()) {
            mapDrawer.clearMapBounds();
            mapDrawer.drawMap();
            spritesDrawer.drawVisibleSprites(cutView);
            clickMenusDrawer.moveMenus(mapMove);
        } else {
            MapDrawer.changeZeroScreenPosition(new Point(-mapMove.x, -mapMove.y));
        }
    }

    public static void setMapMove(Point mapMove) {
        if (IsoViewer.mapMove.x != 0 && mapMove.y != 0)
            IsoViewer.mapMove.y = mapMove.y;
        else if (IsoViewer.mapMove.y != 0 && mapMove.x != 0)
            IsoViewer.mapMove.x = mapMove.x;
        else
            IsoViewer.mapMove = mapMove;
    }

    public static void switchCutView() {
        cutView = !cutView;
    }

    public static Canvas getCanvas() {
        return canvas;
    }
}
