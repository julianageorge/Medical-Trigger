package Fuzzy.inference;

import Fuzzy.Rules.Rule;
import Fuzzy.Rules.RuleBase;
import Fuzzy.variables.FuzzySet;
import Fuzzy.variables.LinguisticVariable;
import Fuzzy.operator.TNorm;
import Fuzzy.operator.SNorm;

import java.util.Map;

public class MamdaniEngine implements InferenceEngine {

    private Map<String, LinguisticVariable> variables;
    private TNorm andOperator;
    private SNorm orOperator;

    public MamdaniEngine(Map<String, LinguisticVariable> variables,
                         TNorm andOp, SNorm orOp) {
        this.variables = variables;
        this.andOperator = andOp;
        this.orOperator = orOp;
    }

    @Override
    public double evaluate(Map<String, Double> inputs, RuleBase base) {

        String outputVarName = getOutputVariableName(base);
        LinguisticVariable outputVar = variables.get(outputVarName);

        int samples = 200;
        double step = (outputVar.getMax() - outputVar.getMin()) / samples;

        double[] aggregated = new double[samples + 1];

        // ---- Rule Processing ----
        for (Rule rule : base.getRules()) {

            if (!rule.enabled) continue;

            double firingStrength = 1.0;

            for (var e : rule.antecedent.entrySet()) {

                String varName = e.getKey();
                String setName = e.getValue();

                LinguisticVariable var = variables.get(varName);
                FuzzySet set = var.getFuzzySet(setName);

                double crispValue = inputs.get(varName);
                double μ = set.getMembership(crispValue);

                firingStrength = andOperator.and(firingStrength, μ);
            }

            firingStrength *= rule.weight;

            String outputSetName = rule.consequent.values().iterator().next();
            FuzzySet outputSet = outputVar.getFuzzySet(outputSetName);

            // Apply implication (clipping)
            for (int i = 0; i <= samples; i++) {

                double x = outputVar.getMin() + i * step;
                double μ = outputSet.getMembership(x);

                double clipped = Math.min(μ, firingStrength);

                aggregated[i] = orOperator.or(aggregated[i], clipped);
            }
        }

        // ---- Defuzzification (Centroid) ----
        double num = 0, den = 0;
        for (int i = 0; i <= samples; i++) {

            double x = outputVar.getMin() + i * step;
            num += x * aggregated[i];
            den +=     aggregated[i];
        }

        if (den == 0) return 0;

        return num / den;
    }


    private String getOutputVariableName(RuleBase base) {
        Rule r = base.getRules().get(0);
        return r.consequent.keySet().iterator().next();
    }
}
