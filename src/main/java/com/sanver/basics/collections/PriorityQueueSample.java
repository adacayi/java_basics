package com.sanver.basics.collections;

import java.util.*;

public class PriorityQueueSample {
    static class Student {
        private int id;
        private String name;
        private double cGPA;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getcGPA() {
            return cGPA;
        }

        public Student(int id, String name, double cGPA) {
            this.id = id;
            this.name = name;
            this.cGPA = cGPA;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, cGPA);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Student))
                return false;

            Student second = (Student) obj;

            if (second.cGPA == this.cGPA && second.id == this.id && second.name.equals(this.name))
                return true;

            return false;
        }
    }

    static class Priorities {
        private PriorityQueue<Student> priorityQueue;

        public List<Student> getStudents(List<String> events) {
            Comparator<Student> cGPAComparator = Comparator.comparing(Student::getcGPA).reversed().
                    thenComparing(Student::getName).thenComparing(Student::getId);
            priorityQueue = new PriorityQueue<>(cGPAComparator);
            List<Student> result = new ArrayList<>();
            Student student;

            if (events == null || events.isEmpty())
                return result;

            for (String event : events) {
                String[] splits = event.split(" ");

                if (splits[0].equals("ENTER")) {
                    student = new Student(Integer.parseInt(splits[3]), splits[1], Double.parseDouble(splits[2]));
                    priorityQueue.add(student);
                } else {
                    priorityQueue.poll();//poll and remove are same but
                    // remove throws exception when the queue is empty. poll does not.
                }
            }
            while (!priorityQueue.isEmpty())
                result.add(priorityQueue.remove());

            return result;
//        return Arrays.asList(priorityQueue.toArray(new Student[0]));// This won't return the queue in ordered list.
        }
    }

    public static void main(String... args) {
        Priorities priorities = new Priorities();
        String input = "SERVED\nSERVED\nENTER Shafaet 3.7 35\n" +
                "ENTER Maria 3.6 46\n" +
                "ENTER Dan 3.95 50";
        List<String> events = Arrays.asList(input.split("\n"));
        List<Student> expected = Arrays.asList(
                new Student(50, "Dan", 3.95),
                new Student(35, "Shafaet", 3.7),
                new Student(46, "Maria", 3.6));
        List<Student> actual = priorities.getStudents(events);
        System.out.printf("%s\n", Arrays.equals(expected.toArray(), actual.toArray()));
    }
}
