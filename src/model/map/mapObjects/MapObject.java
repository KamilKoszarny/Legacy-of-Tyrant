package model.map.mapObjects;


public class MapObject{

    private MapObjectType type;
    private int size;
    private int look;
    private String name;

    public MapObject(MapObjectType type, String name) {
        this.type = type;
        this.name = name;
    }

    public MapObject(MapObjectType type, int size, int look) {
        this.type = type;
        this.size = size;
        this.look = look;
        this.name = type.name();
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

    public String getName() {
        return name;
    }
}
