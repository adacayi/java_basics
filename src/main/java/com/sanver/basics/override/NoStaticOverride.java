package com.sanver.basics.override;

class A2 {
    static void writeStatic() {
        System.out.println("A static method");
    }
}

class B2 extends A2 {
}
class C2 extends A2 {
    static void writeStatic() {
        System.out.println("C static method");
    }
}

public class NoStaticOverride {

    public static void main(String[] args) {
        A2.writeStatic();
        B2.writeStatic();
        C2.writeStatic();
        C2 c=new C2();
        c.writeStatic();
        ((A2)c).writeStatic();
    }

}
