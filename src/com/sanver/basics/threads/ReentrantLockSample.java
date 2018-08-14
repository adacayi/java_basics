package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSample {

	static int value = 0;

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();

		List<Integer> list = new ArrayList<>();
		Thread read = new Thread(() -> {
			while (true) {
				lock.lock();

				try { // use a try and finally block in which the lock is released, so if there is an
						// exception meanwhile the lock will be released.
					if (list.size() == 0)
						try {
							condition.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					System.out.println(list.remove(0) + " removed");
					condition.signalAll();
				} finally {
					lock.unlock();
				}
			}
		});
		Thread write = new Thread(() -> {
			while (true) {
				lock.lock();

				try {
					if (list.size() == 1)
						try {
							condition.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					value++;
					list.add(value);
					System.out.println(value + " added");
					condition.signalAll(); // The other thread does not regain the lock until this lock is released by
											// the
											// current thread.

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} finally {
					lock.unlock();
				}
			}
		});

		read.start();
		write.start();
	}
}
