package model.map.mapObjects;

import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.Random;

public class MapObjectsGenerator {

    private Map map;

    public MapObjectsGenerator(Map map) {
        this.map = map;
    }

    public void generateObjects() {
        generateTrees();
    }

    private void generateTrees() {
        Random r = new Random();
        int size, kind;
        for (Point point: map.getPoints().keySet()) {
            MapPiece mapPiece = map.getPoints().get(point);
            if (mapPiece.getTerrain() == Terrain.TREES && r.nextInt(500) < 1) {
                size = r.nextInt(MapObjectType.TREE.getSizes());
                kind = r.nextInt(MapObjectType.TREE.getLooks());
                mapPiece.setObject(new MapObject(MapObjectType.TREE, size, kind));
            }
        }
    }
}
