package Fuzzy.Rules;

import Fuzzy.operator.SNorm;
import Fuzzy.operator.TNorm;

import java.util.HashMap;
import java.util.Map;

public class Rule {
    public int id;
    public boolean enabled = true;
    public double weight = 1.0;


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
