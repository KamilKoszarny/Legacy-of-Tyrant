package helpers.my;

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
}
