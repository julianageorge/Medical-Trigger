package Fuzzy.operator;

public class SumSNorm implements SNorm{
        @Override
        public double or(double a, double b) {
            return a + b - (a * b);
        }
}

