package Fuzzy.io;

import Fuzzy.Defuzzy.DefuzzificationMethod;
import Fuzzy.Rules.RuleBase;
import Fuzzy.fuzzifier.Fuzzifier;
import Fuzzy.inference.InferenceEngine;
import Fuzzy.operator.SNorm;
import Fuzzy.operator.TNorm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluator {
    private Fuzzifier fuzzifier;
    private InferenceEngine engine;
    private RuleBase ruleBase;

    private DefuzzificationMethod defuzzifier;
    private TNorm tnorm;
    private SNorm snorm;


    private Map<String, Map<String, Double>> fuzzifiedInputs;
    private List<Map<String, Double>> ruleOutputs;
    private Map<Double, Double> aggregatedOutput;


    public Evaluator(Fuzzifier fuzzifier,
                     InferenceEngine engine,
                     RuleBase ruleBase,
                     DefuzzificationMethod defuzzifier,
                     TNorm tnorm,
                     SNorm snorm) {
        this.fuzzifier = fuzzifier;
        this.engine = engine;
        this.ruleBase = ruleBase;
        this.defuzzifier = defuzzifier;
        this.tnorm = tnorm;
        this.snorm = snorm;
    }


    public double evaluate(Map<String, Double> crispInputs) {
        fuzzifiedInputs = fuzzifier.fuzzifyWithErrorHandling(crispInputs);
        return engine.evaluate(crispInputs, ruleBase);
    }

    private Map<Double, Double> aggregateOutputs(List<Map<String, Double>> ruleOutputs) {
        Map<Double, Double> aggregated = new HashMap<>();

        for (Map<String, Double> ruleOutput : ruleOutputs) {
            for (Map.Entry<String, Double> entry : ruleOutput.entrySet()) {

                double x = Double.parseDouble(entry.getKey());
                double m = entry.getValue();

                aggregated.putIfAbsent(x, 0.0);

                double oldValue = aggregated.get(x);
                double newValue = snorm.or(oldValue, m);

                aggregated.put(x, newValue);
            }
        }
        return aggregated;
    }

    public Map<String, Map<String, Double>> getFuzzifiedInputs() {
        return fuzzifiedInputs;
    }

    public List<Map<String, Double>> getRuleOutputs() {
        return ruleOutputs;
    }

    public Map<Double, Double> getAggregatedOutput() {
        return aggregatedOutput;
    }

    public void setFuzzifiedInputs(Map<String, Map<String, Double>> data) {
        this.fuzzifiedInputs = data;
    }

    public void setRuleOutputs(List<Map<String, Double>> data) {
        this.ruleOutputs = data;
    }

    public void setAggregatedOutput(Map<Double, Double> data) {
        this.aggregatedOutput = data;
    }
}
