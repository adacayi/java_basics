package com.sanver.basics.objects.access;

import com.sanver.basics.objects.A;

/**
 * <a href = https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html>Oracle access level modifiers</a>
 * */
public class AccessModifierSample extends A {
    public static void main(String[] args) {
        var a = new A();
//        a.name = "John"; // This cannot be accessed since name is private
//        a.address = "Flat 1"; // This cannot be accessed even AccessModifierSample extends A, because protected only allows for accessing the address field of this class instance, not another A instance.
        new AccessModifierSample().address = "Some address"; // This works because it is an instance of AccessModifierSample
//        new AccessModifierSample().surname = "White"; // This does not work, only public and protected fields are accessible.
//        a.surname = "Brown"; This cannot be accessed since AccessModifierSample and class A are not in the same package.
        a.age = 15;
        System.out.println(a);
        AddressFields fields = a.getAddressFields(); // AddressFields nested class is only accessible if it is defined as public or protected. Protected nested classes are accessible because AccessModifierSample extends A. If AccessModifierSample did not extend, this line would result in a compile error.

        var b = new B();
        b.address = "Flat 1";// This can be accessed since B and AccessModifierSample are in the same package. Note AccessModifier does not extend B.
        b.surname = "Brown"; // This can be accessed since AccessModifierSample and B are in the same package.
        b.age = 25;
        System.out.println(b);
    }
}
