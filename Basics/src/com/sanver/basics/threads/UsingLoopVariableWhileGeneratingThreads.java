package com.sanver.basics.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UsingLoopVariableWhileGeneratingThreads {

	public static void main(String[] args) {
		int poolSize = 4, operationCount = 16;
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		Future<?>[] taskArray = IntStream.rangeClosed(1, operationCount).mapToObj(x -> service.submit(() -> {
			System.out.println("Task " + x + " started.");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Task " + x + " finished.");
		})).collect(Collectors.toList()).toArray(new Future<?>[operationCount]);

		for (Future<?> task : taskArray) {
			try {
				task.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		service.shutdown();
	}

}
