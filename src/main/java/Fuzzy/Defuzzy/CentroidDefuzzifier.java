package Fuzzy.Defuzzy;

import java.util.List;
import java.util.Map;

public class CentroidDefuzzifier implements DefuzzificationMethod {

    @Override
    public double defuzzify(Map<Double, Double> aggregatedFuzzySet) {
        double numerator = 0.0;
        double denominator = 0.0;

        for (Map.Entry<Double, Double> entry : aggregatedFuzzySet.entrySet()) {
            double x = entry.getKey();
            double mu = entry.getValue();

            numerator += x * mu;
            denominator += mu;
        }

        return (denominator == 0) ? 0 : numerator / denominator;
    }

    @Override
    public double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths) {
        throw new UnsupportedOperationException("Centroid does not support Sugeno defuzzification");
    }
}
