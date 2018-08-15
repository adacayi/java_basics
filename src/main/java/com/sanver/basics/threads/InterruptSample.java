package com.sanver.basics.threads;

public class InterruptSample {

	public static void main(String[] args) {
		Thread waitingThread = new Thread(() -> {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted.");
				System.out.println("isInterruptedValue = " + Thread.currentThread().isInterrupted());// If thread is
																									// interrupted,
																									// sleep will throw
																									// an
																									// InterruptedException
																									// and clear the
																									// interrupted flag.
																									// Hence the value
																									// is false.
				Thread.currentThread().interrupt();
				System.out.println("isInterruptedValue = " + Thread.currentThread().isInterrupted());
			}
		});
		waitingThread.start();
		System.out.println("Thread started");
		try {
			Thread.sleep(2000);
			waitingThread.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
