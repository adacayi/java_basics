package com.sanver.basics.threads;

import static com.sanver.basics.utils.ThreadUtils.sleep;

public class ThreadWithLambda {

	public static void main(String[] args) {
		Thread first = new Thread(() -> {
			sleep(1000);
			System.out.println("Selamunaleykum");
		});
		Thread second = new Thread(() -> System.out.println("Waiting for your response"));
		Runnable third = () -> {
			sleep(3000);
			System.out.println("Ve aleykumselam.. with runnable interface");
		};

		new Thread(third).start();
		first.start();
		second.start();
	}
}
