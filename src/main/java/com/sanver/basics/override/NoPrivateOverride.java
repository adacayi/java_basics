package com.sanver.basics.override;

class A1 {
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

class B1 extends A1 {
    private void writePrivate() {
        System.out.println("B from private");
    }

    protected void writeProtected() {
        System.out.println("B from protected");
    }
}

public class NoPrivateOverride {

    public static void main(String[] args) {
        B1 b = new B1();
        b.getPrivate();
        b.getProtected();
    }

}
