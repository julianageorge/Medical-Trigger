package Fuzzy.memberShip;

public class GaussianMF implements IMembershipFunction{
    private double mean, stdv;


    public GaussianMF(double mean, double stdv) {
        this.mean = mean;
        this.stdv = stdv;
    }
    private void validateParameters(double mean, double stdv) {
        if (stdv <= 0) {
            throw new IllegalArgumentException(
                    "Gaussian MF Must: sigma > 0. Got: sigma=" + stdv);
        }
        if (Double.isNaN(mean) || Double.isInfinite(mean)) {
            throw new IllegalArgumentException(
                    "Gaussian MF Must: finite mean. Got: mean=" + mean);
        }
    }


    public double membership(double x) {
        return Math.exp(-0.5 * Math.pow((x - mean) / stdv, 2));
    }


    public double[] getParameters() {
        return new double[]{mean, stdv};
    }
    public String getType() {
        return "gaussian";
    }

    @Override
    public boolean isValid() {
        return stdv > 0 && !Double.isNaN(mean) && !Double.isInfinite(mean);
    }
}
