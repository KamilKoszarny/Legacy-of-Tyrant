package model.map;

import javafx.scene.paint.Color;

import java.awt.*;

public class HeightGenerator {

    Map map;

    public void setMap(Map map) {
        this.map = map;
    }

    public void shapeMapPiece(Point point){

//cut to: height generation (mapPiece.setheightN...)

        MapPiece mapPiece = map.getPoints().get(point);
        Point mapPointN, mapPointE, mapPointS, mapPointW;

        mapPointN = new Point(point.x + 1, point.y - 1);
        mapPointE = new Point(point.x + 1, point.y + 1);
        mapPointS = new Point(point.x - 1, point.y + 1);
        mapPointW = new Point(point.x - 1, point.y - 1);

        mapPiece.setHeightN(calcVertexHeight(point, mapPointN));
        mapPiece.setHeightE(calcVertexHeight(point, mapPointE));
        mapPiece.setHeightS(calcVertexHeight(point, mapPointS));
        mapPiece.setHeightW(calcVertexHeight(point, mapPointW));
    }

    public void lightMapPiece(Point point, Light light){
        MapPiece mapPiece = map.getPoints().get(point);
        Color color = mapPiece.getColor();
        int slope = mapPiece.getSlope();

        color.deriveColor(0, 1, 1.1, 1);
    }

    private int calcVertexHeight(Point point, Point neighbourPoint){
        if (map.getPoints().keySet().contains(neighbourPoint))
            return (map.getPoints().get(point).getHeight() + map.getPoints().get(neighbourPoint).getHeight()) / 2;
        else
            return map.getPoints().get(point).getHeight();
    }


}
