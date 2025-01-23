package com.sanver.basics.arrays;

import static com.sanver.basics.utils.ArrayUtils.printArray;
import static com.sanver.basics.utils.ArrayUtils.printArrays;

public class PrintingArrays {
    public static void main(String[] args) {
        var data1 = new int[]{1, 2, 3};
        var johnny = new Person("Johnny", 23);
        var kate = new Person("Kate", 32);
        var data2 = new Person[]{johnny, kate};
        var data3 = new int[][]{{1, 2, 3}, {4, 5, 6}};
        var data4 = (Object[]) new int[][]{{1, 2, 3}, {4, 5, 6}};
        printArrays(data1, data2, data3);
        printArray(data4);
    }

    record Person(String name, int age) {
        @Override
        public String toString() {
            return "(%s, %d)".formatted(name, age);
        }
    }
}
