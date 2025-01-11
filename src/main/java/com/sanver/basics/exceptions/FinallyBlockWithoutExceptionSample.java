package com.sanver.basics.exceptions;

import java.util.stream.IntStream;

public class FinallyBlockWithoutExceptionSample {
    public static void main(String[] args) {
        accumulate(null);
        accumulate(new byte[]{});
        accumulate(new byte[]{3, 2, 1, 10});
    }

    private static int accumulate(byte[] array) {
        int result = -1; // if we did not initialize this, it would result in a "variable result might not have been initialized" in the finally block. -1 is set purposefully so that we see result assignment in return is done before the finally block.

        try {
            if (array == null || array.length == 0) {
                return result = 0; // Note that the assignment value is returned. We purposefully did the assignment in the return statement to show that the assignment is done before the finally block is executed.
            } else {
                return result = IntStream.range(0, array.length).map(i -> array[i]).sum(); // Note that the assignment value is returned.
            }

        } finally {
            System.out.println("Result = " + result); // Note that result is assigned to its final value
        }
    }
}
