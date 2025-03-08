package com.sanver.basics.exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionHandlingAndTryWithResources {
    public static void main(String[] args) {
        try (FileInputStream stream = new FileInputStream("non-existing-file.txt")) {
        } catch (
                FileNotFoundException e) { // If we had this after the catch(IOException) it would result in a "FileNotFoundException has already been caught" error, because catch(IOException) would have caught it.
            System.out.println("FileNotFoundException: " + e.getMessage());
        } catch (
                IOException e1) { // This block will not be executed since new FileInputStream("non-existing-file.txt") throws a FileNotFoundException in this case, and it is caught in the first catch block.
            System.out.println("IOException: " + e1.getMessage());
        } finally {
            // stream.close(); // This line would give a compile error, since stream is only accessible from within the try with resources block.
            System.out.println("Process finished");
        }

        System.out.println();

        A a1 = new A(1);
        A a2 = new A(2);
        try (a1; a2) { // This is a valid try-with-resources usage and since no exception is thrown from the close method of A, we don't need a catch block
            // Note that a1.close() and a2.close() will be invoked in reverse order.

        }
        System.out.println();

        try (a2; var a3 = new A(3)) { // This is also valid

        }
    }

    static class A implements AutoCloseable {
        private int value;

        public A() {

        }

        public A(int value) {
            this.value = value;
        }

        public void close() {
            System.out.println("Closing the instance " + value);
        }
    }
}
