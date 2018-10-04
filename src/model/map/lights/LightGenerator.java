package model.map.lights;


import model.map.Map;
import model.map.MapPiece;
import model.map.heights.HeightGenerator;

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
        this.height = height * HeightGenerator.H_PEX_PIX;

        for (Point p: map.getPoints().keySet()) {
            MapPiece mapPiece = map.getPoints().get(p);
            lightMapPiece(mapPiece);
        }
    }

    private void lightMapPiece(MapPiece mapPiece){
        int slopeDir = mapPiece.getSlopeDir();
        int slopeSize = mapPiece.getSlopeSize();
//        int light = (int) ((5000 - Math.pow((Math.abs(slopeDir - 50) - dir), 2) * Math.pow(Math.abs(slopeSize - height) / 1000, 2) / HeightGenerator.H_PEX_PIX) / 100 * amount);
        int light = 50 * (100 - Math.abs(50 - Math.abs(slopeDir - 100 + dir)) * slopeSize / 1500) / 100;
        if (light < -30)
            light = -30;
//        System.out.println(light);
        mapPiece.setLight(light);
    }
}
