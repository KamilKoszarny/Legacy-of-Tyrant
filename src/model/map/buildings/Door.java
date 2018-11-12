package model.map.buildings;

import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

public class Door extends MapObject {

    public static final int ACTION_DIST = 4;
    boolean open;


    public Door(int look, WallType type) {
        super(MapObjectType.DOOR, 0, type.getCode() + look);
    }

    public boolean isOpen() {
        return open;
    }

    public void switchOpen() {
        open = !open;
    }
}
