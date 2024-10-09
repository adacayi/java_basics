package com.sanver.basics.objects.access;

import com.sanver.basics.objects.A;

/**
 * <a href = https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html>Oracle access level modifiers</a>
 * */
public class AccessModifierSample extends A {
    public static void main(String[] args) {
        var a = new A();
//        a.name = "John"; This cannot be accessed since name is private
//        a.address = "Flat 1"; This cannot be accessed even AccessModifierSample extends A, because protected only allows for accessing the address field of this class instance, not another A instance.
//        new AccessModifierSample().address = "Some address"; This would work
//        a.surname = "Brown"; This cannot be accessed since AccessModifierSample and class A are not in the same package.
        a.age = 15;
        System.out.println(a);

        var b = new B();
        b.address = "Flat 1";// This can be accessed since B and AccessModifierSample are in the same package. Note AccessModifier does not extend B.
        b.surname = "Brown"; // This can be accessed since AccessModifierSample and B are in the same package.
        b.age = 25;
        System.out.println(b);
    }
}
