package viewIso.mapObjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import model.character.Character;
import model.map.Map;
import model.map.MapPiece;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MapObjectDrawer {

    private Map map;
    private Canvas canvas;
    private java.util.Map<Point, MapObjectSprite> mapObjectSpriteMap = new HashMap<>();

    public MapObjectDrawer(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        initSpriteMap();
    }

    private void initSpriteMap() {
        for (Point point: map.getPoints().keySet()) {
            MapPiece mapPiece = map.getPoints().get(point);
            if (mapPiece.getObject() != null)
                mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject()));
        }
    }

    public void drawVisibleObjects() {
        List<Point> visiblePoints = MapDrawCalculator.calcVisiblePoints();
        visiblePoints.sort(Comparator.comparingInt(c -> c.x + c.y));
        for (Point point: visiblePoints) {
            MapPiece mapPiece = map.getPoints().get(point);
            if (mapPiece.getObject() != null) {
                drawObject(point);
            }
        }
    }

    public void drawObject (Point point) {
        MapObjectSprite mapObjectSprite = mapObjectSpriteMap.get(point);
        Image image = mapObjectSprite.getImage();
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        canvas.getGraphicsContext2D().drawImage(image,
                screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
    }


}
