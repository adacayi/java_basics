package com.sanver.basics.lambda;

import com.sanver.basics.utils.RethrowAsUnchecked;

import java.util.stream.Stream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

/**
 * The code below complains not handling ClassNotFoundException, which is a checked exception thrown by
 * Class.forName method.
 * <pre>
 * {@code
 *     Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
 *           .map(Class::forName)
 *           .collect(Collectors.toList());
 * }
 * </pre>
 * To be able to avoid it and handle the exception outside the stream we can use {@link RethrowAsUnchecked}
 * @see <a href = "https://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams">Stackoverflow</a>
 * @see <a href = "http://www.philandstuff.com/2012/04/28/sneakily-throwing-checked-exceptions.html">snekily-throwing</a>
 */
public class RethrowAsUncheckedSample {
    public static void main(String[] args) {
        try {
            Stream.of("java.lang.Object", "java.lang.Integer", "invalid object", "java.lang.String")
                    .map(x -> uncheck(() -> Class.forName(x)))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Invalid class name. " + e.getMessage());
        }
    }
}
