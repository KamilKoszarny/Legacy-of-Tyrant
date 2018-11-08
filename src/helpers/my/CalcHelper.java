package helpers.my;

public class CalcHelper {

    public static int calcTrues(boolean[] array) {
        int trues = 0;
        for (boolean value: array) {
            if (value)
                trues++;
        }
        return trues;
    }
}
