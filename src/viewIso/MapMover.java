package viewIso;

import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;

import java.awt.*;

public class MapMover {
    private static Point mapMove = new Point(0, 0);

    static void tryMoveMap() {
        if (!mapMove.equals(new Point(0, 0)))
            MapMover.moveMap();
    }

    private static void moveMap() {
        MapDrawer.changeZeroScreenPosition(mapMove);
        if (MapDrawCalculator.mapOnScreen()) {
            MapDrawer.drawMap();
            SpritesDrawer.drawVisibleSprites();
            ClickMenusDrawer.moveMenus(mapMove);
        } else {
            MapDrawer.changeZeroScreenPosition(new Point(-mapMove.x, -mapMove.y));
        }
    }

    public static void setMapMove(Point mapMove) {
        if (mapMove.x != 0 && mapMove.y != 0)
            MapMover.mapMove.y = mapMove.y;
        else if (MapMover.mapMove.y != 0 && mapMove.x != 0)
            MapMover.mapMove.x = mapMove.x;
        else
            MapMover.mapMove = mapMove;
    }
}