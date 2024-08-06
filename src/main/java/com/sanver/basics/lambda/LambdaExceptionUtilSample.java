package com.sanver.basics.lambda;

import java.util.stream.Stream;

import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowFunction;

public class LambdaExceptionUtilSample {
    public static void main(String[] args) {
        // The code below complains not handling ClassNotFoundException, which is a checked exception thrown by
        // Class.forName method.
        //
        //    Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
        //          .map(Class::forName)
        //          .collect(Collectors.toList());
        //
        // To be able to avoid it and handle the exception outside the stream we can use the following LambdaExceptionUtil
        // source:
        // https://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
        // also look into: http://www.philandstuff.com/2012/04/28/sneakily-throwing-checked-exceptions.html

        Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
                .map(rethrowFunction(Class::forName))
                .forEach(System.out::println);
    }
}
