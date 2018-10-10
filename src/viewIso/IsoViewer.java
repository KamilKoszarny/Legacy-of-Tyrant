package viewIso;

import javafx.scene.canvas.Canvas;
import model.character.Character;
import model.map.Map;

import java.awt.*;
import java.util.List;


public class IsoViewer {
    private MapDrawer mapDrawer;
    private CharsDrawer charsDrawer;
    private ClickMenusDrawer clickMenusDrawer;
    private int timeStepCount = 0;

    public IsoViewer(Map map, Canvas canvas, List<Character> characters) {
        mapDrawer = new MapDrawer(map, canvas);
        charsDrawer = new CharsDrawer(map, canvas, mapDrawer, characters);
        clickMenusDrawer = new ClickMenusDrawer(mapDrawer);
        mapDrawer.drawMap();
        charsDrawer.drawAllChars();
    }

    public void moveMap(Point mapMove) {
        mapDrawer.changeZeroScreenPosition(mapMove);
        if (mapDrawer.mapOnScreen()) {
            mapDrawer.clearMapBounds();
            mapDrawer.drawMap();
            charsDrawer.drawVisibleChars();
            clickMenusDrawer.moveMenus(mapMove);
        } else
            mapDrawer.changeZeroScreenPosition(new Point(-mapMove.x, -mapMove.y));
    }

    public void animate() {
        if (timeStepCount%5 == 0)
            charsDrawer.drawVisibleChars();

        timeStepCount++;
    }

    public MapDrawer getMapDrawer() {
        return mapDrawer;
    }

    public CharsDrawer getCharsDrawer() {
        return charsDrawer;
    }

    public ClickMenusDrawer getClickMenusDrawer() {
        return clickMenusDrawer;
    }
}
