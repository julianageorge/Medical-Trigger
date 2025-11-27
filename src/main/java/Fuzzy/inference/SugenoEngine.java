package Fuzzy.inference;

import Fuzzy.Rules.Rule;
import Fuzzy.Rules.RuleBase;
import Fuzzy.variables.FuzzySet;
import Fuzzy.variables.LinguisticVariable;

import java.util.Map;

public class SugenoEngine implements InferenceEngine {

    private Map<String, LinguisticVariable> variables;

    public SugenoEngine(Map<String, LinguisticVariable> variables) {
        this.variables = variables;
    }

    @Override
    public double evaluate(Map<String, Double> inputs, RuleBase base) {

        double weightedSum = 0.0;
        double sumWeights   = 0.0;

        for (Rule rule : base.getRules()) {

            if (!rule.enabled)
                continue;

            double firingStrength = 1.0;

            for (Map.Entry<String, String> ant : rule.antecedent.entrySet()) {

                String varName = ant.getKey();
                String setName = ant.getValue();

                LinguisticVariable var = variables.get(varName);
                if (var == null) continue;

                FuzzySet fs = var.getFuzzySet(setName);
                if (fs == null) continue;

                double crispInput = inputs.get(varName);
                double μ = fs.getMembership(crispInput);

                firingStrength = Math.min(firingStrength, μ);
            }

            firingStrength *= rule.weight;  // scale by rule weight

            // 2. Evaluate Sugeno f(x)

            double fValue = computeSugenoFunction(rule.consequent, inputs);

            weightedSum += firingStrength * fValue;
            sumWeights  += firingStrength;
        }

        if (sumWeights == 0)
            return 0;

        return weightedSum / sumWeights;
    }

    private double computeSugenoFunction(Map<String,String> cons, Map<String,Double> inputs) {

        String expression = cons.values().iterator().next().trim();

        // Case 1: constant output
        if (!expression.contains("*"))
            return Double.parseDouble(expression);

        // Case 2: linear expression
        double result = 0;

        String[] terms = expression.split("\\+");
        for (String t : terms) {
            t = t.trim();
            if (t.contains("*")) {
                String[] parts = t.split("\\*");
                double coeff = Double.parseDouble(parts[0].trim());
                String var = parts[1].trim();
                result += coeff * inputs.get(var);
            } else {
                result += Double.parseDouble(t.trim());
            }
        }

        return result;
    }
}
