package model.map.buildings;

public enum WallType {
    WOOD(1000);

    private int code;

    WallType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
