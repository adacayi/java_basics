package com.sanver.basics.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalVariableTypeInference {
    public static void main(String[] args) {
        // Basic type inference with primitive types
        var number = 42; // Infers int, (not Integer, you can check it by converting it to char explicitly, which is not possible for Integer)
        // char c = (char) number; // this works since var infers it as int, not its wrapper type Integer, which we cannot cast to char explicitly or implicitly
        System.out.println("Number: " + number + ", Type: " + ((Object) number).getClass().getName());

        // String type inference
        var message = "Hello, Java!"; // Infers String
        System.out.println("Message: " + message);

        // Complex type with generics
        var list = new ArrayList<String>(); // Infers ArrayList<String>
        list.add("Apple");
        list.add("Banana");
        System.out.println("List: " + list);

        // HashMap with var
        var map = new HashMap<Integer, String>(); // Infers HashMap<Integer, String>
        map.put(1, "One");
        map.put(2, "Two");
        System.out.println("Map: " + map);

        // Using var in a loop
        for (var i = 65; i < 68; i++) { // Infers int for i
            System.out.printf("Loop iteration: %d %s%n", i, (char)i);
        }

        // Combining with object creation
        var demo = new LocalVariableTypeInference(); // Infers LocalVariableTypeInference
        demo.sayHello();
    }

    public void sayHello() {
        System.out.println("Hello from LocalVariableTypeInference instance!");
    }
}
