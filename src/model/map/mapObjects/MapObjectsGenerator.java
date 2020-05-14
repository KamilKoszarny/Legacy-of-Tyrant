package model.map.mapObjects;

import helpers.my.GeomerticHelper;
import model.map.Map;
import model.map.MapPiece;
import model.map.buildings.BuildingGenerator;
import model.map.terrains.Terrain;

import java.awt.*;
import java.util.Random;

public class MapObjectsGenerator {

    private Map map;
    private Random r = new Random();

    public MapObjectsGenerator(Map map) {
        this.map = map;
    }

    public void generateObjects() {
        Terrain terrain;
        for (Point point: map.getPoints().keySet()) {
            if (!BuildingGenerator.inBuilding(point)) {
                terrain = map.getPoints().get(point).getTerrain();
                if (hasObjects(terrain))
                    calcAndSetMapObject(point, terrain);
            }
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
            setNonWalkablePieces(point, size + 1);
            setPiecesTransparency(point, size, type.getTransparency());
        }
    }

    private int checkAvailableSize(Point point, Terrain terrain) {
        int sizes = MapObjectType.mapObjectTypeByTerrain(map.getPoints().get(point).getTerrain()).getSizes();
        if (sizes == 0)
            return 0;
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
        for (Point point: GeomerticHelper.pointsInRadius(basePoint, radius, map)) {
            if (map.getPoints().get(point) != null) {
                map.getPoints().get(point).setWalkable(false);
            }
        }
    }

    private void setPiecesTransparency(Point basePoint, int radius, double transparency) {
        for (Point point: GeomerticHelper.pointsInRadius(basePoint, radius, map)) {
            if (map.getPoints().get(point) != null) {
                map.getPoints().get(point).setTransparency(transparency);
            }
        }
    }

    private boolean hasObjects(Terrain terrain){
        for (MapObjectType objectType: MapObjectType.values()) {
            if (objectType.getTerrain() != null && objectType.getTerrain().equals(terrain))
                return true;
        }
        return false;
    }
}
