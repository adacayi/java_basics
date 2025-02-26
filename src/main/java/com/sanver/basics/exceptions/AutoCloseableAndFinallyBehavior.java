package com.sanver.basics.exceptions;

public class AutoCloseableAndFinallyBehavior {
    public static void main(String[] args) {
        try (var obj = new MyClosableClass(1);
             var obj2 = new MyClosableClass(2)) { // last object is closed first.
            obj.myMethod();
        } catch (Exception e) {
            System.out.println(3);
        } finally {
            System.out.println(4);
        }

    }

    static class MyClosableClass implements AutoCloseable {
        final int id;

        public MyClosableClass(final int id) {
            this.id = id;
        }

        void myMethod() {
            System.out.println(5);
            throw new RuntimeException("Some exception");
        }

        @Override
        public void close() {
            System.out.println(id);
        }
    }
}
