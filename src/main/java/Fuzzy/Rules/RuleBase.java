package Fuzzy.Rules;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {
    private List<Rule> rules = new ArrayList<>();


    public void addRule(Rule r) {
        rules.add(r);
    }
    public List<Rule> getRules() {
        return rules;
    }
}
