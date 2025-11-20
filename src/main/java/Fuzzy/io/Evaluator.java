package Fuzzy.io;

import Fuzzy.Rules.RuleBase;
import Fuzzy.inference.InferenceEngine;

import java.util.Map;

public class Evaluator {
    private InferenceEngine engine;
    private RuleBase ruleBase;


    public Evaluator(InferenceEngine engine, RuleBase ruleBase) {
        this.engine = engine;
        this.ruleBase = ruleBase;
    }


    public double evaluate(Map<String, Double> inputs) {
        return engine.evaluate(inputs, ruleBase);
    }
}
