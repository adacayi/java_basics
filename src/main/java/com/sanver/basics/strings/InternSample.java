package com.sanver.basics.strings;

public class InternSample {

    public static void main(String[] args) {
        var format = "%-35s : %s%n";

        String str1 = "hello"; // String literals are interned, meaning they share the same reference in memory
        String str2 = "hello"; // String literals are interned, meaning they share the same reference in memory
        System.out.printf(format, "str1 = \"hello\"", str1);
        System.out.printf(format, "str2 = \"hello\"", str2);

        // == returns true because both refer to the same memory location
        System.out.printf(format, "str1 == str2", str1 == str2); // true

        // Create a new String object explicitly (not from the pool)
        String str3 = new String("hello");
        System.out.printf(format,"str3 = new String(\"hello\")", str3 );
        // == returns false because str3 is not from the string pool
        System.out.printf(format, "str1 == str3", str1 == str3); // false

        // If the pool already contains a string equal to this String object as determined by the equals(Object) method,
        // then the string from the pool is returned. Otherwise, this String object is added to the pool and a reference to this String object is returned.
        String str4 = str3.intern(); // Since there is a "hello" in the pool, because of str1, it will return the reference for str1
        System.out.printf(format, "str4 = str3.intern()", str4);
        System.out.printf(format, "str3 == str4", str3 == str4);
        System.out.printf(format,"str1 == str4", str1 == str4); // returns true because str4 refers to the interned string from the pool

        var str5 = new String("hi");
        var str6 = str5.intern(); // Since there is no "hi" in the pol, value of str5 is added to the pool and a reference for it is returned. Note that this reference is not equal to str5.
        var str7 = "hi"; // Since there is now a "hi" in the pool, str7 will point to that, which is what str6 points to as well.
        System.out.printf(format, "str5 = new String(\"hi\")", str5);
        System.out.printf(format, "str6 = str5.intern()", str6);
        System.out.printf(format, "str7 = \"hi\"", str7);
        System.out.printf(format, "str5 == str6", str5  == str6);
        System.out.printf(format, "str6 == str7", str6  == str7);

        // Second Example

        int i = 1;
        var message1 = "some text value" + i; // This is not in the string pool, since "some text value" + i results in a new String object being created at runtime because of string concatenation with a non-constant (`i` is a variable). Change `i` to final to see the different behavior.
        var message2 = "some text value" + i;
        var message3 = "some text value1";
        System.out.printf(format, "message1 = \"some text value\" + i", message1);
        System.out.printf(format, "message2 = \"some text value\" + i", message2);
        System.out.printf(format, "message3 = \"some text value1", message3);
        System.out.printf(format, "message1 == message2", message1 == message2);
        System.out.printf(format, "message1 == message3", message1 == message3);
        System.out.printf(format, "message2 == message3", message2 == message3);
        var message1Intern = message1.intern();
        var message2Intern = message2.intern();
        System.out.printf(format, "message1Intern = message1.intern()", message1Intern);
        System.out.printf(format, "message2Intern = message2.intern()", message2Intern);
        System.out.printf(format, "message1Intern == message3", message1Intern == message3);
        System.out.printf(format, "message2Intern == message3", message2Intern == message3);
        System.out.printf(format, "message1Intern == message1", message1Intern == message1);
        System.out.printf(format, "message2Intern == message2", message2Intern == message2);
        System.out.printf(format, "message1Intern == message2Intern", message1Intern == message2Intern);
    }
}

