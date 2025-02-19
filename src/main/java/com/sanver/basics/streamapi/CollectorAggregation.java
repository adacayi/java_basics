package com.sanver.basics.streamapi;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorAggregation {

    public static void main(String[] args) {
        Person[] people = {new Person("Ahmet", 3), new Person("Mustafa", 3),
                new Person("Fatima", 6), new Person("Ahmet", 6)};
        Supplier<Stream<Person>> supplier = () -> Arrays.stream(people);
        int intResult = supplier.get().collect(Collectors.summingInt(Person::age));
        System.out.println("Sum of ages is " + intResult);
        intResult = supplier.get().mapToInt(p -> p.age).sum();
        System.out.println("Sum of ages is " + intResult);
        long longResult = supplier.get().collect(Collectors.counting());
        System.out.println("Count of ages is " + longResult);
        longResult = supplier.get().count();
        System.out.println("Count of ages is " + longResult);
        double doubleResult = supplier.get().collect(Collectors.averagingInt(Person::age));
        System.out.println("Average age is " + doubleResult);
        System.out.println("Empty stream averagingInt result: " + Stream.empty().collect(Collectors.averagingInt(x -> 1)));
        OptionalDouble optionalDoubleResult = supplier.get().mapToInt(Person::age).average();
        System.out.println("Average age is " + optionalDoubleResult.orElse(0));
        var comparator = Comparator.comparing(Person::age).thenComparing(Comparator.comparing(Person::name).reversed());
        System.out.println("Minimum aged person is "
                + supplier.get().collect(Collectors.minBy(comparator)).
                orElse(new Person("", 0)));
        System.out.println("Minimum aged person is "
                + supplier.get().min(comparator).
                orElse(new Person("", 0)));
        OptionalInt optionalIntResult = supplier.get().mapToInt(p -> p.age).min();
        System.out.println("Minimum age is " + optionalIntResult.orElse(0));
        System.out.println("Maximum aged person is "
                + supplier.get().collect(Collectors.maxBy(comparator)).
                orElse(new Person("", 0)));
        System.out.println("Maximum aged person is " +
                supplier.get().max(comparator).orElse(new Person("", 0)));
        optionalIntResult = supplier.get().mapToInt(p -> p.age).max();
        System.out.println("Maximum age is " + optionalIntResult.orElse(0));
        DoubleSummaryStatistics doubleSummary = supplier.get().collect(Collectors.<Person>summarizingDouble(p -> p.age() * 0.1)); // We can have summarizingInt and summarizingLong methods as well, resulting in IntSummaryStatistics and LongSummaryStatistics, based on the sum, min, average and max of the results of the ToIntFunction or ToLongFunction passed to the method.
        System.out.println("doubleSummary: " + doubleSummary);
        System.out.println("doubleSummary.getCount(): " + doubleSummary.getCount());
        System.out.println("doubleSummary.getSum(): " + doubleSummary.getSum()); // getSum() returns double for DoubleSummaryStatistics
        System.out.println("doubleSummary.getMin(): " + doubleSummary.getMin()); // getMin() returns double for DoubleSummaryStatistics
        System.out.println("doubleSummary.getMax(): " + doubleSummary.getMax()); // getMax() returns double for DoubleSummaryStatistics
        System.out.println("doubleSummary.getAverage(): " + doubleSummary.getAverage()); // getAverage() returns double for DoubleSummaryStatistics
        System.out.println("Empty stream summarizingDouble: " +Stream.empty().collect(Collectors.summarizingDouble(x->1)));
        System.out.println("Empty stream summarizingInt: " +Stream.empty().collect(Collectors.summarizingInt(x->1)));
        System.out.println("Empty stream summarizingLong: " +Stream.empty().collect(Collectors.summarizingLong(x->1)));
        System.out.println(supplier.get().map(p -> p.name + " with age " + p.age)
                .collect(Collectors.joining(" and ", "People in the collection are ", ".")));
        System.out.println(
                "Map " + supplier.get().collect(Collectors.toMap(p -> p.age, p -> p.name, (x, y) -> x + ":" + y)));
        Map<Boolean, List<Person>> partitionMap = supplier.get().collect(Collectors.partitioningBy(p -> p.age > 3));
        System.out.println("Partitioning by age greater than 3" + partitionMap);
        Map<String, List<Person>> groupingMap = supplier.get().collect(Collectors.groupingBy(p -> p.name));
        System.out.println("Grouping by name " + groupingMap);
        // The last function is a merger function in case there is a key with more than one value.
        Collector<Person, StringJoiner, String> personNameCollector = Collector.of(() -> new StringJoiner(" | "), // supplier
                (j, p) -> j.add(p.name.toUpperCase()), // accumulator
                StringJoiner::merge, // combiner
                StringJoiner::toString); // finisher
        // The supplier initially constructs such a StringJoiner with the appropriate
        // delimiter. The accumulator is used to add each person's upper-cased name to
        // the StringJoiner. The combiner knows how to merge two StringJoiners into one.
        // In the last step the finisher constructs the desired String from the
        // StringJoiner.
        System.out.println(supplier.get().parallel().collect(personNameCollector)); // This works properly with parallel streams as well

        var largeSumCollector = Collector.<Long, BigInteger[], BigInteger>of(() -> new BigInteger[]{BigInteger.valueOf(0)}, (x, y) -> x[0] = x[0].add(BigInteger.valueOf(y)), (x, y) -> new BigInteger[]{x[0].add(y[0])}, x -> x[0]); // Since BigInteger is immutable we used BigInteger[] which is mutable.
        var values = new long[]{(long) Math.pow(10, 18), 3 * (long) Math.pow(10, 18), Long.MAX_VALUE};
        var sumResult = Arrays.stream(values).boxed().collect(largeSumCollector);
        var format = NumberFormat.getInstance();
        System.out.printf("Sum of %s: %s%n", Arrays.stream(values).mapToObj(format::format).collect(Collectors.joining(" + ")), format.format(sumResult));
    }

    record Person(String name, int age) {
        @Override
        public String toString() {
            return "(%s, %d)".formatted(name, age);
        }
    }
}
