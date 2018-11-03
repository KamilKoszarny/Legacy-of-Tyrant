package model.map.terrains;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public enum Terrain {
    GROUND(Color.rgb(135, 86, 1), 1),
    GRASS(Color.rgb(0, 180, 0), 0.9),
    BUSH(Color.rgb(0, 120, 0), 0.8),
    TREES(Color.rgb(0, 60, 0), 0.8),
    WATER(Color.rgb(37, 109, 123), 0.4),
    WALL(Color.DARKGRAY, 0);

    Color color;
    double moveFactor;
    double intensity;
    ArrayList<Point> focalPoints;

    Terrain(Color color, double moveFactor){
        this.color = color;

        this.moveFactor = moveFactor;
    }

    public Color getColor() {
        return color;
    }

    public double getMoveFactor() {
        return moveFactor;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public ArrayList<Point> getFocalPoints() {
        return focalPoints;
    }

    public void setFocalPoints(ArrayList<Point> focalPoints) {
        this.focalPoints = focalPoints;
    }
}
