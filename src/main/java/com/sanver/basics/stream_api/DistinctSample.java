package com.sanver.basics.stream_api;

import java.util.Arrays;

public class DistinctSample {

    public static void main(String[] args) {
        int[] numbers = {3, 2, 5, 6, 3, 3};
        String[] names = {"Ahmet", "Salih", "Ahmet"};
        System.out.println(Arrays.toString(Arrays.stream(numbers).distinct().toArray()));
		System.out.println(Arrays.toString(Arrays.stream(names).distinct().toArray(String[]::new)));
    }
}
