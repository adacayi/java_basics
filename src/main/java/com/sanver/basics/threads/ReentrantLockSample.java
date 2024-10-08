package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock class implements the Lock interface. It offers the same concurrency and memory semantics as the implicit monitor lock accessed using synchronized methods and statements, with extended capabilities.
 * <ul>
 * <li>We need to make sure that we are wrapping the lock() and the unlock() calls in the try-finally block to avoid the deadlock situations.
 * <li>A reentrant lock means that a thread can acquire the same lock multiple times. This is also known as reentrancy. The synchronized keyword also allows the same thread to re-enter any synchronized block or method it has already locked without causing a deadlock. So, if a thread that already holds a lock tries to acquire the same lock again, it succeeds.
 * </ul>
 *
 * <h1>Methods</h1>
 * <ul>
 * <li>lock(): Call to the lock() method increments the hold count by 1 and gives the lock to the thread if the shared resource is initially free.</li>
 * <li>unlock(): Call to the unlock() method decrements the hold count by 1. When this count reaches zero, the resource is released.
 * <li>tryLock(): If the resource is not held by any other thread, then call to tryLock() returns true and the hold count is incremented by one. If the resource is not free, then the method returns false, and the thread is not blocked, but exits.</li>
 * <li>tryLock(long timeout, TimeUnit unit): As per the method, the thread waits for a certain time period as defined by arguments of the method to acquire the lock on the resource before exiting.</li>
 * <li>lockInterruptibly(): This method acquires the lock if the resource is free while allowing for the thread to be interrupted by some other thread while acquiring the resource. It means that if the current thread is waiting for the lock but some other thread requests the lock, then the current thread will be interrupted and return immediately without acquiring the lock.</li>
 * <li>getHoldCount(): This method returns the count of the number of locks held on the resource.</li>
 * <li>isHeldByCurrentThread(): This method returns true if the lock on the resource is held by the current thread.</li>
 * <li>hasQueuedThread(): This Method Queries whether the given thread is waiting to acquire this lock.</li>
 * <li>isLocked(): Queries if this lock is held by any thread.</li>
 * <li>newCondition(): Returns a Condition instance for use with this Lock instance.</li>
 * </ul>
 *
 * <a href="https://www.baeldung.com/java-concurrent-locks">Link 1</a>
 * <a href="https://www.geeksforgeeks.org/reentrant-lock-java/">Link 2</a>
 */
public class ReentrantLockSample {
  static int value = 0;

  public static void main(String[] args) {
    Lock lock = new ReentrantLock(true);
    Condition condition = lock.newCondition();

    List<Integer> list = new ArrayList<>();
    var read = getReadThread(lock, list, condition);
    var write = getWriteThread(lock, list, condition);

    read.start();
    write.start();
  }

  private static Thread getWriteThread(Lock lock, List<Integer> list, Condition condition) {
    return new Thread(() -> {
      while (true) {
        try { // use a try and finally block in which the lock is released, so if there is an exception meanwhile the lock will be released.
          lock.lock();

          if (list.size() == 1) {
            try {
              condition.await();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }

          value++;
          list.add(value);
          System.out.println(value + " added");
          condition.signalAll();
          // The other thread does not regain the lock until this lock is released by the current thread.

          sleep(2000);
        } finally {
          lock.unlock();
        }
      }
    });
  }

  private static Thread getReadThread(Lock lock, List<Integer> list, Condition condition) {
    return new Thread(() -> {
      while (true) {
        lock.lock();

        try {
          if (list.isEmpty()) {
            try {
              condition.await();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }
          System.out.println(list.remove(0) + " removed");
          condition.signalAll();
          // The other thread does not regain the lock until this lock is released by the current thread.

          sleep(2000);
        } finally {
          lock.unlock();
        }
      }
    });
  }
}
