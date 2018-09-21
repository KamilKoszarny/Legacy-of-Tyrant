package model;

import javafx.animation.AnimationTimer;
import viewIso.IsoViewer;
import viewIso.MapDrawer;

import java.awt.*;

public class IsoBattleLoop extends AnimationTimer{

    private boolean mapMoveFlag = false;
    private Point mapMove = new Point(0, 0);
    private IsoViewer isoViewer;
    private MapDrawer mapDrawer;


    @Override
    public void handle(long curNanoTime) {
        if (mapMoveFlag) {
            System.out.println("Move: " + mapMove);
            mapDrawer.changeZeroScreenPosition(mapMove);
            if (mapDrawer.mapOnScreen()) {
                mapDrawer.clearMapBounds();
                mapDrawer.drawMap();
            } else
                mapDrawer.changeZeroScreenPosition(new Point(-mapMove.x, -mapMove.y));
        }
    }

    public void setMapMoveFlag(boolean mapMoveFlag) {
        this.mapMoveFlag = mapMoveFlag;
    }

    public void changeMapMove(Point mapMoveChange) {
        mapMove.x = mapMove.x + mapMoveChange.x;
        mapMove.y = mapMove.y + mapMoveChange.y;
    }

    public void resetMapMove(Point mapMove) {
        this.mapMove = mapMove;
    }

    public void setIsoViewer(IsoViewer isoViewer) {
        this.isoViewer = isoViewer;
        mapDrawer = isoViewer.getMapDrawer();
    }
}
