package Fuzzy.memberShip;

public class TriangleMF implements IMembershipFunction{
    private double a,b,c;
    public TriangleMF(double a,double b, double c){
        this.a=a;
        this.b=b;
        this.c=c;
    }

    @Override
    public double membership(double x) {
        return 0;
    }
    public double[] getParameters() {
        return new double[]{a,b,c};
    }
    public String getType() {
        return "triangle"; }
}
