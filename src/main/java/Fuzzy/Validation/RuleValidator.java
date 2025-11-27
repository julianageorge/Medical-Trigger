package Fuzzy.Validation;

import Fuzzy.Rules.Rule;
import Fuzzy.variables.LinguisticVariable;

import java.util.Map;

public class RuleValidator {

    private final Map<String, LinguisticVariable> variables;

    public RuleValidator(Map<String, LinguisticVariable> variables) {
        this.variables = variables;
    }

    public void validateRule(Rule rule) {
        validateWeight(rule.weight);
        if (rule.antecedent == null || rule.antecedent.isEmpty()) {
            throw new ValidationException("Antecedent must contain at least one clause");
        }
        for (Map.Entry<String, String> clause : rule.antecedent.entrySet()) {
            LinguisticVariable var = getRequiredVariable(clause.getKey());
            getRequiredSet(var, clause.getValue());
        }
        if (rule.consequent == null || rule.consequent.size() != 1) {
            throw new ValidationException("Consequent must contain exactly one clause");
        }
        Map.Entry<String, String> cons = rule.consequent.entrySet().iterator().next();
        LinguisticVariable output = getRequiredVariable(cons.getKey());
        getRequiredSet(output, cons.getValue());
    }

    public void validateWeight(double weight) {
        if (weight <= 0 || weight > 1) {
            throw new ValidationException("Weight must be in (0,1]");
        }
    }

    private LinguisticVariable getRequiredVariable(String name) {
        LinguisticVariable var = variables.get(name);
        if (var == null) {
            throw new ValidationException("Unknown variable " + name);
        }
        return var;
    }

    private void getRequiredSet(LinguisticVariable variable, String setName) {
        if (variable.getFuzzySet(setName) == null) {
            throw new ValidationException(
                    "Variable " + variable.getName() + " missing fuzzy set " + setName);
        }
    }
}
