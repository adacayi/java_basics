package com.sanver.basics.overload;

import java.util.Arrays;

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

//    public <T extends ChildClass> void print(T value){} // This has the same signature as the public void print(ChildClass value) and will cause a compile error
//    public <T super ChildClass> void print(T value){} // super is not allowed here

    public void print(ChildClass value, String... params) {
        System.out.println("Printing ChildClass with parameters");
        System.out.println(Arrays.toString(params));
    }

    public OverloadSample() {
        System.out.println("Constructor with no parameters");
    }

    public OverloadSample(String... args) { // Constructor with varargs is allowed
        System.out.println("Constructor with varargs");
    }

//    public OverloadSample(String[] args) {} // This has the same method signature with the above public OverloadSample(String... args) constructor

    public static void main(String[] args) {
        new OverloadSample().print(new GrandChild());
    }
}
