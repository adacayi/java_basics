package com.sanver.basics.threads;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Fibonacci extends RecursiveTask<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8560656118862208134L;
	final int n;

	Fibonacci(int n) {
		this.n = n;
	}

	protected Integer compute() {
		if (n <= 1) {//This if statement is the part where the divided job is executed.
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return n;
		}
		
		// This part is responsible for job division
		System.out.println(n + " started");
		Fibonacci f1 = new Fibonacci(n - 1);
		f1.fork();
		Fibonacci f2 = new Fibonacci(n - 2);
		int result = f2.compute() + f1.join();
		System.out.println(n + " finished with result " + result);
		return result;
	}
}

public class RecursiveTaskSample {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		LocalTime start = LocalTime.now();
		Integer result = pool.invoke(new Fibonacci(4));
		System.out.println("Result is " + result);
		Duration duration = Duration.between(start, LocalTime.now());
		System.out.printf("Time elapsed is %02d:%03d", duration.getSeconds(), duration.getNano() / 1000000);
	}

}
