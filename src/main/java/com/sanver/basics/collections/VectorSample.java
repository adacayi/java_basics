package com.sanver.basics.collections;

import java.util.List;
import java.util.Vector;

public class VectorSample {
    public static void main(String[] args) {
        List<String> vector = new Vector<>();//Change it to ArrayList to see the difference
        int count = 100000;

        Runnable append = () -> {
            for (int i = 0; i < count; i++)
                vector.add("a");
        };

        Runnable remove = () -> {
            for (int i = 0; i < count; ) {
                if (vector.size() > 0) {
                    vector.remove(0);
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

        System.out.println("Finished. Vector size= " + vector.size());// Since Vector is synchronised the size
        // is 0. If we used ArrayList instead the
        // code would not end always.
    }
}
