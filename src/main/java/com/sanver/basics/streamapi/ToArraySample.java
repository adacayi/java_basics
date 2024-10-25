package com.sanver.basics.streamapi;

import java.util.stream.Stream;

import static com.sanver.basics.utils.ArrayUtils.printArray;

public class ToArraySample {
    static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + " at age " + age;
        }
    }

    public static void main(String[] args) {
        Person[] people = Stream.of(new Person("Ahmet", 1),
                new Person("Mustafa", 2),
                new Person("Muhammed", 6))
                .toArray(Person[]::new);// The size of the person array in toArray method must be exactly the count
        // of the stream, else it will give a runtime error.
        // toArray is an IntFunction<A[]>, where the int value is the count of the stream.
        //toArray(Person[]::new) is the same as
        //toArray(x->new Person[x])
        printArray(people);
    }
}
