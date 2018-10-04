package viewIso;

import javafx.scene.canvas.Canvas;
import model.character.Character;
import model.map.Map;

import java.awt.*;
import java.util.List;


public class IsoViewer {
    private MapDrawer mapDrawer;
    private CharsDrawer charsDrawer;

    public IsoViewer(Map map, Canvas canvas, List<Character> characters) {
        mapDrawer = new MapDrawer(map, canvas);
        charsDrawer = new CharsDrawer(map, canvas, mapDrawer, characters);
        mapDrawer.drawMap();
        charsDrawer.drawAllChars();
    }

    public void moveMap(Point mapMove) {
        mapDrawer.changeZeroScreenPosition(mapMove);
        if (mapDrawer.mapOnScreen()) {
            mapDrawer.clearMapBounds();
            mapDrawer.drawMap();
        } else
            mapDrawer.changeZeroScreenPosition(new Point(-mapMove.x, -mapMove.y));
    }

    public MapDrawer getMapDrawer() {
        return mapDrawer;
    }
}
