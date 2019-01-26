package viewIso;

import model.Battle;
import model.character.Character;
import model.map.MapPiece;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.mapObjects.ItemObjectsDrawer;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class SpritesDrawer {

    private CharsDrawer charsDrawer;
    private MapObjectDrawer mapObjectDrawer;

    public SpritesDrawer() {
        charsDrawer = new CharsDrawer();
        new LabelsDrawer();
        mapObjectDrawer = new MapObjectDrawer();
    }

    public void drawVisibleSprites() {
        LabelsDrawer.hideLabels();
        List<Point> visiblePoints = MapDrawCalculator.calcOnCanvasPoints();
        visiblePoints.sort(Comparator.comparingInt(c -> c.x + c.y));
        for (Point point: visiblePoints) {
            if (MapDrawCalculator.isExplored(point)) {
                MapPiece mapPiece = Battle.getMap().getPoints().get(point);
                if (mapPiece.getObject() != null) {
                    mapObjectDrawer.drawObject(point);
                }
                drawCharIfThere(point);
            }
        }
        for (Character character: Battle.getCharacters()) {
            charsDrawer.drawChar(character, true);
        }

        ItemObjectsDrawer.resetItemGlowIncrement();
    }

    private void drawCharIfThere(Point point) {
        for (Character character : Battle.getCharacters()) {
            if (point.equals(character.getPosition())) {
                charsDrawer.drawChar(character, false);
            }
        }
    }
}
