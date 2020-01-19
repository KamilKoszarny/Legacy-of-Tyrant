package viewIso;

import main.App;
import model.Battle;
import model.character.Character;
import model.map.MapPiece;
import model.map.visibility.VisibilityChecker;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
import viewIso.mapObjects.ItemObjectsDrawer;
import viewIso.mapObjects.MapObjectDrawer;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class SpritesDrawer {

    private static List<Point> visiblePoints = null;

    public SpritesDrawer() {
        new CharsDrawer();
        new LabelsDrawer();
        new MapObjectDrawer();
    }

    public static void drawVisibleSprites() {
        LabelsDrawer.hideLabels();
        App.resetTime(3);
        if (MapMover.isMapMoved() || visiblePoints == null) {
            visiblePoints = MapDrawCalculator.calcOnCanvasPoints();
            App.showAndResetTime("getVisiblePoints", 3);
            visiblePoints.sort(Comparator.comparingInt(c -> c.x + c.y));
            App.showAndResetTime("sortVisiblePoints", 3);
        }
        for (Point point: visiblePoints) {
            MapPiece mapPiece = Battle.getMap().getPoints().get(point);
            if (mapPiece.getObject() != null && VisibilityChecker.isExplored(point)) {
                MapObjectDrawer.drawObject(point);
            }
            drawCharIfThere(point);
        }
        App.showAndResetTime("drawObjectsAndChars", 3);
        for (Character character: Battle.getCharacters()) {
            CharsDrawer.drawChar(character, true);
        }
        App.showAndResetTime("drawTransparentChars", 3);

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
