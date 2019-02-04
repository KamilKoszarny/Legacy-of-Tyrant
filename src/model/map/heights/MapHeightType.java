package model.map.heights;

public enum MapHeightType {
    FLAT(5),
    VALLEY(25),
    HILLS(80),
    MOUNTAINS(120);

    private int hilly;

    MapHeightType(int hilly) {
        this.hilly = hilly;
    }

    public int getHilly() {
        return hilly;
    }
}
