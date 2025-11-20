package Fuzzy.inference;

import Fuzzy.Rules.RuleBase;

import java.util.Map;

public interface InferenceEngine {
    double evaluate(Map<String, Double> inputs, RuleBase base);
}
