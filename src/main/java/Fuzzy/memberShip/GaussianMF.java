package Fuzzy.memberShip;

public class GaussianMF implements IMembershipFunction{
    private double mean, stdv;


    public GaussianMF(double mean, double stdv) {
        this.mean = mean;
        this.stdv = stdv;
    }


    public double membership(double x) {
        return Math.exp(-0.5 * Math.pow((x - mean) / stdv, 2));
    }


    public double[] getParameters() { return new double[]{mean, stdv}; }
    public String getType() { return "gaussian"; }
}
