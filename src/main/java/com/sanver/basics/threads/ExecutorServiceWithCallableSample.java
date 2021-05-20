package com.sanver.basics.threads;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceWithCallableSample {

	class Increment<T> implements Callable<T> {
		T value;

		Increment(T value) {
			this.value = value;
		}

		@Override
		public T call() throws Exception {

			if (!int[].class.isAssignableFrom(value.getClass()))
				return value;

			int[] result = (int[]) value;

			sleep(3000);// This is put to show that Future.get() method waits for the call method to finish

			for (int i = 0; i < 10000; i++, result[0]++)
				;

			return value;
		}
	}

	public static void main(String[] args) {
		int[] value = new int[1];
		ExecutorServiceWithCallableSample sample = new ExecutorServiceWithCallableSample();
		ExecutorService service = Executors.newFixedThreadPool(10);
		Future<int[]> future = service.submit(sample.new Increment<>(value));
		
		try {
			System.out.println(future.get()[0]);
			System.out.println(value[0]);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		service.shutdown();
	}
}
