package Fuzzy.Rules;

import Fuzzy.Validation.RuleValidator;
import Fuzzy.Validation.ValidationException;

import java.util.List;
import java.util.Map;

public class RuleEditor {

    private final RuleBase ruleBase;
    private final RuleValidator validator;

    public RuleEditor(RuleBase ruleBase, RuleValidator validator) {
        this.ruleBase = ruleBase;
        this.validator = validator;
    }

    public Rule create(Map<String, String> antecedent,
                       Map<String, String> consequent,
                       double weight) {
        Rule draft = new Rule(0, antecedent, consequent);
        draft.weight = weight;
        validator.validateRule(draft);
        return ruleBase.createRule(draft);
    }

    public Rule update(int id,
                       Map<String, String> antecedent,
                       Map<String, String> consequent,
                       double weight,
                       boolean enabled) {
        Rule rule = ruleBase.findRule(id)
                .orElseThrow(() -> new ValidationException("Rule " + id + " not found"));
        rule.antecedent = antecedent;
        rule.consequent = consequent;
        rule.weight = weight;
        rule.enabled = enabled;
        validator.validateRule(rule);
        return rule;
    }

    public void setWeight(int id, double weight) {
        validator.validateWeight(weight);
        Rule rule = ruleBase.findRule(id)
                .orElseThrow(() -> new ValidationException("Rule " + id + " not found"));
        rule.weight = weight;
    }

    public void enable(int id, boolean flag) {
        Rule rule = ruleBase.findRule(id)
                .orElseThrow(() -> new ValidationException("Rule " + id + " not found"));
        rule.enabled = flag;
    }

    public void delete(int id) {
        ruleBase.removeRule(id);
    }

    public List<Rule> list() {
        return ruleBase.listRules();
    }
}