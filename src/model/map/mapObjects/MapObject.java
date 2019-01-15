package model.map.mapObjects;


public class MapObject{

    private MapObjectType type;
    private int size;
    private int look;
    private boolean cutable;

    public MapObject(MapObjectType type, boolean cutable) {
        this.type = type;
        this.cutable = cutable;
    }

    public MapObject(MapObjectType type, int size, int look, boolean cutable) {
        this.type = type;
        this.size = size;
        this.look = look;
        this.cutable = cutable;
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

    public boolean isCutable() {
        return cutable;
    }
}
