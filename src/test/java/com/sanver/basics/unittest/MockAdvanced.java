package com.sanver.basics.unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class MockAdvanced {

    static class Number {
        private int number;

        public Number(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return Integer.toString(number);
        }

        @Override // We overrided equals method to make comparing Number objects easier
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass())
                return false;

            return this.getNumber() == ((Number) o).getNumber();
        }

        @Override // We overrided hashCode so that every equal object according to the equals
        // method has the same hash code.
        public int hashCode() {
            return this.getNumber();
        }
    }

    static class Calculator {
        public Number calculate(Number x, Number y) {
            return new Number(x.getNumber() + y.getNumber() + 1); // We intentionally made this method to not calculate
            // sum correctly to show mocked objects behave
            // differently.
        }

        public void calculateWithoutReturn(Number x, Number y) {
            if (x.getNumber() < 0)
                throw new IllegalArgumentException("Just non-negatives for now");
        }
    }

    @Mock
    Calculator calculatorMock;

    @Spy
    Calculator calculatorSpy;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calculate3plus4() {
        Number x = new Number(3);
        Number y = new Number(4);
        Number expected = new Number(7);
        Mockito.when(calculatorMock.calculate(x, y)).thenReturn(new Number(7));
        Number actual = calculatorMock.calculate(x, y);
        Assertions.assertEquals(expected, actual, String.format("%s plus %s should be equal to %s", x, y, expected));
    }

    @Test
    void calculate3plus4WithSpy() {
        Number x = new Number(3);
        Number y = new Number(4);
        Number expected = new Number(8); // In actual implementation the result is new Number(8). not 7.
        // So we changed expected accordingly.
        Number actual = calculatorSpy.calculate(x, y);// If we wrote Number actual = calculatorMock.calculate(x, y);
        // actual would be null. Mock does not preserve implementation,
        // while spy preserves it.
        Assertions.assertEquals(expected, actual, String.format("%s plus %s should be equal to %s", x, y, expected));
    }

    @Test
    void calculateGeneral() {
        Mockito.when(calculatorMock.calculate(Mockito.any(), Mockito.any())).then(
                m -> new Number(((Number) m.getArgument(0)).getNumber() + ((Number) m.getArgument(1)).getNumber()));
        // We cannot combine matchers with values i.e. this would give an error
        // calculator.calculate(Mockito.any(), new Number(0))
        Number x = new Number(8);
        Number y = new Number(3);
        Number expected = new Number(11);
        Number actual = calculatorMock.calculate(x, y);
        Assertions.assertEquals(expected, actual, String.format("%s plus %s should be equal to %s", x, y, actual));
    }

    @Test
    void calculateCallRealMethod() {
        Mockito.when(calculatorMock.calculate(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Number x = new Number(87);
        Number y = new Number(2);
        Number expected = new Number(90);// We intentionally made it 90 because actual method will add 1 to the sum.
        Number actual = calculatorMock.calculate(x, y);
        Assertions.assertEquals(expected, actual, String.format("%s plus %s should be equal to %s", x, y, expected));
    }

    @Test
    void testCalculateWithoutReturn() {
        Number x = new Number(5);
        Number y = new Number(2);
        Mockito.doAnswer(m -> {
            calculatorMock.calculate(m.getArgument(0), m.getArgument(1));
            return null;
        }).when(calculatorMock).calculateWithoutReturn(Mockito.any(), Mockito.any());
        calculatorMock.calculateWithoutReturn(x, y);
        Mockito.verify(calculatorMock, Mockito.times(1)).calculate(x, y);
    }
}
