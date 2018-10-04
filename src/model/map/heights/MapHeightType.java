package model.map.heights;

public enum MapHeightType {
    FLAT(5),
    VALLEY(20),
    MOUTAINS(80),
    PEAK(100);

    private int hilly;

    MapHeightType(int hilly) {
        this.hilly = hilly;
    }

    public int getHilly() {
        return hilly;
    }
}
