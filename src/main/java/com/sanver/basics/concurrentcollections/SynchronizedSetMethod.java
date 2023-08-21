package com.sanver.basics.concurrentcollections;

import java.util.*;

public class SynchronizedSetMethod {
    public static void main(String[] args) {
        Set<String> set = Collections.synchronizedSet(new HashSet<>());// Use new HashSet<>() to see the difference.
		/*
		Returns a synchronized (thread-safe) set backed by the specified set.
		In order to guarantee serial access, it is critical that all access to the backing set is accomplished
		through the returned set.

		It is imperative that the user manually synchronize on the returned set when iterating over it:
		Set s = Collections.synchronizedSet(new HashSet());
            ...
		synchronized (s) {
			Iterator i = s.iterator(); // Must be in the synchronized block
			while (i.hasNext())
				foo(i.next());
		}

		Failure to follow this advice may result in non-deterministic behavior.
		The returned set will be serializable if the specified set is serializable.
        */

        int count = 100_000;

        Runnable append = () -> {
            for (int i = 0; i < count; i++) {
                set.add(Integer.toString(i));
            }
        };

        Runnable secondAppend = () -> {
            for (int i = count; i < 2*count; i++) {
                set.add(Integer.toString(i));
            }
        };

        Thread t1 = new Thread(append);
        Thread t2 = new Thread(secondAppend);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished. Set size= " + set.size());// Since the new set is synchronised the size
        // is 2*count. If we used HashSet instead the count will change depending on the run.
    }
}
