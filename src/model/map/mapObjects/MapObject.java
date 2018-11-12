package model.map.mapObjects;

import helpers.downloaded.pathfinding.algorithms.datatypes.Point;

public class MapObject{

    private MapObjectType type;
    private int size;
    private int look;


    public MapObject(MapObjectType type, int size, int look) {
        this.type = type;
        this.size = size;
        this.look = look;
    }

    public MapObjectType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getLook() {
        return look;
    }

    public void setLook(int look) {
        this.look = look;
    }
}
