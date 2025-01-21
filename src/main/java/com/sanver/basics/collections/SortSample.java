package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SortSample {

    public static void main(String[] args) {
        var values = new ArrayList<>(Arrays.asList(4, 2, 3, 5));
        Comparator<Integer> ascending = Comparator.naturalOrder();
        Comparator<Integer> descending = ascending.reversed();
        var format = "%-37s: %s%n";
        values.sort(ascending);
        System.out.printf(format, "values.sort(ascending)", values);

        values.sort(descending);
        System.out.printf(format, "values.sort(descending)", values);

        Collections.sort(values); // This can also be used for sorting
        // Collections.reverse(values) should not be used to sort in descending order.
        // Instead, comparators should be used.
        System.out.printf(format, "Collections.sort(values)", values);

        Collections.sort(values, descending);
        System.out.printf(format, "Collections.sort(values, descending)", values);

        values.sort(null); // This is what Collections.sort(list) does
        System.out.printf(format, "values.sort(null)", values); // sort(null) sorts the list into ascending order according to the natural ordering of its elements
    }
}
