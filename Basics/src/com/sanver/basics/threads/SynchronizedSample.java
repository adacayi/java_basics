package com.sanver.basics.threads;

public class SynchronizedSample {

	static int value;

	public static void main(String[] args) {
		int limit = 10000;
		// limit++; // If we wrote a statement like that we would get an error in the
		// increment lambda expression
		// "Local
		// variable limit defined in an enclosing scope must be final or effectively
		// final" on the line where we used increment in the inner function. So
		// limit must
		// behave as a final variable at least or be final.
		Object lock = new Object();
		Runnable increment = () -> {
			for (int i = 0; i < limit; i++) {// limit can be accessed from inner function. But it must behave as
												// if it was a final variable. We cannot change its value.
				synchronized (lock) {
					value++;
				}
			}
		};
		Thread first = new Thread(increment);
		Thread second = new Thread(increment);
		first.start();
		second.start();
		try {
			first.join();
			second.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(value);
	}
}
