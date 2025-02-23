package com.sanver.basics.codeblocks;

/**
 * <p>
 * Labels are identifiers followed by a colon (:) that mark specific control flow statements
 * (loops and blocks). They provide additional control over program flow, particularly in
 * nested structures.
 * </p>
 *
 * <h2>Primary Use Cases</h2>
 * <ol>
 *   <li><b>Breaking Outer Loops:</b> Most common use case for breaking out of nested loops.
 *     <pre>
 *     outerLoop:
 *     for (int i = 0; i < 5; i++) {
 *         for (int j = 0; j < 5; j++) {
 *             if (condition) break outerLoop; // Exits both loops
 *         }
 *     }
 *     </pre>
 *   </li>
 *   <li><b>Continuing Outer Loops:</b> Skipping to the next iteration of a labeled outer loop.
 *     <pre>
 *     outerLoop:
 *     for (int i = 0; i < 5; i++) {
 *         for (int j = 0; j < 5; j++) {
 *             if (condition) continue outerLoop; // Continues outer loop
 *         }
 *     }
 *     </pre>
 *   </li>
 *   <li><b>Labeled Blocks:</b> Less common usage for labeling code blocks to break out.
 *     <pre>
 *     blockLabel: {
 *         if (condition) {
 *             break blockLabel; // Exits the block
 *         }
 *         // More code
 *     }
 *     </pre>
 *   </li>
 * </ol>
 *
 * <h2>Usage Notes</h2>
 * <ul>
 *   <li>Labels are case-sensitive</li>
 *   <li>Should follow Java naming conventions (typically camelCase)</li>
 *   <li>Can only be used with break and continue statements</li>
 *   <li>Cannot break or continue across method boundaries</li>
 * </ul>
 *
 * <h2>Best Practices</h2>
 * <ol>
 *   <li>Use sparingly - overuse can lead to confusing code flow</li>
 *   <li>Consider refactoring complex nested loops into separate methods</li>
 *   <li>Choose descriptive label names that indicate their purpose</li>
 *   <li>Document the purpose of labels in complex scenarios</li>
 * </ol>
 *
 * <h2>Common Anti-patterns</h2>
 * <ol>
 *   <li>Using labels as a substitute for proper method decomposition</li>
 *   <li>Creating too many labeled blocks in a single method</li>
 *   <li>Using labels for simple single-loop scenarios where they're unnecessary</li>
 * </ol>
 *
 * <h2>Limitations</h2>
 * <ul>
 *   <li>Cannot break or continue to labels in different scopes</li>
 *   <li>Labels must be in scope of the break/continue statement</li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.7">
 * Java Language Specification - Labeled Statements</a>
 */
public class LabelingCodeBlocks {
    public static void main(String[] args) {

        label1:
        {
            if (true) {
                break label1;
            }
            System.out.println("Not broken");
        }

        System.out.println("Broken");

        label2:
        System.out.println("Labeling is allowed before any statement");
        if (true) {
//            break label2; // This won't compile, since label2 does not refer to a code block or a loop
        }

        label3:
        switch (1) {
            case 1:
                System.out.println("Case 1");
//                break label3; // This would work
                for (int i = 0; i < 5; i++) {
                    if (i == 2) {
                        break label3; // If we used break instead, it would only break the for.
                    }
                }
            case 2:
                System.out.println("Case 2");
        }

        label4:
        {
            int i = 0;
            try {
                for (; i < 1000; i++) {
                    if (i > 5) {
                        break label4;
                    }
                }
                System.out.println("Not broken");
            } finally {
                System.out.println("Finally block executed"); // This is to show that finally will be executed. The only case finally is not executed is when System.exit() is called, which stops the application, thus the finally block cannot be executed afterward.
            }
        }

        System.out.println("Block label4 ended");
    }
}
