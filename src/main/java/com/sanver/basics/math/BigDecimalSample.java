package com.sanver.basics.math;

import java.math.BigDecimal;

/**
 * The {@code BigDecimal} class provides operations on arbitrary-precision decimal numbers.
 * Unlike the primitive types such as {@code double}, {@code BigDecimal} provides precise
 * control over decimal arithmetic and is especially useful for financial calculations
 * where precision is critical.
 *
 * <p>Operations supported by {@code BigDecimal} include standard arithmetic, rounding,
 * comparison, and conversion methods. {@code BigDecimal} also allows customization of scale
 * (number of digits to the right of the decimal point) and precision for operations.</p>
 *
 * <p>Note: {@code BigDecimal} objects are immutable, meaning their values cannot be changed
 * after they are created. Methods that perform arithmetic operations return new {@code BigDecimal}
 * objects rather than modifying existing ones.</p>
 *
 * <h2>Usage Examples:</h2>
 *
 * <pre>
 * {@code
 * BigDecimal value1 = new BigDecimal("0.1");
 * BigDecimal value2 = new BigDecimal("0.2");
 * BigDecimal sum = value1.add(value2);
 * System.out.println("Sum: " + sum);  // Outputs "Sum: 0.3"
 * }
 * </pre>
 *
 * <p>It is recommended to use the constructor that accepts a {@code String} to avoid precision
 * issues that can occur when using floating-point types (such as {@code double} or {@code float}).
 * </p>
 *
 * @see java.math.BigInteger
 * @see java.math.MathContext
 * @see java.math.RoundingMode
 *
 */
public class BigDecimalSample {
    public static void main(String[] args) {
        // Using Double
        double doubleValue1 = 0.1;
        double doubleValue2 = 0.2;
        double doubleSum = doubleValue1 + doubleValue2;

        // Using BigDecimal
        BigDecimal bigDecimalValue1 = new BigDecimal("0.1");
        BigDecimal bigDecimalValue2 = new BigDecimal("0.2");
        BigDecimal bigDecimalSum = bigDecimalValue1.add(bigDecimalValue2);

        // Display results
        System.out.println("Using Double:");
        System.out.println(doubleValue1 + " + " + doubleValue2 + " = " + doubleSum);

        System.out.println("\nUsing BigDecimal:");
        System.out.println(bigDecimalValue1 + " + " + bigDecimalValue2 + " = " + bigDecimalSum);
    }
}
