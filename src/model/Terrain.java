package model;

import javafx.scene.paint.Color;

public enum Terrain {
    GROUND(Color.rgb(135, 86, 1)),
    GRASS(Color.rgb(0, 250, 0)),
    BUSH(Color.rgb(0, 150, 0)),
    TREES(Color.rgb(0, 50, 0));

    Color color;

    Terrain(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
