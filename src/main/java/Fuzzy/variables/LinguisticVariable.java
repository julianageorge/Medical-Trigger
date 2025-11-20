package Fuzzy.variables;

import java.util.HashMap;
import java.util.Map;

public class LinguisticVariable {
    private String name;
    private double mn, mx;
    private Map<String, FuzzySet> sets = new HashMap<>();


    public LinguisticVariable(String name, double mn, double mx) {
        this.name = name;
        this.mn = mn;
        this.mx = mx;
    }


    public void addFuzzySet(FuzzySet set) {
        sets.put(set.getName(), set);
    }


    public Map<String, FuzzySet> getSets() { return sets; }
    public double getMin() {
        return mn;
    }
    public double getMax() {
        return mx;
    }
    public String getName() {
        return name;
    }
}
