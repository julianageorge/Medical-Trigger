package Fuzzy.Rules;
import Fuzzy.operator.TNorm;
import Fuzzy.operator.SNorm;
import Fuzzy.variables.LinguisticVariable;
import Fuzzy.variables.FuzzySet;
import java.util.HashMap;
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

    public Map<String, String> antecedent = new HashMap<>();
    public Map<String, String> consequent = new HashMap<>();

    public double applyImplication(TNorm tnorm, double antecedentStrength, double consequentMembership) {
        if (!enabled) {
            return 0.0;
        }
        double weightedStrength = antecedentStrength * weight;
        return tnorm.and(weightedStrength, consequentMembership);
    }

    public static double aggregate(SNorm snorm, double membership1, double membership2) {
        return snorm.or(membership1, membership2);
    }

    public static double aggregate(SNorm snorm, double... memberships) {
        if (memberships == null || memberships.length == 0) {
            return 0.0;
        }
        if (memberships.length == 1) {
            return memberships[0];
        }
        double result = memberships[0];
        for (int i = 1; i < memberships.length; i++) {
            result = snorm.or(result, memberships[i]);
        }
        return result;
    }
    public double computeAntecedentStrength(TNorm tnorm, double... antecedentMemberships) {
        if (!enabled || antecedentMemberships == null || antecedentMemberships.length == 0) {
            return 0.0;
        }
        if (antecedentMemberships.length == 1) {
            return antecedentMemberships[0] * weight;
        }
        double result = antecedentMemberships[0];
        for (int i = 1; i < antecedentMemberships.length; i++) {
            result = tnorm.and(result, antecedentMemberships[i]);
        }
        return result * weight;

    }
}
