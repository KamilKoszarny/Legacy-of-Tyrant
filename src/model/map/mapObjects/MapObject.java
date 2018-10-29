package model.map.mapObjects;

public class MapObject{

    public static final int SIZES = 3;
    public static final int KINDS = 8;

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
}
