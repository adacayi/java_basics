package com.sanver.basics.threads;

public class SimpleThreadSample {

	class Increment extends Thread {
		int[] value;
		int incrementValue;

		public Increment(int[] value, int incrementValue) {
			this.value = value;
			this.incrementValue = incrementValue;
		}

		public void run() {
			for (int i = 0; i < incrementValue; i++, value[0]++)
				;
			System.out.println("Finished");
		}
	}

	public static void main(String[] args) {
		int[] value = { 0 };// We are using an integer array with one element to pass a single value as
							// reference to another class. We cannot pass primitive values as reference in
							// Java.
		int incrementValue = 10000;
		SimpleThreadSample sample = new SimpleThreadSample();
		Increment firstOperation = sample.new Increment(value, incrementValue);
		Increment secondOperation = sample.new Increment(value, incrementValue);
		firstOperation.start();
		secondOperation.start();
		try {
			firstOperation.join();
			secondOperation.join();
			System.out.println("Value is " + value[0]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
