package com.sanver.basics.exceptions;

import java.util.Arrays;

public class ExceptionInCatchAndFinallyBlocks {
    public static void main(String[] args) {
        try {
            try {
                A.method(1);
            } catch (Exception e) {
                printExceptionMessages(e);
                A.method(2);
            } finally {
                A.method(3);
            }
        } catch (Exception e) {// Seems like only the last exception thrown from catch and finally block is preserved.
            printExceptionMessages(e);
        }

        System.out.printf("%n%nExample 2%n%n");

        try {
            try(var a = new A(1)) {
                A.method(2);
            } catch (Exception e) { // This exception has the exception thrown from A.method(2) and also contains the exception thrown from the close method of a in its suppressedExceptions.
                printExceptionMessages(e);
                A.method(3);
            } finally {
                A.method(4);
            }
        } catch (Exception e) { // This only contains the exception from the finally block and does not contain the exception from the A.method(3), even inside suppressedExceptions.
            // Seems like only exceptions thrown from close() method are added to suppressedExceptions and only the last exception thrown from catch and finally block is preserved.
            printExceptionMessages(e);
        }
    }

    private static void printExceptionMessages(Exception e) {
        System.out.println(e.getMessage());
        Arrays.stream(e.getSuppressed()).forEach(x-> System.out.println(x.getMessage()));
    }

    private static class A implements AutoCloseable {
        private final int i;

        public A(final int i) {
            this.i = i;
        }

        public static void method(int i) {
            System.out.println(i);
            throw new RuntimeException("Runtime exception " + i);
        }

        @Override
        public void close() {
            System.out.println("Closing " + i);
            throw new RuntimeException("Runtime exception when closing " + i);
        }
    }
}
