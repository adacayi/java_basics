package com.sanver.basics.statics;

public class AccessingStaticMembersFromInstanceObjects {
    static int value = 5;
    public static void main(String[] args) {
        System.out.println(value);
        System.out.println(AccessingStaticMembersFromInstanceObjects.value);
        System.out.println(new AccessingStaticMembersFromInstanceObjects().value);
        AccessingStaticMembersFromInstanceObjects a = null;
        System.out.println(a.value); // Even though "a" is null, the compiler creates the instruction for the JVM to access this field directly using the class reference instead of the object reference.
    }
}
