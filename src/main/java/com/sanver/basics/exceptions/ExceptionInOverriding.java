package com.sanver.basics.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionInOverriding {
    static class A{
        void process() throws IOException {

        }
    }

    static class B extends A{
        void process() throws FileNotFoundException, RuntimeException { // This can throw IOException, any subclass of IOException, any unchecked exceptions, but cannot throw a superclass of IOException.

        }
    }

    static class C extends B{
        void process() { // This is also possible. The overriding method doesn't necessarily need to throw an exception if the overridden method throws an exception.

        }
    }
}
