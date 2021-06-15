package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.sleep;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

class ArrayReader<T> extends RecursiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6824918032836548869L;
	private static final Object lock = new Object();
	private T[] array;
	private int low, high;
	private static final int MAX = 5;

	public ArrayReader(T[] array, int low, int high) {
		this.array = array;
		this.low = low;
		this.high = high;
	}

	protected void compute() {
		if (high - low <= MAX) {// This if statement is the part where the divided job is executed.
			sleep(1000);
			synchronized (lock) {// If we did not include this synchronisation then the writings would be mixed.
				System.out.printf("Writing %d-%d: ", low, high);
				for (int i = low; i < high; i++)
					System.out.print(array[i] + " ");
				System.out.printf("\n%d-%d finished\n\n", low, high);
			}
			return;
		}

		// This part is responsible for job division
		int mid = (low + high) / 2;
		ArrayReader<T> reader1 = new ArrayReader<>(array, low, mid);
		ArrayReader<T> reader2 = new ArrayReader<>(array, mid, high);
		reader1.fork();
		reader2.compute();
		reader1.join();
	}
}

public class RecursiveActionSample {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		LocalTime start = LocalTime.now();
		Integer[] array = IntStream.range(0, 20).boxed().toArray(x -> new Integer[20]);
		pool.invoke(new ArrayReader<>(array, 0, array.length));
		Duration duration = Duration.between(start, LocalTime.now());
		System.out.printf("Time elapsed is %02d:%03d", duration.getSeconds(), duration.getNano() / 1000000);
	}

}
