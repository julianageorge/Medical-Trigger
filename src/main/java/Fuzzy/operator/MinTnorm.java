package Fuzzy.operator;

public class MinTnorm implements  TNorm{
    @Override
   public double and(double a ,double b){
        return Math.min(a,b);
    }
}
