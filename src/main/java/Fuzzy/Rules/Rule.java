package Fuzzy.Rules;

import java.util.HashMap;
import java.util.Map;

public class Rule {
    public int id;
    public boolean enabled = true;
    public double weight = 1.0;


    public Map<String, String> antecedent = new HashMap<>();
    public Map<String, String> consequent = new HashMap<>();
}
