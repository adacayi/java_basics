package com.sanver.basics.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowConsumer;
import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowFunction;
import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowSupplier;
import static com.sanver.basics.utils.LambdaExceptionUtil.uncheck;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Source: https://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
class LambdaExceptionUtilTest {
    @Nested
    class RethrowConsumer {
        @Test
        void shouldExecute_whenConsumerHasCheckedExceptionSignature() {
            assertDoesNotThrow(() ->
                    Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
                            .forEach(rethrowConsumer(className -> System.out.println(Class.forName(className)))));
        }

        @Test
        void shouldThrowUncheckedException_whenConsumerThrowsCheckedException() {
            assertThatThrownBy(() ->
                    Stream.of("Object", "Integer", "String")
                            .forEach(rethrowConsumer(className -> System.out.println(Class.forName(className))))
            ).isInstanceOf(ClassNotFoundException.class);
        }
    }

    @Nested
    class RethrowFunction {
        @Test
        void shouldExecute_whenFunctionHasCheckedExceptionSignature() {
            assertDoesNotThrow(() ->
                    Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
                            .map(rethrowFunction(Class::forName)));
        }

        @Test
        void shouldThrowUncheckedException_whenFunctionThrowsCheckedException() {
            assertThatThrownBy(() ->
                    Stream.of("Object", "Integer", "String").map(rethrowFunction(Class::forName))
            ).isInstanceOf(ClassNotFoundException.class);
        }
    }

    @Nested
    class RethrowSupplier {

        @Test
        void shouldExecute_whenSupplierHasCheckedExceptionSignature() {
            assertDoesNotThrow(() -> rethrowSupplier(() -> Class.forName("java.lang.String")).get());
        }

        @Test
        void shouldThrowUncheckedException_whenFunctionThrowsCheckedException() {
            assertThatThrownBy(() ->
                    rethrowSupplier(() -> Class.forName("Invalid")).get()
            ).isInstanceOf(ClassNotFoundException.class);
        }
    }

    @Nested
    class Uncheck {

        @Test
        void shouldNotNeedThrowsExceptionInTheTestMethod_whenSupplierThrowsCheckedException() {
            assertDoesNotThrow(() -> {
                uncheck(() -> Class.forName("java.lang.String"));
                uncheck(Class::forName, "java.lang.String");
            });
        }

        @Test
        void shouldThrowUncheckedExceptionWithSameClass() {
            assertThrows(ClassNotFoundException.class, () -> uncheck(Class::forName, "INVALID"));
        }
    }
}
