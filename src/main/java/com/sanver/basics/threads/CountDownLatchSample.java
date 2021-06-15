package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchSample {

  public static void main(String[] args) {
    var count = 3;
    var countDownLatch = new CountDownLatch(count);
    var thread1 = new Thread(() -> {
      try {
        System.out.println("Count down started with " + countDownLatch.getCount());
        countDownLatch.await();
        System.out.println("Count down finished");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    thread1.start();

    for (int i = 0; i < count; i++) {
      sleep(3000);
      countDownLatch.countDown();
      System.out.println("Count down " + countDownLatch.getCount());
    }
  }
}
