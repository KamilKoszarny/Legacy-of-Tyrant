package model.map;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public enum Terrain {
    GROUND(Color.rgb(135, 86, 1)),
    GRASS(Color.rgb(0, 250, 0)),
    BUSH(Color.rgb(0, 150, 0)),
    TREES(Color.rgb(0, 50, 0));

    Color color;
    double intensity;
    ArrayList<Point> focalPoints;

    Terrain(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
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

    public Point getFocalPoint(int i) {
        return focalPoints.get(i);
    }

}
