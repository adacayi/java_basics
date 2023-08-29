package com.sanver.basics.comparing;

import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BasicComparatorSample {
    public static void main(String[] args) {
        // Comparator is a functional interface with a compare method returning int:
        // @FunctionalInterface
        // public interface Comparator<T> { int compare(T o1, T o2);}
        // We can have anonymous classes with lambda expression that implement Comparator interface to compare a collection of objects.
        // We can also use Comparator.of method to generate a comparator for a collection of objects.
        // The parameter of the Comparator.of method is used as the key extractor for the objects and then the collection is sorted based on those extracted keys.
        // The extracted keys in this case needs to implement Comparable interface (which has a compareTo method).
        var person1 = new Person("Jane", 25);
        var person2 = new Person("Mike", 23);
        var person3 = new Person("Adam", 23);
        var list = new ArrayList<>(List.of(person1, person2, person3));

        var ageComparator = Comparator.comparing(Person::getAge); // Possible other ways to write it are Comparator.comparing((Person person)-> person.getAge()); Comparator.<Person, Integer>comparing(person -> person.getAge())
        // As explained above this generates a Comparator instance with the key extractor being Person::getAge, hence will extract age for each Person in the list and then compare those ages when List.sort method is called with this comparator.
        list.sort(ageComparator);
        printList("ageComparator ", list);


        var ageNameComparator = (Comparator<Person>) (x, y) -> { // This is to show how we can use lambda expression to define a Comparator. Here we are defining the int compare(T o1, T o2); method of the Comparator interface. This method will be used to sort the list.
            if (x.getAge() > y.getAge()) {
                return 1;
            }
            if (x.getAge() < y.getAge()) {
                return -1;
            }
            return x.getName().compareTo(y.getName());
        };

        list.sort(ageNameComparator);
        printList("ageNameComparator", list);


        var ageNameComparatorSecondWay = Comparator.comparing(Person::getAge).thenComparing(Person::getName);
        Collections.shuffle(list);
        list.sort(ageNameComparatorSecondWay);
        printList("ageNameComparatorSecondWay", list);


        var ageDescendingNameComparator = Comparator.comparing(Person::getAge).thenComparing(Comparator.comparing(Person::getName).reversed());
        list.sort(ageDescendingNameComparator);
        printList("ageDescendingNameComparator", list);

        var reversedAgeNameComparator = Comparator.comparing(Person::getAge).thenComparing(Person::getName).reversed();
        list.sort(reversedAgeNameComparator);
        printList("reversedAgeNameComparator", list);
    }

    private static <T> void printList(String description, List<T> list) {
        System.out.printf("%-28s: %s\n", description, list);
    }

    @Value
    static class Person {
        String name;
        int age;

        @Override
        public String toString() {
            return String.format("%s %s", name, age);
        }
    }
}
