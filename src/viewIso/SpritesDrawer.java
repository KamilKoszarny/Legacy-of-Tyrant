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

    public SpritesDrawer() {
        new CharsDrawer();
        new LabelsDrawer();
        new MapObjectDrawer();
    }

    public static void drawVisibleSprites() {
        LabelsDrawer.hideLabels();
        List<Point> visiblePoints = MapDrawCalculator.calcOnCanvasPoints();
        visiblePoints.sort(Comparator.comparingInt(c -> c.x + c.y));
        for (Point point: visiblePoints) {
            MapPiece mapPiece = Battle.getMap().getPoints().get(point);
            if (mapPiece.getObject() != null) {
                if (MapDrawCalculator.isExplored(point)) {
                    MapObjectDrawer.drawObject(point);
                }
            }
            drawCharIfThere(point);
        }
        for (Character character: Battle.getCharacters()) {
            CharsDrawer.drawChar(character, true);
        }

        ItemObjectsDrawer.resetItemGlowIncrement();
    }

    private static void drawCharIfThere(Point point) {
        for (Character character : Battle.getCharacters()) {
            if (point.equals(character.getPosition())) {
                CharsDrawer.drawChar(character, false);
            }
        }
    }
}
