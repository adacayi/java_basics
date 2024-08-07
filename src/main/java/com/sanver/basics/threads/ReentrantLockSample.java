package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSample {
//	Source: https://www.geeksforgeeks.org/reentrant-lock-java/
//	Background
//
//	The traditional way to achieve thread synchronization in Java is by the use of synchronized keyword. While it
//	provides a certain basic synchronization, the synchronized keyword is quite rigid in its use. For example, a
//	thread can take a lock only once. Synchronized blocks don’t offer any mechanism of a waiting queue and after the
//	exit of one thread, any thread can take the lock. This could lead to starvation of resources for some other thread
//	for a very long period of time.
//	Reentrant Locks are provided in Java to provide synchronization with greater flexibility.
//
//	What are Reentrant Locks?
//
//	The ReentrantLock class implements the Lock interface and provides synchronization to methods while accessing
//	shared resources. The code which manipulates the shared resource is surrounded by calls to lock and unlock method.
//	This gives a lock to the current working thread and blocks all other threads which are trying to take a lock on
//	the shared resource.
//
//	As the name says, ReentrantLock allows threads to enter into the lock on a resource more than once. When the
//	thread first enters into the lock, a hold count is set to one. Before unlocking the thread can re-enter into lock
//	again and every time hold count is incremented by one. For every unlocks request, hold count is decremented by one
//	and when hold count is 0, the resource is unlocked.

//	Reentrant Locks also offer a fairness parameter, by which the lock would abide by the order of the lock request i
//	.e. after a thread unlocks the resource, the lock would go to the thread which has been waiting for the longest
//	time. This fairness mode is set up by passing true to the constructor of the lock.

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
        lock.lock();

        try {
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

        try { // use a try and finally block in which the lock is released, so if there is an
          // exception meanwhile the lock will be released.
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
