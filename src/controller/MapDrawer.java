package controller;

import javafx.scene.canvas.GraphicsContext;
import model.Map;

import javafx.scene.paint.Color;

import java.awt.*;

public class MapDrawer {

    GraphicsContext g;

    public MapDrawer(GraphicsContext g){
        this.g = g;
    }

    public void drawMap(Map map){
        double x, y;
        Color color;
        for (Point point : map.getPoints().keySet()){
            x = point.x;
            y = point.y;
            color = map.getPoints().get(point).getTerrain().getColor();

            g.setFill(color);
            g.fillRect(x, y,1, 1);
        }

    }
}
