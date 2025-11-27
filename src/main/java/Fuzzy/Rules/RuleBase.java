package Fuzzy.Rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class RuleBase {
    private final List<Rule> rules = new ArrayList<>();
    private int nextId = 1;

    public Rule createRule(Rule rule) {
        if (rule.id <= 0) {
            rule.id = nextId++;
        }
        rules.add(rule);
        return rule;
    }

    public Optional<Rule> findRule(int id) {
        return rules.stream().filter(r -> r.id == id).findFirst();
    }

    public List<Rule> listRules() {
        return new ArrayList<>(rules);
    }

    public void removeRule(int id) {
        Iterator<Rule> it = rules.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                break;
            }
        }
    }
    public List<Rule> getRules() {
        return rules;
    }
}