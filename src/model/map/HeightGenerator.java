package model.map;

import helpers.OpenSimplexNoise;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeightGenerator {

    public static final int H_PEX_PIX = 1000;

    Map map;
    MapHeightType heightType;
    private int hilly, slope, peaksCount;
    private int maxHeight = 0;
    Random r = new Random();


    public HeightGenerator(Map map) {
        this.map = map;
        heightType = map.getHeightType();
        hilly = (int) (heightType.getHilly() * (r.nextDouble()*0.5 + 0.5));
        slope = (int) Math.min(heightType.getSlope() * (r.nextDouble() + 0.5), map.MAX_HEIGHT);
        peaksCount = (int) (heightType.getPeaksCount() * (r.nextDouble() + 0.5));
//        if (heightType == MapHeightType.PEAK)
            peaksCount = 1;
    }

    public void generateHeights() {
//        createSlope();
//        createPeaks();
//        creaseHeights();
        withOpenSimplexNoise(100);
        withOpenSimplexNoise(20);
//        creaseHeights();
        for (Point point: map.getPoints().keySet()) {
            shapeMapPiece(point);
        }
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
                double value = noise.eval(x / featureSize, y / featureSize, 0.0);
                height = (int) ((map.MIN_HEIGHT + (map.MAX_HEIGHT - map.MIN_HEIGHT) * (value / 2 + 0.5)) * H_PEX_PIX / (map.mapXPoints / featureSize));
                mapPiece.setHeight(mapPiece.getHeight() + height);
                System.out.println(value);
            }
        }
    }

    private void createSlope(){
        MapPiece mapPiece;
        int height;
        int dir = r.nextInt(8);
//        System.out.println(dir);
        for (Point point: map.getPoints().keySet()) {
            height = calcSlopeHeight(point, dir);
            mapPiece = map.getPoints().get(point);
            mapPiece.setHeight(calcSlopeHeight(point, dir));
            if (maxHeight < height)
                maxHeight = height;
        }
    }

    private int calcSlopeHeight(Point point, int dir){
        int height = 0;
        if (dir == 0)
            height = H_PEX_PIX * slope * ((map.mapXPoints - point.x) + (map.mapYPoints - point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 1)
            height = H_PEX_PIX * slope * ((map.mapYPoints - point.y))/(map.mapYPoints);
        if (dir == 2)
            height = H_PEX_PIX * slope * ((point.x) + (map.mapYPoints - point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 3)
            height = H_PEX_PIX * slope * ((point.x))/(map.mapXPoints);
        if (dir == 4)
            height = H_PEX_PIX * slope * ((point.x) + (point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 5)
            height = H_PEX_PIX * slope * ((point.y))/(map.mapYPoints);
        if (dir == 6)
            height = H_PEX_PIX * slope * ((map.mapXPoints - point.x) + (point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 7)
            height = H_PEX_PIX * slope * ((map.mapXPoints - point.x))/(map.mapXPoints);
        return height;
    }

    private void createPeaks(){
        createPeaksInSize(1);
        createPeaksInSize(2);
//        createPeaksInSize(3);
//        createPeaksInSize(4);
//        createPeaksInSize(5);
//        createPeaksInSize(6);
//        createPeaksInSize(7);
//        createPeaksInSize(8);
//        createPeaksInSize(9);
    }

    private void createPeaksInSize(int sizeDivider){
        List<Peak> peaks = new ArrayList<>();
        Peak peak;
//        System.out.println(peaksCount);
        for (int i = 0; i < peaksCount * sizeDivider; i++) {
            peak = new Peak();
            peak.position = new Point((int)(map.mapXPoints * r.nextDouble()), (int)(map.mapYPoints * r.nextDouble()));
            System.out.println(peak.position);
            int j = 0;
            do  {
                peak.height = (int) (H_PEX_PIX * hilly * 10 * (r.nextDouble()*0.5 + 0.5)) / sizeDivider / peaksCount;
                j++;
            } while ((peak.height + map.getPoints().get(peak.position).getHeight() < map.MIN_HEIGHT * H_PEX_PIX ||
                    peak.height  + map.getPoints().get(peak.position).getHeight() > map.MAX_HEIGHT * H_PEX_PIX) &&
                    j < 500);
            peak.slope = (int) (peak.height / ((map.mapXPoints + map.mapYPoints)/2) * (r.nextDouble() + 1));

            peaks.add(peak);
        }

        MapPiece mapPiece;
        for (Point point: map.getPoints().keySet()) {
            mapPiece = map.getPoints().get(point);
            int height = -1000;
            int newHeight;
            for (Peak p: peaks) {
                newHeight = calcPeakHeight(mapPiece, point, p);
                if (newHeight > height)
                    height = newHeight;
            }
            mapPiece.setHeight(height);
        }
    }

    private int calcPeakHeight(MapPiece mapPiece, Point point, Peak peak){
        int height = mapPiece.getHeight();
        double dist = point.distance(peak.position);

        height += peak.height - dist * peak.slope;
        if (height < map.MIN_HEIGHT * H_PEX_PIX)
            height = (int) map.MIN_HEIGHT * H_PEX_PIX;
        if (height > map.MAX_HEIGHT * H_PEX_PIX)
            height = (int) map.MAX_HEIGHT * H_PEX_PIX;
        return height;
    }

    private void creaseHeights(){
        MapPiece mapPiece;
        int height;
        for (Point point: map.getPoints().keySet()) {
            mapPiece = map.getPoints().get(point);
            height = mapPiece.getHeight();
            mapPiece.setHeight((int) (height + (r.nextDouble() * 1000 - 500)));
        }
    }

    private void shapeMapPiece(Point point){

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
