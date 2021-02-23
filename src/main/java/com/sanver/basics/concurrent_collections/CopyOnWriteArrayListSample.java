package com.sanver.basics.concurrent_collections;

import java.util.concurrent.CopyOnWriteArrayList;

// https://www.baeldung.com/java-copy-on-write-arraylist
// This is a thread safe list, which multiple threads can access in the same time,
// which cannot be done with synchronized list, where only one thread can access it at a time.
// This is achieved by putting locks on different segments.
// Also, the design of the CopyOnWriteArrayList uses an interesting technique to make it thread-safe without a need
// for synchronization. When we are using any of the modify methods – such as add() or remove() – the whole content
// of the CopyOnWriteArrayList is copied into the new internal copy.
// Due to this simple fact, we can iterate over the list in a safe way, even when concurrent modification is happening.
// This makes this more performant on cases where we do less modifying and more reading.
public class CopyOnWriteArrayListSample {

  public static void main(String[] args) {
    var list = new CopyOnWriteArrayList<String>();
    int count = 100000;

    Runnable append = () -> {
      for (int i = 0; i < count; i++) {
        list.add("a");
      }
    };

    Runnable remove = () -> {
      for (int i = 0; i < count; ) {
        if (!list.isEmpty()) {
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

    System.out.println("Finished. List size = " + list.size());// Since the new list is thread-safe the size
    // is 0. If we used ArrayList instead the
    // code would not end always.
  }
}
