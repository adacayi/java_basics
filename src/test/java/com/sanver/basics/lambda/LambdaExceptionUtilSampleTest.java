package com.sanver.basics.lambda;

import static com.sanver.basics.lambda.LambdaExceptionUtilSample.LambdaExceptionUtil.rethrowConsumer;
import static com.sanver.basics.lambda.LambdaExceptionUtilSample.LambdaExceptionUtil.rethrowFunction;
import static com.sanver.basics.lambda.LambdaExceptionUtilSample.LambdaExceptionUtil.rethrowSupplier;
import static com.sanver.basics.lambda.LambdaExceptionUtilSample.LambdaExceptionUtil.uncheck;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class LambdaExceptionUtilSampleTest {

  // Source: https://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
  @Test
  public void test_Consumer_with_checked_exceptions() throws ClassNotFoundException {
    Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
          .forEach(rethrowConsumer(className -> System.out.println(Class.forName(className))));

    Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
          .forEach(rethrowConsumer(System.out::println));
  }

  @Test
  public void test_Function_with_checked_exceptions() throws ClassNotFoundException {
    List<Class<?>> classes1
        = Stream.of("Object", "Integer", "String")
                .map(rethrowFunction(className -> Class.forName("java.lang." + className)))
                .collect(Collectors.toList());

    List<Class<?>> classes2
        = Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
                .map(rethrowFunction(Class::forName))
                .collect(Collectors.toList());
  }

  @Test
  public void test_Supplier_with_checked_exceptions() {
    Collector.of(
        rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, StandardCharsets.UTF_8))),
        StringJoiner::add, StringJoiner::merge, StringJoiner::toString);
  }

  @Test
  public void test_uncheck_exception_thrown_by_method() {
    Class<?> clazz1 = uncheck(() -> Class.forName("java.lang.String"));

    Class<?> clazz2 = uncheck(Class::forName, "java.lang.String");
  }

  @Test
  public void test_if_correct_exception_is_still_thrown_by_method() {
    assertThrows(ClassNotFoundException.class, () -> uncheck(Class::forName, "INVALID"));
  }
}
