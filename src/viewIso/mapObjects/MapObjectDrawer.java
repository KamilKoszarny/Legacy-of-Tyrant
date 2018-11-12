package viewIso.mapObjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
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
    private static java.util.Map<Point, MapObjectSprite> mapObjectSpriteMap = new HashMap<>();

    public MapObjectDrawer(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        initSpriteMap();
    }

    public static void refreshSpriteMap(Point point, Map map) {
        MapPiece mapPiece = map.getPoints().get(point);
        if (mapPiece.getObject() != null)
            mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject()));
    }

    private void initSpriteMap() {
        for (Point point: map.getPoints().keySet()) {
            MapPiece mapPiece = map.getPoints().get(point);
            if (mapPiece.getObject() != null)
                mapObjectSpriteMap.put(point, new MapObjectSprite(mapPiece.getObject()));
        }
    }

    public void drawObject (Point point, boolean cutView) {
        MapObjectSprite mapObjectSprite = mapObjectSpriteMap.get(point);
        Image image = mapObjectSprite.getImage();
        Point screenPos = MapDrawCalculator.screenPositionWithHeight(point);
        if (cutView){
            int sourceX = (int) (image.getWidth() * .4);
            int sourceY = (int)(mapObjectSprite.getOffset().y - (image.getHeight() - mapObjectSprite.getOffset().y)/2);
            int sourceWidth = (int) (image.getWidth() * .2);
            int sourceHeight = (int)(image.getHeight() - mapObjectSprite.getOffset().y);
            canvas.getGraphicsContext2D().drawImage(image,
                    sourceX, sourceY,
                    sourceWidth, sourceHeight,
                    screenPos.x - mapObjectSprite.getOffset().x + sourceX, screenPos.y - mapObjectSprite.getOffset().y + sourceY,
                    sourceWidth, sourceHeight);
        } else
            canvas.getGraphicsContext2D().drawImage(image,
                screenPos.x - mapObjectSprite.getOffset().x, screenPos.y - mapObjectSprite.getOffset().y);
    }


}
