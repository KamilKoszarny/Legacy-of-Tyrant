package model.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeightGenerator {

    Map map;
    MapHeightType heightType;
    private int hilly, slope, peaksCount;

    public HeightGenerator(Map map) {
        this.map = map;
        heightType = map.getHeightType();
        Random r = new Random();
        hilly = (int) (heightType.getHilly() * (r.nextDouble() + 0.5));
        slope = (int) Math.min(heightType.getSlope() * (r.nextDouble() + 0.5), map.MAX_HEIGHT);
//        System.out.println(slope);
        peaksCount = (int) (heightType.getPeaksCount() * (r.nextDouble() + 0.5));
        if (heightType == MapHeightType.PEAK)
            peaksCount = 1;
    }

    public void generateHeights(){

        createSlope();
        createPeaks();
        for (Point point: map.getPoints().keySet()) {
            shapeMapPiece(point);
        }
    }

    private void createSlope(){
        MapPiece mapPiece;
        int i = 0;
        int dir = new Random().nextInt(8);
//        System.out.println(dir);
        for (Point point: map.getPoints().keySet()) {
            mapPiece = map.getPoints().get(point);
            mapPiece.setHeight(calcSlopeHeight(point, dir));
        }
    }

    private int calcSlopeHeight(Point point, int dir){
        int height = 0;
        if (dir == 0)
            height = slope * ((map.mapXPoints - point.x) + (map.mapYPoints - point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 1)
            height = slope * ((map.mapYPoints - point.y))/(map.mapYPoints);
        if (dir == 2)
            height = slope * ((point.x) + (map.mapYPoints - point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 3)
            height = slope * ((point.x))/(map.mapXPoints);
        if (dir == 4)
            height = slope * ((point.x) + (point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 5)
            height = slope * ((point.y))/(map.mapYPoints);
        if (dir == 6)
            height = slope * ((map.mapXPoints - point.x) + (point.y))/(map.mapXPoints + map.mapYPoints);
        if (dir == 7)
            height = slope * ((map.mapXPoints - point.x))/(map.mapXPoints);
        return height;
    }

    private void createPeaks(){
        List<Peak> peaks = new ArrayList<>();
        Peak peak;
        Random r = new Random();
        System.out.println(peaksCount);
        for (int i = 0; i < peaksCount; i++) {
            peak = new Peak();
            peak.position = new Point((int)(map.mapXPoints * r.nextDouble()), (int)(map.mapYPoints * r.nextDouble()));
//            peak.position = new Point(20, 20);
            peak.height = (int) (hilly * 10 * (r.nextDouble() * 0.5 + 0.5));
//            peak.height = 500;
            peak.slope = (int) (peak.height / ((map.mapXPoints + map.mapYPoints)/2) * (r.nextDouble() * 2 + 1));
            peaks.add(peak);
            System.out.println(peak.position + "h: " + peak.height + "s: " + peak.slope);
        }

        MapPiece mapPiece;
        for (Point point: map.getPoints().keySet()) {
            mapPiece = map.getPoints().get(point);
            int height = -1000;
            for (Peak p: peaks) {
                if (calcPeakHeight(mapPiece, point, p) > height)
                    height = calcPeakHeight(mapPiece, point, p);
            }
            mapPiece.setHeight(height);
        }
    }

    private int calcPeakHeight(MapPiece mapPiece, Point point, Peak peak){
        int height = mapPiece.getHeight();
        double dist = point.distance(peak.position);
        height += peak.height - dist * peak.slope;
        if (height < map.MIN_HEIGHT)
            height = (int) map.MIN_HEIGHT;
        if (height > map.MAX_HEIGHT)
            height = (int) map.MAX_HEIGHT;
        return height;
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

        int paramN = 50;
        int paramE = 75;
        int paramS = 0;
        int paramW = 25;
        if (pieceHeightE > pieceHeightW)
            paramS = 100;
        while (pieceHeightN < 0 || pieceHeightE < 0 || pieceHeightS < 0 || pieceHeightW < 0) {
            pieceHeightN++;            pieceHeightE++;            pieceHeightS++;            pieceHeightW++;
        }
        if ((pieceHeightN + pieceHeightE + pieceHeightS + pieceHeightW) == 0){
            pieceHeightN++;            pieceHeightE++;            pieceHeightS++;            pieceHeightW++;
        }
        if (pieceHeightN > 0)
            pieceHeightN = 1;
        if (pieceHeightE > 0)
            pieceHeightE = 1;
        if (pieceHeightS > 0)
            pieceHeightS = 1;
        if (pieceHeightW > 0)
            pieceHeightW = 1;
        int dir = (pieceHeightN * paramN + pieceHeightE * paramE + pieceHeightS * paramS + pieceHeightW * paramW) /
                (pieceHeightN + pieceHeightE + pieceHeightS + pieceHeightW);
        int size = Math.abs(pieceHeightN - pieceHeightS) + Math.abs(pieceHeightE - pieceHeightW);
        piece.setSlope (dir, size);

//        System.out.println(pieceHeightN+ " " +            pieceHeightE+ " " +            pieceHeightS+ " " +            pieceHeightW);
//        System.out.println(dir + " s: " + size);

    }
}
