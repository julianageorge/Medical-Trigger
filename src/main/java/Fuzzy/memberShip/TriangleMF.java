package Fuzzy.memberShip;

public class TriangleMF implements IMembershipFunction{
    private double a,b,c;
    public TriangleMF(double a,double b, double c){
        this.a=a;
        this.b=b;
        this.c=c;
    }

    private void validateParameters(double a, double b, double c) {
        if (!(a <= b && b <= c)) {
            throw new IllegalArgumentException(
                    "Triangular MF Must: a <= b <= c. Got: a=" + a + ", b=" + b + ", c=" + c);
        }
        if (a == c) {
            throw new IllegalArgumentException(
                    "Triangular MF Must: a < c");
        }
    }

    @Override
    public double membership(double x) {
        if (x <= a || x >= c) {
            return 0;
        }
        else if (x == b) {
            return 1;
        }
        else if (x > a && x < b){
            return (x - a) / (b - a);
        }
        else {
            return (c - x) / (c - b);
        }
    }
    public double[] getParameters() {
        return new double[]{a,b,c};
    }
    public String getType() {
        return "triangle";
    }

    @Override
    public boolean isValid() {
        return a <= b && b <= c && a < c;
    }
}
