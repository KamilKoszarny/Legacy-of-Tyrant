package model.map;

public enum MapHeightType {
    FLAT(5, 60, 0),
    PEAK(50, 100, 1),
    MOUTAINS(50, 300, 5),
    VALLEY(20, 250, 4);

    private int hilly, slope, peaksCount;

    MapHeightType(int hilly, int slope, int peaksCount) {
        this.hilly = hilly;
        this.slope = slope;
        this.peaksCount = peaksCount;
    }

    public int getHilly() {
        return hilly;
    }

    public int getSlope() {
        return slope;
    }

    public int getPeaksCount() {
        return peaksCount;
    }
}
