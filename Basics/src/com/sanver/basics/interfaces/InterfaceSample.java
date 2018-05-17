package com.sanver.basics.interfaces;

interface Payable {
	double MAX_RATE = 0.78;

	void increaseRate(double increaseRate);

	default void getMaxRate() {
		System.out.println(MAX_RATE);
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
		if (increaseRate < MAX_RATE)
			rate *= 1 + increaseRate;
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
		System.out.println(
				Payable.isIncreaseRateValid(increaseRate) ? "Increase rate is valid" : "Increase rate is not valid");
		pay.increaseRate(0.3);
		System.out.println(pay.getRate());
		pay.getMaxRate();
	}

}
