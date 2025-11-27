package Fuzzy.operator;

public class MaxSNorm implements  SNorm{
    @Override
    public double or(double a, double b) {
        return Math.max(a, b);
    }
}
