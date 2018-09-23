package model.map;


import javafx.scene.paint.Color;

import java.awt.*;

public class LightGenerator {
    Map map;
    int dir, height, amount;

    public LightGenerator(Map map) {
        this.map = map;
    }

    public void generateLight(int dir, int height, int amount){
        this.dir = dir;
        this.amount = amount;
        this.height = height;

        for (Point p: map.getPoints().keySet()) {
            MapPiece mapPiece = map.getPoints().get(p);
            lightMapPiece(mapPiece);
        }
    }

    private void lightMapPiece(MapPiece mapPiece){
        Color color = mapPiece.getTerrain().getColor();
        int slopeDir = mapPiece.getSlopeDir();
        int slopeSize = mapPiece.getSlopeSize();
        int light = (10000 - (slopeDir - dir)%100 * Math.abs(slopeSize - height)) / 100 * amount;
        System.out.println(light);
        color = color.deriveColor(0, 1, 1 + (double)light/100, 1);
        mapPiece.setColor(color);
    }
}
