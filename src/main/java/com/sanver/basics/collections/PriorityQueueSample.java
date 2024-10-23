package com.sanver.basics.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * PriorityQueue is an unbounded priority queue based on a priority heap. The elements of the priority queue are ordered according to their natural ordering, or by a Comparator provided at queue construction time, depending on which constructor is used. A priority queue does not permit null elements. A priority queue relying on natural ordering also does not permit insertion of non-comparable objects (Objects must implement Comparable) (doing so may result in ClassCastException).
 * <br>Check <a href = https://www.geeksforgeeks.org/binary-heap>binary heap</a> for how it works.
 */
public class PriorityQueueSample {
    public static void main(String... args) {
        List<Student> students = Arrays.asList(
                new Student(46, "Maria", 3.6),
                new Student(50, "Dan", 3.95),
                new Student(42, "Maria", 3.6),
                new Student(35, "Shafaet", 3.7)
        );
        var studentComparator = Comparator.<Student, Double>comparing(x -> x.cGPA).thenComparing(x -> x.id);
        var priorityQueue = new PriorityQueue<>(studentComparator); // If we did not specify a comparator, then Student class should be implementing Comparable to be able to use the NaturalComparator, otherwise we will get a ClassCastException.
        // var priorityQueue = new PriorityQueue<>(); Try this instead to see the ClassCastException, since Student does not implement Comparable.

        students.forEach(priorityQueue::offer); // Insert elements to the priority queue to form a min-heap. Priority queue uses an array to store the elements, with an initial capacity, which can be determined when constructed.

        System.out.printf("priorityQueue.forEach(System.out::println): %n%n");
        priorityQueue.forEach(System.out::println); // Notice that this does not need to be completely ordered based on the studentComparator, since this is a min-heap, a complete binary tree where each parent should be less than or equal to its children.
        System.out.printf("%npriorityQueue.toString():%n%n%s%n ", priorityQueue); // The order will be the same as the forEach
        System.out.printf("%nPolling: %n%n");
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll()); // Retrieves and removes the head of this queue, which will always be the smallest element of the queue.
            // poll and remove both retrieve and remove the head of the queue but remove throws an exception when the queue is empty, poll does not.
        }
    }

    record Student(int id, String name, double cGPA) {
    }
}
