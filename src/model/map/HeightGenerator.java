package model.map;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeightGenerator {

    Map map;

    public HeightGenerator(Map map) {
        this.map = map;
    }

    public void generateHeights(int hilly){
        MapPiece mapPiece;
        int i = 0;
        for (Point point: map.getPoints().keySet()) {
            mapPiece = map.getPoints().get(point);
            mapPiece.setHeight(new Random().nextInt(hilly));
            mapPiece.setHeight(10*(mapPiece.getHeight()/5));
        }
        for (Point point: map.getPoints().keySet()) {
            shapeMapPiece(point);
        }
    }

    public void shapeMapPiece(Point point){

        MapPiece mapPiece = map.getPoints().get(point);
        Point mapPointN, mapPointNE, mapPointE, mapPointSE, mapPointS, mapPointSW, mapPointW, mapPointNW;

        mapPointN = new Point(point.x - 1, point.y - 1);
        mapPointNE = new Point(point.x, point.y - 1);
        mapPointE = new Point(point.x + 1, point.y - 1);
        mapPointSE = new Point(point.x + 1, point.y);
        mapPointS = new Point(point.x + 1, point.y + 1);
        mapPointSW = new Point(point.x, point.y + 1);
        mapPointW = new Point(point.x - 1, point.y + 1);
        mapPointNW = new Point(point.x - 1, point.y);

        mapPiece.setHeightN(calcVertexHeight(mapPointN, mapPointNE, point, mapPointNW));
        mapPiece.setHeightE(calcVertexHeight(mapPointNE, mapPointE, mapPointSE, point));
        mapPiece.setHeightS(calcVertexHeight(point, mapPointSE, mapPointS, mapPointSW));
        mapPiece.setHeightW(calcVertexHeight(mapPointNW, point, mapPointSW, mapPointW));
    }

    public void lightMapPiece(Point point, Light light){
        MapPiece mapPiece = map.getPoints().get(point);
        Color color = mapPiece.getColor();
        int slope = mapPiece.getSlope();

        color.deriveColor(0, 1, 1.1, 1);
    }

    private int calcVertexHeight(Point... vertexSurroundingPoints){
        int height = 0;
        int count = vertexSurroundingPoints.length;
        for (Point point: vertexSurroundingPoints) {
            if (map.getPoints().keySet().contains(point))
                height += map.getPoints().get(point).getHeight();
            else
                count--;
        }
        height /= count;
        return height;
    }

}
