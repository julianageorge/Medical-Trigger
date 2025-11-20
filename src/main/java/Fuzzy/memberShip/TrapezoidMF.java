package Fuzzy.memberShip;

public class TrapezoidMF implements IMembershipFunction {
    private double a, b, c, d;


    public TrapezoidMF(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }


    @Override
    public double membership(double x) {
        return 0;
    }

    @Override
    public double[] getParameters() {
        return new double[]{a,b,c,d};
    }

    @Override
    public String getType() {
        return "trapezoid";
    }
}