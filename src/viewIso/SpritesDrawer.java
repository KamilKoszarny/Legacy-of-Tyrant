package viewIso;

import model.Battle;
import model.character.Character;
import model.map.MapPiece;
import viewIso.characters.CharsDrawer;
import viewIso.map.MapDrawCalculator;
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

    public void drawVisibleSprites(boolean cutView) {
        LabelsDrawer.hideLabels();
        List<Point> visiblePoints = MapDrawCalculator.calcVisiblePoints();
        visiblePoints.sort(Comparator.comparingInt(c -> c.x + c.y));
        for (Point point: visiblePoints) {
            MapPiece mapPiece = Battle.getMap().getPoints().get(point);
            if (mapPiece.getObject() != null) {
                mapObjectDrawer.drawObject(point, cutView);
            }
            for (Character character: Battle.getCharacters()) {
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
