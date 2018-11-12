package viewIso;

import javafx.scene.canvas.Canvas;
import model.character.Character;
import model.map.Map;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.List;


public class IsoViewer {
    private MapDrawer mapDrawer;
    private CharsDrawer charsDrawer;
    private MapObjectDrawer mapObjectDrawer;
    private ClickMenusDrawer clickMenusDrawer;
    private SpritesDrawer spritesDrawer;
    private boolean cutView = false;

    public IsoViewer(Map map, Canvas canvas, List<Character> characters) {
        mapDrawer = new MapDrawer(map, canvas);
        spritesDrawer = new SpritesDrawer(map, canvas, mapDrawer, characters);
        clickMenusDrawer = new ClickMenusDrawer(mapDrawer);
        mapDrawer.drawMap();
        spritesDrawer.drawVisibleSprites(cutView);
    }

    public void moveMap(Point mapMove) {
        mapDrawer.changeZeroScreenPosition(mapMove);
        if (MapDrawCalculator.mapOnScreen()) {
            mapDrawer.clearMapBounds();
            mapDrawer.drawMap();
            spritesDrawer.drawVisibleSprites(cutView);
            clickMenusDrawer.moveMenus(mapMove);
        } else {
            mapDrawer.changeZeroScreenPosition(new Point(-mapMove.x, -mapMove.y));
        }
    }

    public void draw() {
        mapDrawer.drawMap();
        spritesDrawer.drawVisibleSprites(cutView);
    }

    public MapDrawer getMapDrawer() {
        return mapDrawer;
    }

    public CharsDrawer getCharsDrawer() {
        return spritesDrawer.getCharsDrawer();
    }

    public ClickMenusDrawer getClickMenusDrawer() {
        return clickMenusDrawer;
    }

    public void switchCutView() {
        cutView = !cutView;
    }
}
