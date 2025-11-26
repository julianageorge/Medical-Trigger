package Fuzzy.Defuzzy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MOMDefuzzifier implements DefuzzificationMethod{
    @Override
    public double defuzzify(Map<Double, Double> aggregatedFuzzySet) {
        double max = -1;

        for (double value : aggregatedFuzzySet.values()) {
            if (value > max)
                max = value;
        }

        List<Double> xs = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : aggregatedFuzzySet.entrySet()) {
            if (entry.getValue() == max)
                xs.add(entry.getKey());
        }

        if (xs.isEmpty()) return 0;
        double sum = 0;
        for (double x : xs) sum += x;

        return sum / xs.size();
    }

    @Override
    public double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths) {
        throw new UnsupportedOperationException("MoM does not support Sugeno defuzzification");
    }
}
