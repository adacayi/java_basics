package com.sanver.basics.garbage_collection;

import static com.sanver.basics.utils.ThreadUtils.sleep;

class Sample {

  private String name;

  public Sample(String name) {
    this.name = name;
  }

  @Override
  //Called by the garbage collector on an object when garbage collection
  //determines that there are no more references to the object.
  public void finalize() {
    System.out.printf("%s finalize method invoked", name);
  }
}

public class FinalizeSample {

  public static void main(String... args) {
    Sample sample = new Sample("First sample");
    System.out.println("Started");
    sample = null;
    System.gc();

    sleep(1000);//This is necessary for the finalize to work.
    // Else execution might terminate before garbage collection.
  }
}
