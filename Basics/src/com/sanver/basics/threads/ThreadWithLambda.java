package com.sanver.basics.threads;

public class ThreadWithLambda {

	public static void main(String[] args) {
		Thread first = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Selamunaleykum");
		});
		Thread second = new Thread(() -> System.out.println("Waiting for your response"));
		Runnable third = () -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Ve aleykumselam.. with runnable interface");
		};

		new Thread(third).start();
		first.start();
		second.start();
	}
}
