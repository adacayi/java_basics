package com.sanver.basics.strings;

public class InternSample {

    public static void main(String[] args) {

        String str1 = "hello"; // String literals are interned, meaning they share the same reference in memory
        String str2 = "hello"; // String literals are interned, meaning they share the same reference in memory

        // == returns true because both refer to the same memory location
        System.out.println("str1 == str2: " + (str1 == str2)); // true

        // Create a new String object explicitly (not from the pool)
        String str3 = new String("hello");

        // == returns false because str3 is not from the string pool
        System.out.println("str1 == str3: " + (str1 == str3)); // false

        // Interning the string places it into the string pool if not already present
        String str4 = str3.intern();

        // == returns true because str4 refers to the interned string from the pool
        System.out.println("str1 == str4: " + (str1 == str4)); // true

        // Second Example

        int i = 1;
        var message1 = "some text value" + i; // This is not in the string pool, since "some text value" + i results in a new String object being created at runtime because of string concatenation with a non-constant (`i` is a variable). Change `i` to final to see the different behavior.
        var message2 = "some text value" + i;
        System.out.println(message1 == message2);
        message1 = message1.intern();
        message2 = message2.intern();
        System.out.println(message1 == message2);
    }
}

