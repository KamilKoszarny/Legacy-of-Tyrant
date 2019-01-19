package model.map.buildings;

import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

public class Wall extends MapObject {

    public Wall(int look, WallType type) {
        super(MapObjectType.WALL, 0, type.getCode() + look);
    }

    public Wall(int side1, int side2, WallType type) {
        super(MapObjectType.WALL, 0, type.getCode() + side1 * 100 + side2 * 10);
    }
}
