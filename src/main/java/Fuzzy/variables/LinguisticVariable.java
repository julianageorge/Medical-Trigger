package Fuzzy.variables;

import java.util.HashMap;
import java.util.Map;

public class LinguisticVariable {
    private String name;
    private double mn, mx;
    private Map<String, FuzzySet> Fuzzysets = new HashMap<>();


    public LinguisticVariable(String name, double mn, double mx) {
        if (mn >= mx) {
            throw new IllegalArgumentException(
                    "Invalid range: minRange must be < maxRange. Got: [" + mn + ", " + mx + "]");
        }
        if (Double.isNaN(mn) || Double.isInfinite(mx) ||
                Double.isNaN(mn) || Double.isInfinite(mx)) {
            throw new IllegalArgumentException("Range values must be finite numbers");
        }
        this.name = name;
        this.mn = mn;
        this.mx = mx;
    }


    public void addFuzzySet(FuzzySet set) {
        Fuzzysets.put(set.getName(), set);
    }


    public Map<String, FuzzySet> getSets() {
        return Fuzzysets;
    }

    public double getMin() {
        return mn;
    }

    public double getMax() {
        return mx;
    }

    public String getName() {
        return name;
    }

    public void fuzzify(double crispValue) {
        System.out.println("\nFuzzifying " + name + " = " + crispValue);
        for (FuzzySet fs : Fuzzysets.values()) {
            double membership = fs.getMembership(crispValue);
            System.out.printf("  %s: %.3f\n", fs.getName(), membership);
        }
    }

    public FuzzySet getFuzzySet(String setName) {
        return Fuzzysets.get(setName);
    }

    public boolean hasFuzzySet(String setName) {
        return Fuzzysets.containsKey(setName);
    }
}