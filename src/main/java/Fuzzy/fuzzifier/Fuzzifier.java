package Fuzzy.fuzzifier;

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
}
