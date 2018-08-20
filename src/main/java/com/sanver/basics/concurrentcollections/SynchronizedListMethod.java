package com.sanver.basics.concurrentcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListMethod {

	public static void main(String[] args) {
		List<String> list = Collections.synchronizedList(new ArrayList<>());
		// Returns a synchronized (thread-safe) list backed by the specified list. In
		// order to guarantee serial access, it is critical that all access to the
		// backing list is accomplished through the returned list.
		//
		// It is imperative that the user manually synchronize on the returned list when
		// iterating over it:
		//
		// List list = Collections.synchronizedList(new ArrayList());
		// ...
		// synchronized (list) {
		// Iterator i = list.iterator(); // Must be in synchronized block
		// while (i.hasNext())
		// foo(i.next());
		// }
		//
		// Failure to follow this advice may result in non-deterministic behavior.
		// The returned list will be serializable if the specified list is serializable.

		int count = 100000;

		Runnable append = () -> {
			for (int i = 0; i < count; i++)
				list.add("a");
		};

		Runnable remove = () -> {
			for (int i = 0; i < count;) {
				if (list.size() > 0) {
					list.remove(0);
					i++;
				}
			}
		};

		Thread t1 = new Thread(append);
		Thread t2 = new Thread(remove);
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Finished. List size= " + list.size());// Since the new list is synchronised the size
																		// is 0. If we used ArrayList instead the
																		// code would not end always.
	}
}
