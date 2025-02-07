package com.sanver.basics.console;

import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

/**
 * This class is to show how to move the cursor.
 * <p>If run in IntelliJ only {@code \r} works to move the cursor to the start of the line.
 * {@code \033[F} and other ANSI escape characters do not move the cursor in IntelliJ.
 * For that, run the code from the terminal.
 * <h4>Ways of running from terminal</h2>
 * <ol>
 *     <li>Go to \Basics\target\classes and type
 *          <pre>
 *          {@code java com.sanver.basics.console.MoveCursorSample}
 *          </pre>
 *     </li>
 *     <li> Go to \Basics and type
 *          <pre>
 *          {@code java -cp target/classes com.sanver.basics.console.MoveCursorSample}
 *          </pre>
 *     </li>
 * </ol>
 * </p>
 */
public class MoveCursorSample {
    public static void main(String[] args) {
        // Print "Process Started" on the first line
        System.out.print("Countdown will start in 3 seconds");
        sleep(3000);
        System.out.print("\r");
        System.out.println("Countdown started                "); // Extra spaces put to overwrite "art in 3 seconds" part of the previous message
        System.out.print("Count:   ");
        // Simulate processing
        for (int i = 10; i >= 0; i--) {
            // Move the cursor back to the end of the "Count: " line
            moveLeft(2);
            System.out.printf("%2d",i);
            sleep(500);
        }

        // Move the cursor up one line to overwrite "Countdown Started"
        System.out.print("\033[F"); // The ANSI escape code \033[F moves the cursor up by one line and positions it at the beginning of that line. Use "\033[A" to just move the cursor up one line.
        moveRight( "CountDown".length() + 1);
        System.out.print("Finished"); // Overwrite the first line
        System.out.print("\033[E"); // The ANSI escape code \033[E moves the cursor down by one line and positions it at the beginning of that line. Use "\033[B" to just move the cursor down one line.
        moveRight("Count:  0".length());
        System.out.println(". Done!");
    }

    /**
     * Moves the cursor left for the requested times without overwriting any existing output.
     * @param times The times to move the cursor to left
     */
    private static void moveLeft(int times) {
        IntStream.range(0, times).forEach(x-> System.out.print("\033[D"));
    }

    /**
     * Moves the cursor right for the requested times without overwriting any existing output.
     * @param times The times to move the cursor to right
     */
    private static void moveRight(int times) {
        IntStream.range(0, times).forEach(x-> System.out.print("\033[C"));
    }
}
