package com.sanver.basics.overload;

class ParentClass {
}

class ChildClass extends ParentClass {
}

class GrandChild extends ChildClass {
}

public class OverloadSample {
    public void print(ParentClass value) {
        System.out.println("ParentClass print method");
    }

    public void print(ChildClass value) {
        System.out.println("ChildClass print method");
    }

    public static void main(String[] args) {
        new OverloadSample().print(new GrandChild());
    }
}
