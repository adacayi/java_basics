package com.sanver.basics.interfaces;

interface Payable {
    double MAX_RATE = 0.78; // This is automatically public, static and final. Another access modifier is
    // not permitted.

    static boolean isIncreaseRateValid(double increaseRate) {
        return increaseRate <= MAX_RATE;
    } // This is public by default, but can be made private as well. Protected is not allowed and package private is not possible, since the default accessibility without the modifier is public.

    void increaseRate(double increaseRate); // This is automatically public. We cannot have method declarations (methods that don't have any bodies) with private, protected or package-private access. We can have private methods though. No protected or package-private methods are allowed.

    // protected void increase(); // This won't compile, because only public is allowed for method declarations.
    // protected void increase(){} // This won't compile. Only public and private are allowed for method definitions and if the method definition is public, it must use default or static.
    // public void increase(){} // This won't compile as well, since it is a public method definition (method with body), it should either be a default method or a static one.

    default double getMaxRate() {
        return MAX_RATE;
    }

    double getRate();

    interface SomeInterface { // This is public and static, but not final, and cannot be made protected, package private or private.

    }

    class SomeClass { // This is public and static, but not final (but can be made final) and cannot be made protected, package private or private.

    }

    abstract class SomeAbstractClass { // This is public and static, but not final, and cannot be made protected, package private or private.

    }
}

class Pay implements Payable {

    private double rate = 0.2;

    @Override
    public void increaseRate(double increaseRate) {
        if (Payable.isIncreaseRateValid(increaseRate))
            rate += increaseRate;
    }

    @Override
    public double getRate() {
        return rate;
    }
}

public class InterfaceSample {

    public static void main(String[] args) {
        Pay pay = new Pay();
        double increaseRate = 0.3;
        System.out.println(increaseRate + " " + (Payable.isIncreaseRateValid(increaseRate) ? "Increase rate is valid"
                : "Increase rate is not valid"));
        System.out.println("Original rate is " + pay.getRate());
        pay.increaseRate(0.3);
        System.out.println("Increased rate is " + pay.getRate());
        System.out.println("Max rate is " + pay.getMaxRate());
    }

}
