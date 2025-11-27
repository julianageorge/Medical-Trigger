package Fuzzy.fuzzifier;

import Fuzzy.Validation.ValidationException;
import Fuzzy.variables.FuzzySet;
import Fuzzy.variables.LinguisticVariable;

import java.util.HashMap;
import java.util.Map;

public class Fuzzifier {
    private Map<String, LinguisticVariable> variables;


    public Fuzzifier(Map<String, LinguisticVariable> variables) {
        this.variables = variables;
    }

    public Map<String, Map<String, Double>> fuzzify(Map<String, Double> crispInputs) {

        Map<String, Map<String, Double>> fuzzified = new HashMap<>();

        for (Map.Entry<String, Double> entry : crispInputs.entrySet()) {
            String varName = entry.getKey();
            double value = entry.getValue();

            LinguisticVariable lv = variables.get(varName);
            if (lv == null) {
                continue;
            }

            Map<String, Double> memberships = new HashMap<>();
            for (FuzzySet fs : lv.getSets().values()) {
                memberships.put(fs.getName(), fs.getMembership(value));
            }

            fuzzified.put(varName, memberships);
        }

        return fuzzified;
    }

    //  NOUR

    public Map<String, Map<String, Double>> fuzzifyWithErrorHandling(Map<String, Double> crispInputs) {
        if (crispInputs == null || crispInputs.isEmpty()) {
            throw new ValidationException("Crisp input map is empty");
        }

        Map<String, Map<String, Double>> fuzzified = new HashMap<>();

        for (Map.Entry<String, LinguisticVariable> entry : variables.entrySet()) {
            String varName = entry.getKey();
            LinguisticVariable lv = entry.getValue();

            Double rawValue = crispInputs.get(varName);
            if (rawValue == null) {
                throw new ValidationException("Missing crisp input for variable " + varName);
            }

            double clampedValue = clamp(rawValue, lv.getMin(), lv.getMax());

            Map<String, Double> memberships = new HashMap<>();
            for (FuzzySet fs : lv.getSets().values()) {
                memberships.put(fs.getName(), fs.getMembership(clampedValue));
            }

            fuzzified.put(varName, memberships);
        }

        return fuzzified;
    }

    private double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
    //  NOUR
}
