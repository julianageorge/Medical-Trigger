package Fuzzy.Rules;

import Fuzzy.operator.TNorm;
import Fuzzy.operator.SNorm;
import Fuzzy.variables.LinguisticVariable;
import Fuzzy.variables.FuzzySet;

import java.util.Map;

public class Rule {

    public int id;
    public boolean enabled = true;
    public double weight = 1.0;

    public Map<String, String> antecedent;
    public Map<String, String> consequent;

    public Rule(int id, Map<String, String> antecedent, Map<String, String> consequent) {
        this.id = id;
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    public double evaluateAntecedent(Map<String, Double> inputs,
                                     Map<String, LinguisticVariable> variables,
                                     TNorm tnorm, SNorm snorm) {

        double result = -1;

        for (String varName : antecedent.keySet()) {

            double crispValue = inputs.get(varName);
            String setName = antecedent.get(varName);

            LinguisticVariable lv = variables.get(varName);
            FuzzySet fs = lv.getFuzzySet(setName);

            double membership = fs.getMembership(crispValue);

            if (result < 0) {
                result = membership;
            } else {
                result = tnorm.and(result, membership);
            }
        }

        return result * weight;
    }

    public FuzzySet getOutputSet(Map<String, LinguisticVariable> variables) {

        String varName = consequent.keySet().iterator().next();
        String setName = consequent.values().iterator().next();

        return variables.get(varName).getFuzzySet(setName);
    }

    public double evaluateConsequent(Map<String, Double> inputs) {

        // You can extend this for polynomials but assume single constant value

        String val = consequent.values().iterator().next();

        try {

            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            // Sum of inputs:
            // THEN out = HR + Temp
            return inputs.values().stream().mapToDouble(d -> d).sum();
        }
    }
}
