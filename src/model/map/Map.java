package model.map;

import java.awt.*;
import java.util.HashMap;

public class Map {

    public static final double RESOLUTION_M = 0.1;

    private int widthM;
    private int heightM;
    private java.util.Map<Point, MapPiece> points = new HashMap<>();

    public Map(int widthM, int heightM) {
        this.widthM = widthM;
        this.heightM = heightM;

        for (int i = 0; i < widthM / RESOLUTION_M; i += 1)
            for (int j = 0; j < heightM / RESOLUTION_M; j += 1){
                points.put(new Point(i, j), new MapPiece());
            }
    }

    public java.util.Map<Point, MapPiece> getPoints() {
        return points;
    }

    public int getHeight() {
        return (int)(heightM / RESOLUTION_M);
    }

    public int getWidth() {
        return (int)(widthM / RESOLUTION_M);
    }
}
