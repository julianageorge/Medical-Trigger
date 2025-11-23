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
        if (x <= a || x >= d) {
            return 0;
        }
        else if (x >= b && x <= c){
            return 1;
        }
        else if (x > a && x < b){
            return (x - a) / (b - a);
        }
        else{
            return (d - x) / (d - c);
        }
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