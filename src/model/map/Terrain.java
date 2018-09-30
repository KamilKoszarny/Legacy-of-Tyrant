package model.map;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public enum Terrain {
    GROUND(Color.rgb(135, 86, 1), 1),
    GRASS(Color.rgb(0, 250, 0), 0.9),
    BUSH(Color.rgb(0, 150, 0), 0.7),
    TREES(Color.rgb(0, 50, 0), 0.5),
    WALL(Color.DARKGRAY, 0);

    Color color;
    double moveFactor;
    double intensity;
    ArrayList<Point> focalPoints;

    Terrain(Color color, double moveFactor){
        this.color = color;
//        System.out.println(color);
//        System.out.println(color.deriveColor(0, 1, 1.5, 1));

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
