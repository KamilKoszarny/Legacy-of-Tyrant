package model.map.mapObjects;

import model.map.Map;
import model.map.MapPiece;
import model.map.terrains.Terrain;
import viewIso.map.MapDrawCalculator;

import java.awt.*;
import java.util.Random;

public class MapObjectsGenerator {

    private Map map;
    private Random r = new Random();

    public MapObjectsGenerator(Map map) {
        this.map = map;
    }

    public void generateObjects() {
        generateTrees();
    }

    private void generateTrees() {
        Terrain terrain;
        for (Point point: map.getPoints().keySet()) {
            terrain = map.getPoints().get(point).getTerrain();
            if (hasObjects(terrain))
                calcAndSetMapObject(point, terrain);
        }
    }

    private void calcAndSetMapObject(Point point, Terrain terrain) {
        int size, look;
        MapPiece mapPiece = map.getPoints().get(point);
        MapObjectType type = MapObjectType.mapObjectTypeByTerrain(mapPiece.getTerrain());

        if (r.nextInt(type.getProbabilityDivider()) < 1) {
            size = checkAvailableSize(point, terrain);
            look = r.nextInt(type.getLooks());
            mapPiece.setObject(new MapObject(type, size, look));
            setNonWalkablePieces(point, size);
        }
    }

    private int checkAvailableSize(Point point, Terrain terrain) {
        int sizes = MapObjectType.mapObjectTypeByTerrain(map.getPoints().get(point).getTerrain()).getSizes();
        for (int size = sizes; size > 0 ; size--)
            if (isSurroundedBySameTerrain(point, size, terrain))
                return size;
        return 0;
    }

    private boolean isSurroundedBySameTerrain(Point basePoint, int radius, Terrain baseTerrain) {
        Point point;
        Terrain terrain;
        for (int y = -radius/2; y <= (radius - 1)/2; y++) {
            for (int x = -radius/2; x <= (radius - 1)/2; x++) {
                point = new Point(basePoint.x + x, basePoint.y + y);
                if (map.getPoints().get(point) != null) {
                    terrain = map.getPoints().get(point).getTerrain();
                    if (!terrain.equals(baseTerrain))
                        return false;
                }
            }
        }
        return true;
    }

    private void setNonWalkablePieces(Point basePoint, int radius) {
        Point point;
        for (int y = -radius/2; y <= (radius - 1)/2; y++) {
            for (int x = -radius/2; x <= (radius - 1)/2; x++) {
                point = new Point(basePoint.x + x, basePoint.y + y);
                if (map.getPoints().get(point) != null) {
                    map.getPoints().get(point).setWalkable(false);
                }
            }
        }
    }

    private boolean hasObjects(Terrain terrain){
        for (MapObjectType objectType: MapObjectType.values()) {
            if (objectType.getTerrain().equals(terrain))
                return true;
        }
        return false;
    }
}
