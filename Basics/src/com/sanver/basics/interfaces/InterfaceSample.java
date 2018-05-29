package com.sanver.basics.interfaces;

interface Payable {
	double MAX_RATE = 0.78; // This is automatically public, static and final. Another access modifier is
							// not permitted.

	void increaseRate(double increaseRate);

	default double getMaxRate() {
		return MAX_RATE;
	}

	static boolean isIncreaseRateValid(double increaseRate) {
		return increaseRate <= MAX_RATE;
	}

	double getRate();
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
