package helpers.my;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomHelper {

    public static Object shuffleFrom2DoubleMap(Map<? extends Object, Double> map) {
        double probabilitiesSum = 0;
        for (Double probability: map.values()) {
            probabilitiesSum += probability;
        }

        Map<Object, Double> normalizedMap = new HashMap<>();
        double normProbCurrentSum = 0;
        for (Object object: map.keySet()) {
            double probability = map.get(object);
            double normProb = probability / probabilitiesSum;
            normalizedMap.put(object, normProbCurrentSum + normProb);
            normProbCurrentSum += normProb;
        }

        double shuffledValue = new Random().nextDouble();
        for (Object object: normalizedMap.keySet()) {
            double normProb = normalizedMap.get(object);
            if (shuffledValue < normProb)
                return object;
        }

        return null;
    }
}
