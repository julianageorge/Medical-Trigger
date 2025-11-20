package Fuzzy.Defuzzy;

import java.util.List;
import java.util.Map;

public class Defuzzifier implements DefuzzificationMethod{
    @Override
    public double defuzzify(Map<Double, Double> aggregatedFuzzySet) {
        return 0;
    }

    @Override
    public double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths) {
        return 0;
    }
}
