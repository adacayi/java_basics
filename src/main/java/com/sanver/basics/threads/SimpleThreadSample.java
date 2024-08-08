package com.sanver.basics.threads;

public class SimpleThreadSample {

	private static int value = 0;

	static class Increment extends Thread {
		int incrementValue;

		public Increment(int incrementValue) {
			this.incrementValue = incrementValue;
		}

		@Override
		public void run() {
			for (int i = 0; i < incrementValue; i++){
				value++;
			}

			System.out.println("Finished");
		}
	}

	public static void main(String[] args) {
		int incrementValue = 10_000;
		Increment firstOperation = new Increment(incrementValue);
		Increment secondOperation = new Increment(incrementValue);
		firstOperation.start();
		secondOperation.start();

		try {
			firstOperation.join();
			secondOperation.join();
			System.out.println("Value is " + value);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
