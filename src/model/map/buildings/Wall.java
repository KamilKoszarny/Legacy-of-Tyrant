package model.map.buildings;

import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

public class Wall extends MapObject {

    public Wall(int look) {
        super(MapObjectType.WALL, 1, look);
    }
}
