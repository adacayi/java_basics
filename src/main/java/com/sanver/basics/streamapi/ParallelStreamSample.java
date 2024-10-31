package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.getThreadInfo;

public class ParallelStreamSample {

    public static void main(String[] args) {
        int length = 10;
        List<Integer> idList = IntStream.range(0, length).boxed().toList();
        System.out.println(idList);
        idList = idList.parallelStream().map(x -> {
            System.out.printf("Mapping %d to %2d. %s%n", x, 2 * x, getThreadInfo());
            return 2 * x;
        }).toList(); // toList and collect methods preserve order, however we can see that multiple threads are used.
        System.out.println(idList);
        var newList = new ArrayList<Integer>();
        idList.parallelStream().forEach(newList::add);
        System.out.println(newList);
    }
}
