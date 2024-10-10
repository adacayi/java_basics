package com.sanver.basics.concurrentcollections;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * 		 Returns a synchronized (thread-safe) list backed by the specified list. In
 * 		 order to guarantee serial access, it is critical that all access to the
 * 		 backing list is accomplished through the returned list.
 *		 <br>
 * 		 It is imperative that the user manually synchronize on the returned list when
 * 		 iterating over it:
 * 		 <br><br>
 *
 <pre>
 <code>List list = Collections.synchronizedList(new ArrayList());
 synchronized (list) {
   Iterator i = list.iterator(); // Must be in synchronized block
   while (i.hasNext()) {
	foo(i.next());
   }
 }
 </code>
 </pre>
 * 		 <br><br>
 * 		 Failure to follow this advice may result in non-deterministic behavior.
 * 		 The returned list will be serializable if the specified list is serializable.
 */
public class SynchronizedListMethod {

	public static void main(String[] args) {
		List<String> list = Collections.synchronizedList(new ArrayList<>());

		int count = 100_000;
		var format = NumberFormat.getInstance();

		Runnable append = () -> {
			for (int i = 0; i < count; i++)
				list.add("a");
		};

		var futures = IntStream.range(0, 10).mapToObj(x -> CompletableFuture.runAsync(append)).toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(futures).join();

		System.out.println("Finished. List size = " + format.format(list.size())); // Use ArrayList instead to observe different sizes.
	}
}
