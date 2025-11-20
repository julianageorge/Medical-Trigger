package Fuzzy.memberShip;

public interface IMembershipFunction {
    double membership(double x);
    double[] getParameters();
    String getType();
}
