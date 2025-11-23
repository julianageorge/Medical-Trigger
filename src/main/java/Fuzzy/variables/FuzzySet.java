package Fuzzy.variables;

import Fuzzy.memberShip.IMembershipFunction;

public class FuzzySet {
        private String name;
        private IMembershipFunction mf;


        public FuzzySet(String name, IMembershipFunction mf) {
            this.name = name;
            this.mf = mf;
        }
    public double getMembership(double value) {
        return mf.membership(value);
    }
        public String getName() {
            return name;
        }
        public IMembershipFunction getMF() {
            return mf;
        }
}
