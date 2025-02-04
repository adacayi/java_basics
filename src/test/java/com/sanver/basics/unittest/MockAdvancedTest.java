package com.sanver.basics.unittest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockAdvancedTest {
    @Mock
    Calculator calculatorMock;
    @Spy
    Calculator calculatorSpy;

    private record MyNumber(int number) {
    }

    private static class Calculator {
        public MyNumber calculate(MyNumber x, MyNumber y) {
            return new MyNumber(x.number() + y.number() + 1); // We intentionally made this method to not calculate
            // sum correctly to show mocked objects behave differently.
        }

        public void calculateWithoutReturn(MyNumber x, MyNumber y) {
            if (x.number() < 0)
                throw new IllegalArgumentException("Just non-negatives for now");
        }
    }

    @Nested
    class Calculate {
        @ParameterizedTest
        @CsvSource({"3, 5, 9", "10, 12, 23"})
        void shouldReturnAdditionPlusOne(int i, int j, int expected) {
            // Given
            var number1 = spy(new MyNumber(i));
            var number2 = spy(new MyNumber(j));
            var calculator = new Calculator();

            // When
            var result = calculator.calculate(number1, number2);

            // Then
            verify(number1).number();
            verify(number2).number();
            assertThat(result).isEqualTo(new MyNumber(expected));
        }
    }

    @Nested
    class MockUsages {
        @Test
        void calculate3plus4() {
            // Given
            var x = new MyNumber(3);
            var y = new MyNumber(4);
            var expected = new MyNumber(7);
            when(calculatorMock.calculate(x, y)).thenReturn(new MyNumber(7)); // This is specifically set to 7 to show that mock is called, rather than the actual method, since the actual method would return 8 in this case.

            // When
            var actual = calculatorMock.calculate(x, y);

            // Then
            assertThat(actual).as("%s plus %s should be equal to %s", x, y, expected).isEqualTo(expected);
        }

        @Test
        void calculate3plus4WithSpy() {
            // Given
            var x = new MyNumber(3);
            var y = new MyNumber(4);
            var expected = new MyNumber(8); // In actual implementation the result is new MyNumber(8). not 7.
            // So we changed expected accordingly.

            // When
            var actual = calculatorSpy.calculate(x, y);// While spy calls the real method, calculatorMock.calculate(x, y) would return null.

            // Then
            assertThat(actual).as("%s plus %s should be equal to %s", x, y, expected).isEqualTo(expected);
        }

        @Test
        void calculateGeneral() {
            // Given
            when(calculatorMock.calculate(any(), any())).then( // We cannot combine matchers with values i.e. this would give an error calculator.calculate(Mockito.any(), new MyNumber(0))
                    m -> new MyNumber(((MyNumber) m.getArgument(0)).number() + ((MyNumber) m.getArgument(1)).number()));

            var x = new MyNumber(8);
            var y = new MyNumber(3);
            var expected = new MyNumber(11);

            // When
            var actual = calculatorMock.calculate(x, y);

            // Then
            assertThat(actual).as("%s plus %s should be equal to %s", x, y, expected).isEqualTo(expected);
        }

        @Test
        void calculateCallRealMethod() {
            // Given
            when(calculatorMock.calculate(any(), any())).thenCallRealMethod();
            var x = new MyNumber(87);
            var y = new MyNumber(2);
            var expected = new MyNumber(90);// This should be 90 because actual method will add 1 to the sum.

            // When
            var actual = calculatorMock.calculate(x, y);

            // Then
            assertThat(actual).as("%s plus %s should be equal to %s", x, y, expected).isEqualTo(expected);
        }

        @Test
        void testCalculateWithoutReturn() {
            // Given
            var x = new MyNumber(5);
            var y = new MyNumber(2);
            doAnswer(m -> {
                calculatorMock.calculate(m.getArgument(0), m.getArgument(1));
                return null;
            }).when(calculatorMock).calculateWithoutReturn(any(), any());

            // When
            calculatorMock.calculateWithoutReturn(x, y);

            // Then
            verify(calculatorMock, times(1)).calculate(x, y);
        }
    }
}
