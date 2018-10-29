package model.map.heights;

import helpers.OpenSimplexNoise;
import model.map.Map;
import model.map.MapPiece;

import java.awt.*;
import java.util.Random;

public class HeightGenerator {

    public static final int H_PEX_PIX = 1000;

    Map map;
    MapHeightType heightType;
    private int hilly;
    private int maxHeight = 0;
    Random r = new Random();


    public HeightGenerator(Map map) {
        this.map = map;
        heightType = map.getHeightType();
        hilly = (int) (heightType.getHilly() * (r.nextDouble()*0.5 + 0.5));
    }

    public void generateHeights() {
        withOpenSimplexNoise(100);
        withOpenSimplexNoise(20);
    }

    private void withOpenSimplexNoise(double featureSize){
        OpenSimplexNoise noise = new OpenSimplexNoise();

        Point point;
        MapPiece mapPiece;
        int height;
        for (int y = 0; y < map.mapYPoints; y++) {
            for (int x = 0; x < map.mapXPoints; x++) {
                point = new Point(x, y);
                mapPiece = map.getPoints().get(point);
                double value = noise.eval(x / featureSize, y / featureSize);
                height = (int) ((map.MIN_HEIGHT_PIX + (map.MAX_HEIGHT_PIX - map.MIN_HEIGHT_PIX) * (value / 2 + 0.5)) * H_PEX_PIX /
                        (map.mapXPoints / featureSize) / (50. / hilly));
                mapPiece.setHeight(mapPiece.getHeight() + height);
//                System.out.println(value);
            }
        }
    }

    public void shapeMapPieces() {
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

        calcAndSetPieceSlope(mapPiece);
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

    private void calcAndSetPieceSlope(MapPiece piece){
        int pieceHeightN = piece.getHeightN() - piece.getHeight();
        int pieceHeightE = piece.getHeightE() - piece.getHeight();
        int pieceHeightS = piece.getHeightS() - piece.getHeight();
        int pieceHeightW = piece.getHeightW() - piece.getHeight();

        if (pieceHeightN == pieceHeightE && pieceHeightN == pieceHeightS && pieceHeightN == pieceHeightW) {
            piece.setSlope(0, 0);
            return;
        }

        int dir;
        int highestVertex = Math.max(pieceHeightN, Math.max(pieceHeightE, Math.max(pieceHeightS, pieceHeightW)));

        if (pieceHeightN == highestVertex) {
            dir = 0;
            if (pieceHeightN == pieceHeightE && pieceHeightN == pieceHeightW)
                dir = 50;
            else if (pieceHeightN == pieceHeightE)
                dir = 12;
            else if (pieceHeightN == pieceHeightW)
                dir = 87;
            else {
                dir += 12 * (pieceHeightE - pieceHeightW) / Math.max(pieceHeightN - pieceHeightE, pieceHeightN - pieceHeightW);
            }
        } else if (pieceHeightE == highestVertex) {
            dir = 25;
            if (pieceHeightE == pieceHeightS)
                dir = 37;
            else {
                dir += 12 * (pieceHeightS - pieceHeightN) / Math.max(pieceHeightE - pieceHeightN, pieceHeightE - pieceHeightS);
            }
        } else if (pieceHeightS == highestVertex) {
            if (pieceHeightS == pieceHeightW)
                dir = 37;
            else {
                dir = 50;
                dir += 12 * (pieceHeightW - pieceHeightE) / Math.max(pieceHeightS - pieceHeightE, pieceHeightS - pieceHeightW);
            }
        }else {
            dir = 75;
            dir += 12 * (pieceHeightN - pieceHeightS) / Math.max(pieceHeightW - pieceHeightN, pieceHeightW - pieceHeightS);
        }
        if (dir < 0)
            dir += 100;

//
//
////        int paramN = 50;
////        int paramE = 75;
////        int paramS = 0;
////        int paramW = 25;
////        if (pieceHeightE > pieceHeightW)
////            paramS = 100;
//        while (pieceHeightN < 0 || pieceHeightE < 0 || pieceHeightS < 0 || pieceHeightW < 0) {
//            pieceHeightN++;            pieceHeightE++;            pieceHeightS++;            pieceHeightW++;
//        }
//        if ((pieceHeightN + pieceHeightE + pieceHeightS + pieceHeightW) == 0){
//            pieceHeightN++;            pieceHeightE++;            pieceHeightS++;            pieceHeightW++;
//        }
//        if (pieceHeightN > 0)
//            pieceHeightN = 1;
//        if (pieceHeightE > 0)
//            pieceHeightE = 1;
//        if (pieceHeightS > 0)
//            pieceHeightS = 1;
//        if (pieceHeightW > 0)
//            pieceHeightW = 1;
//        int dir = (pieceHeightN * paramN + pieceHeightE * paramE + pieceHeightS * paramS + pieceHeightW * paramW) /
//                (pieceHeightN + pieceHeightE + pieceHeightS + pieceHeightW);
////        System.out.println(dir);
        int size = Math.abs(pieceHeightN - pieceHeightS) + Math.abs(pieceHeightE - pieceHeightW);
        if (size < 100)
            size = 0;
        piece.setSlope (dir, size);

//        System.out.println(pieceHeightN+ " " +            pieceHeightE+ " " +            pieceHeightS+ " " +            pieceHeightW);
//        System.out.println(dir + " s: " + size);

    }
}
