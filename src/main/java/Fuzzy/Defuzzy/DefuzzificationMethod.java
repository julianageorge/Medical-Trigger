package Fuzzy.Defuzzy;

import java.util.List;
import java.util.Map;

public interface DefuzzificationMethod{
    double defuzzify(Map<Double, Double> aggregatedFuzzySet);
    double defuzzify(List<Double> ruleOutputs, List<Double> firingStrengths);
 }