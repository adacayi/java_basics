package com.sanver.basics.streamapi;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class OptionalSample {

    public static void main(String[] args) {
        var names = List.of("abc", "abd", "bcd");
        Optional<String> optionalA = names
                .stream()
                .filter(x -> x.startsWith("a"))
                .findFirst();
        Optional<String> optionalC = names
                .stream()
                .filter(x -> x.startsWith("c"))
                .findFirst();
        System.out.println(optionalA.map(x -> "name starting with a " + x).orElse("na"));
        System.out.println(optionalC.map(x -> "name starting with c " + x).orElse("na"));
        System.out.println("optionalA: " + optionalA); // notice map does not change optionalA. Since Optional class is immutable in java.
        System.out.println("optionalC: " + optionalC);
        String value = null;
        // Optional<String> optional=Optional.of(value);// This will give a runtime
        // exception since the value is null
        Optional<String> optional = Optional.ofNullable(value); // 'Optional. ofNullable()' with null argument should be replaced with 'Optional. empty()'
        System.out.println(optional);
        System.out.println(optional.isPresent());
        // System.out.println(optional.get()); // This will give a runtime error since
        // the value is null
        Optional<Double> doubleOptional = Optional.of(2.3);
        System.out.println(doubleOptional);
        System.out.println(doubleOptional.isPresent());
        System.out.println(doubleOptional.get());
        doubleOptional = Optional.empty();
        System.out.println(optional.isPresent());
        System.out.println(doubleOptional);
        System.out.println(doubleOptional.orElse(3.2));
        OptionalDouble optionalDouble = DoubleStream.of(2, 3, 4).average();
        System.out.println(optionalDouble);
        System.out.println(optionalDouble.isPresent());
        System.out.println(optionalDouble.getAsDouble());
        optionalDouble = OptionalDouble.empty();
        System.out.println(optionalDouble);
    }
}
