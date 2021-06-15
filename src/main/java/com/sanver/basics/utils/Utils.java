package com.sanver.basics.utils;

import java.util.concurrent.Callable;

public class Utils {
  public interface ThrowingRunnable {
    void run() throws Exception;
  }


  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static <T, E extends Throwable> T rethrowAsThrowable(final Callable<T> callable) throws E {
    try {
      return callable.call();
    } catch (Exception e) {
      throw (E) e;
    }
  }

  public static <E extends Throwable> void rethrowAsThrowable(final ThrowingRunnable runnable) throws E {
    try {
      runnable.run();
    } catch (Exception e) {
      throw (E) e;
    }
  }


}
