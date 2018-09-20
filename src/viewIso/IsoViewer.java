package viewIso;

import javafx.scene.canvas.Canvas;
import model.map.Map;


public class IsoViewer {
    private MapDrawer mapDrawer;

    public IsoViewer(Map map, Canvas canvas) {
        mapDrawer = new MapDrawer(map, canvas);
        mapDrawer.drawMap();
    }

    public MapDrawer getMapDrawer() {
        return mapDrawer;
    }
}
