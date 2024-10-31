package com.sanver.basics.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
 * <h3>Round Half Up (Common Rounding):</h3>
 * This is the most common method and the one described above. If the rounding digit is exactly 5, you round up.
 *
 *  <h3>Round Half Down:</h3>
 * If the rounding digit is exactly 5, you round down. For instance, 2.5 would round to 2 rather than 3.
 *
 *  <h3>Round Half to Even (Banker’s Rounding):</h3>
 * If the rounding digit is exactly 5, you round to the nearest even number. For example, 2.5 rounds to 2, but 3.5 rounds to 4. This method is used in financial and statistical applications to reduce rounding bias.
 *
 *  <h3>Round Up (Ceiling):</h3>
 * You always round up to the nearest specified place. For instance, 3.2 would round to 4.
 *
 *  <h3>Round Down (Floor):</h3>
 * You always round down to the nearest specified place. For example, 3.8 would round to 3.
 *
 * @see java.math.BigInteger
 * @see java.math.MathContext
 * @see java.math.RoundingMode
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
        System.out.printf("%s + %s = %s%n%n",bigDecimalValue1, bigDecimalValue2, bigDecimalSum);

        System.out.println("BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), 5, RoundingMode.FLOOR)  : " + BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), 5, RoundingMode.FLOOR));
        System.out.println("BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), 5, RoundingMode.CEILING): " + BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), 5, RoundingMode.CEILING));
        // System.out.println(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3))); // This would result in "java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result." We need to set a rounding mode for this non-terminating decimal.
        System.out.println("BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP): " + BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP)); // This would round to the nearest integer, which is 0

        System.out.println();
        System.out.println("BigDecimal.valueOf(45).divide(BigDecimal.valueOf(99), 5, RoundingMode.HALF_UP)     : " + BigDecimal.valueOf(45).divide(BigDecimal.valueOf(99), 5, RoundingMode.HALF_UP)); // Since the 45/99 = 0.45454545454545.. and 6th digit is 5 and rounding is HALF_UP, 5th digit is increased from 4 to 5.
        System.out.println("BigDecimal.valueOf(45).divide(BigDecimal.valueOf(99), 5, RoundingMode.HALF_DOWN)   : " + BigDecimal.valueOf(45).divide(BigDecimal.valueOf(99), 5, RoundingMode.HALF_DOWN)); // Since the 45/99 = 0.45454545454545.. and 6th digit is 5 and since the rest is of the digits is not 0 and rounding is HALF_DOWN, 5th digit is increased from 4 to 5 as well.
        System.out.println("new BigDecimal(454545).divide(new BigDecimal(1_000_000), 5, RoundingMode.HALF_UP)  : " + new BigDecimal(454545).divide(new BigDecimal(1_000_000), 5, RoundingMode.UP)); // Since the result is = 0.454545 and 6th digit is 5 and the rest is 0 and rounding is HALF_DOWN, 5th digit stays as 4.
        System.out.println("new BigDecimal(454545).divide(new BigDecimal(1_000_000), 5, RoundingMode.HALF_DOWN): " + new BigDecimal(454545).divide(new BigDecimal(1_000_000), 5, RoundingMode.HALF_DOWN)); // Since the result is = 0.454545 and 6th digit is 5 and the rest is 0 and rounding is HALF_DOWN, 5th digit stays as 4.

        System.out.println();

        var scaledBigDecimal = new BigDecimal("0.454545").setScale(3, RoundingMode.HALF_UP);
        System.out.println("scaledBigDecimal = new BigDecimal(\"0.454545\").setScale(3, RoundingMode.HALF_UP): " + scaledBigDecimal);
        System.out.println("scaledBigDecimal.scale(): " + scaledBigDecimal.scale());
        System.out.println("scaledBigDecimal.multiply(new BigDecimal(\"0.1\")): " + scaledBigDecimal.multiply(new BigDecimal("0.1"))); // This is to show that the final BigDecimal scale is not preserved
        System.out.println("scaledBigDecimal.multiply(new BigDecimal(\"0.1\")).scale(): " + scaledBigDecimal.multiply(new BigDecimal("0.1")).scale());

        System.out.println();
        // Using MathContext to preserve scale and rounding up
        var mathContext = new MathContext(3, RoundingMode.HALF_UP);
        System.out.println("var mathContext = new MathContext(3, RoundingMode.HALF_UP);");
        System.out.println("new BigDecimal(\"0.454545\").add(new BigDecimal(\"0.1\"), mathContext): " + new BigDecimal("0.454545").add(new BigDecimal("0.1"), mathContext));

        // Setting scale with chaining operations
        System.out.println("new BigDecimal(\"0.454545\").subtract(new BigDecimal(\"0.0011\")).setScale(3, RoundingMode.HALF_UP) = (0.454545 - 0.0011).scale = (0.453445).scale = " + new BigDecimal("0.454545").subtract(new BigDecimal("0.0011")).setScale(3, RoundingMode.HALF_UP));
        System.out.println("new BigDecimal(\"0.454545\").setScale(3, RoundingMode.HALF_UP).subtract(new BigDecimal(\"0.0011\")).setScale(3, RoundingMode.HALF_UP) = (0.455 - 0.0011).scale = (0.4539).scale = " + new BigDecimal("0.454545").setScale(3, RoundingMode.HALF_UP).subtract(new BigDecimal("0.0011")).setScale(3, RoundingMode.HALF_UP)); // This is to show that scaling at the end is doing the scaling at the final moment, which might result in a different value then scaling individual values and doing the operations afterwards.
    }
}
