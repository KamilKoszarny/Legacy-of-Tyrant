package viewIso;

import javafx.scene.canvas.Canvas;
import model.character.Character;
import model.map.Map;
import model.map.MapPiece;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.map.MapDrawer;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class SpritesDrawer {

    private Map map;
    private Canvas canvas;
    private List<Character> characters;
    private CharsDrawer charsDrawer;
    private MapObjectDrawer mapObjectDrawer;

    public SpritesDrawer(Map map, Canvas canvas, MapDrawer mapDrawer, List<Character> characters) {
        this.map = map;
        this.canvas = canvas;
        this.characters = characters;
        charsDrawer = new CharsDrawer(map, canvas, mapDrawer, characters);
        mapObjectDrawer = new MapObjectDrawer(map, canvas);
    }

    public void drawVisibleSprites(boolean cutView) {
        List<Point> visiblePoints = MapDrawCalculator.calcVisiblePoints();
        visiblePoints.sort(Comparator.comparingInt(c -> c.x + c.y));
        for (Point point: visiblePoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            if (mapPiece.getObject() != null) {
                mapObjectDrawer.drawObject(point, cutView);
            }
            for (Character character: characters) {
                if (point.equals(character.getPosition())) {
                    charsDrawer.drawChar(character);
                }
            }
        }
    }

    public CharsDrawer getCharsDrawer() {
        return charsDrawer;
    }
}
