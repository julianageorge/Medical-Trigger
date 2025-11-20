package Fuzzy.Rules;

public class RuleEditor {
    public void setWeight(Rule r, double w) {
        r.weight = w;
    }
    public void enable(Rule r, boolean flag) {
        r.enabled = flag;
    }
}
