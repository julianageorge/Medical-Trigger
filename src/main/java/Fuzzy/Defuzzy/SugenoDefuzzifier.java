package Fuzzy.Defuzzy;

import java.util.List;
import java.util.Map;

public class SugenoDefuzzifier implements DefuzzificationMethod{
    @Override
    public double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths) {
        double numerator = 0.0;
        double denominator = 0.0;

        for (int i = 0; i < ruleOutputs.size(); i++) {
            numerator += ruleOutputs.get(i) * firingStrengths.get(i);
            denominator += firingStrengths.get(i);
        }

        return (denominator == 0) ? 0 : numerator / denominator;
    }

    @Override
    public double defuzzify(Map<Double, Double> aggregatedFuzzySet) {
        throw new UnsupportedOperationException("Sugeno does not support Mamdani defuzzification");
    }
}
