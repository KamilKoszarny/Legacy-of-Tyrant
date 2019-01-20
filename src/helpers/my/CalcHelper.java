package helpers.my;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class CalcHelper {

    public static int calcTrues(boolean[] array) {
        int trues = 0;
        for (boolean value: array) {
            if (value)
                trues++;
        }
        return trues;
    }

    public static int sum(Collection<Integer> list) {
        int sum = 0;

        for (int i : list)
            sum = sum + i;

        return sum;
    }

    public static double[][] pointsList2Coords(List<Point> points) {
        int size = points.size();
        double[] xCoords = new double[size], yCoords = new double[size];
        for (int i = 0; i < size; i++) {
            xCoords[i] = points.get(i).getX();
            yCoords[i] = points.get(i).getY();
        }
        double[][] xyCoords = {xCoords, yCoords};
        return xyCoords;
    }
}
