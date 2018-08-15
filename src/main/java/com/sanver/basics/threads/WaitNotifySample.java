package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;

public class WaitNotifySample {

	static int value = 0;

	public static void main(String[] args) {
		Object lock = new Object();

		List<Integer> list = new ArrayList<>();
		Thread read = new Thread(() -> {
			while (true) {
				synchronized (lock) {
					if (list.size() == 0)
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					System.out.println(list.remove(0) + " removed");
					lock.notifyAll();
				}
			}
		});

		Thread write = new Thread(() -> {
			while (true) {
				synchronized (lock) {
					if (list.size() == 1)
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					value++;
					list.add(value);
					System.out.println(value + " added");
					lock.notifyAll(); // The other thread does not regain the lock until this lock is released by the
										// current thread.

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		read.start();
		write.start();
	}
}
