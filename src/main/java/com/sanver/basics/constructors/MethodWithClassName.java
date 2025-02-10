package com.sanver.basics.constructors;

public class MethodWithClassName {
    public static void main(String[] args) {
        var result = new A().A();
        System.out.println("A constructed");
        System.out.println(result);
    }

    static class A {
        A() {
            System.out.println("Constructing A");
        }

        String A() { // Method name 'A' is the same as its class name, but this is not a constructor. Although, this is allowed, it should not be used.
            return "This is a method for A, not a constructor";
        }

        void process() {

        }

//        String process() {} // Notice that we cannot have two methods with the same name, parameters but different return types.
//        But for the constructor and a method with the same name and parameters, this is possible as shown above with the A() method returning a String.
    }
}
