package com.sanver.basics.override;

public class NoPrivateOverride {

    static class A {
        private void writePrivate() {
            System.out.println("A from private");
        }

        protected void writeProtected() {
            System.out.println("A from protected");
        }

        public void getPrivate() {
            writePrivate();
        }

        public void getProtected() {
            writeProtected();
        }
    }

    static class B extends A {
        private void writePrivate() {
            System.out.println("B from private");
        }

        protected void writeProtected() {
            System.out.println("B from protected");
        }
    }

    public static void main(String[] args) {
        B b = new B();
        b.getPrivate();
        b.writePrivate();
        b.getProtected();
        b.writeProtected();
    }
}
