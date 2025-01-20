package com.sanver.basics.basicoperators;

/**
 * This class demonstrates the differences between three ways of calculating the average of two integers:
 * - (a + b) / 2  : Uses standard signed division.
 * - (a + b) >> 1 : Uses arithmetic right shift (preserves sign).
 * - (a + b) >>> 1: Uses logical right shift (ignores sign, treats as unsigned).
 *
 * <p>The behavior of these expressions differs particularly for negative numbers and integer overflow cases.</p>
 *
 * <h2>Comparison Table</h2>
 * <table border="1">
 *   <tr>
 *     <th>a</th>
 *     <th>b</th>
 *     <th>(a + b) / 2</th>
 *     <th>(a + b) >> 1</th>
 *     <th>(a + b) >>> 1</th>
 *   </tr>
 *   <tr>
 *     <td>6</td>
 *     <td>5</td>
 *     <td>5</td>
 *     <td>5</td>
 *     <td>5</td>
 *   </tr>
 *   <tr>
 *     <td>-6</td>
 *     <td>-5</td>
 *     <td>-5</td>
 *     <td>-6</td>
 *     <td>2,147,483,642</td>
 *   </tr>
 *   <tr>
 *     <td>Integer.MAX_VALUE</td>
 *     <td>Integer.MAX_VALUE</td>
 *     <td>-1 (overflow)</td>
 *     <td>-1 (overflow)</td>
 *     <td>Integer.MAX_VALUE</td>
 *   </tr>
 *   <tr>
 *     <td>Integer.MAX_VALUE</td>
 *     <td>1</td>
 *     <td>-1,073,741,824 (overflow)</td>
 *     <td>-1,073,741,824</td>
 *     <td>1,073,741,824</td>
 *   </tr>
 *   <tr>
 *     <td>Integer.MIN_VALUE</td>
 *     <td>-1</td>
 *     <td>1,073,741,823</td>
 *     <td>1,073,741,823</td>
 *     <td>1,073,741,823</td>
 *   </tr>
 * </table>
 *
 * <h3>Key Takeaways:</h3>
 * <ul>
 *   <li><code>(a + b) / 2</code> can overflow and result in incorrect values.</li>
 *   <li><code>(a + b) >> 1</code> can handle negative values correctly due to sign preservation. Rounds down for negative values.</li>
 *   <li><code>(a + b) >>> 1</code> handles positive value overflow correctly, thus also preferred for binary search. Check {@code Collections.binarySearch()}</li>
 * </ul>
 */
public class CalculatingAverageOfTwoIntegers {

    /**
     * Demonstrates the difference between (a + b) / 2, (a + b) >> 1, and (a + b) >>> 1.
     * @param a First integer
     * @param b Second integer
     */
    public static void compareAverages(int a, int b) {
        int div = (a + b) / 2;
        int arithShift = (a + b) >> 1;
        int logicalShift = (a + b) >>> 1;

        System.out.printf("a = %,d, b = %,d%n", a, b);
        System.out.printf("(a + b) /   2 = % ,d%n", div);
        System.out.printf("(a + b) >>  1 = % ,d%n", arithShift);
        System.out.printf("(a + b) >>> 1 = % ,d%n", logicalShift);
        System.out.println("------------------------------------");
    }

    public static void main(String[] args) {
        // Test Cases
        compareAverages(6, 5);
        compareAverages(-6, -5);
        System.out.printf("Integer.MAX_VALUE = %,d%n", Integer.MAX_VALUE);
        compareAverages(Integer.MAX_VALUE, Integer.MAX_VALUE);
        System.out.printf("Integer.MIN_VALUE / 2 = %,d%n", Integer.MIN_VALUE /2);
        compareAverages(Integer.MAX_VALUE, 1);
        compareAverages(Integer.MIN_VALUE, -1);
    }
}

