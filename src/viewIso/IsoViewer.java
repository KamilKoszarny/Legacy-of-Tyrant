package viewIso;

import javafx.scene.canvas.Canvas;
import model.Battle;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;

import java.awt.*;


public class IsoViewer {
    private MapDrawer mapDrawer;
    private static ClickMenusDrawer clickMenusDrawer;
    private SpritesDrawer spritesDrawer;
    private static boolean cutView = false;

    public IsoViewer(Battle battle, Canvas canvas) {
        mapDrawer = new MapDrawer(battle.getMap(), canvas);
        spritesDrawer = new SpritesDrawer(battle.getMap(), canvas, mapDrawer, battle.getCharacters());
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

    public static ClickMenusDrawer getClickMenusDrawer() {
        return clickMenusDrawer;
    }

    public static void switchCutView() {
        cutView = !cutView;
    }
}
