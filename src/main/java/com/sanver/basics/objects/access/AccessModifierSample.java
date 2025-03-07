package com.sanver.basics.objects.access;

import com.sanver.basics.objects.A;

/**
 * Demonstrates the use of access level modifiers in Java, illustrating
 * how they control the visibility and accessibility of fields and methods
 * within and across different packages. This class specifically explores
 * the access modifiers {@code public}, {@code protected}, {@code package-private (default)}, and
 * {@code private}, showcasing their behavior when used with classes and their members.
 * <p>
 * This class, {@code AccessModifierSample}, extends class {@link com.sanver.basics.objects.A} to provide
 * examples of {@code protected} access across packages.
 *
 * <p>
 * For a comprehensive overview of access control in Java, refer to the Oracle documentation:
 * <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html">Oracle access level modifiers</a>
 *
 * <p>
 * <b>Access Modifiers in Java:</b>
 *
 * <ul>
 *   <li><b>{@code public}:</b>
 *     <ul>
 *       <li>Accessible from anywhere, both within the same package and from other packages.</li>
 *       <li>Applicable to classes, interfaces, methods, and fields.</li>
 *       <li>Top-level classes can only be public or package-private.</li>
 *     </ul>
 *   </li>
 *   <li><b>{@code protected}:</b>
 *     <ul>
 *       <li>Accessible within the same package.</li>
 *       <li>Accessible by subclasses in different packages through inheritance.</li>
 *       <li>A subclass can access the protected members of its superclass on instances of the subclass, not of the superclass.</li>
 *        <li>Applicable to inner classes, methods, and fields.</li>
 *        <li>Top-level classes cannot be protected.</li>
 *     </ul>
 *   </li>
 *   <li><b>Package-private (default - no modifier):</b>
 *     <ul>
 *       <li>Accessible only within the same package.</li>
 *       <li>Applicable to classes, interfaces, methods, and fields.</li>
 *     </ul>
 *   </li>
 *   <li><b>{@code private}:</b>
 *     <ul>
 *       <li>Accessible only within the same class.</li>
 *       <li>Applicable to inner classes, methods, and fields.</li>
 *       <li>Top-level classes cannot be private.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <p>
 * <b>Key Observations:</b>
 * <ul>
 *   <li>{@code private} members (like {@code name} in class {@link com.sanver.basics.objects.A}) are not accessible outside the class,
 *   even from subclasses or classes in the same package.</li>
 *   <li>{@code protected} members (like {@code address} in class {@link com.sanver.basics.objects.A}) can be accessed by subclasses in
 *   different packages (like {@code AccessModifierSample} extending {@link com.sanver.basics.objects.A}), but only on instances of the subclass or its subtypes.</li>
 *   <li>Package-private members (like {@code surname} in class {@link com.sanver.basics.objects.A} and {@link com.sanver.basics.objects.access.B}) are accessible
 *   to any class within the same package, regardless of inheritance.</li>
 *   <li>{@code public} members (like {@code age} in class {@link com.sanver.basics.objects.A} and {@link com.sanver.basics.objects.access.B}) are accessible from anywhere.</li>
 *   <li>{@code AddressFields} nested class which is {@code protected} in class {@link com.sanver.basics.objects.A} is only accessible because
 *   {@code AccessModifierSample} extends {@link com.sanver.basics.objects.A}</li>
 * </ul>
 *
 * <p>
 * <b>Class Relationships:</b>
 * <ul>
 *   <li>{@link com.sanver.basics.objects.A}: A class used to demonstrate access modifier behavior.</li>
 *   <li>{@link B}: A class in the same package as {@code AccessModifierSample}.</li>
 * </ul>
 */
public class AccessModifierSample extends A {
    public static void main(String[] args) {
        var a = new A();
//        a.name = "John"; // This cannot be accessed since name is private
//        a.address = "Flat 1"; // This cannot be accessed even AccessModifierSample extends A, because protected only allows for accessing the address field of this class instance, not another A instance.
        new AccessModifierSample().address = "Some address"; // This works because it is an instance of AccessModifierSample
//        new AccessModifierSample().surname = "White"; // This does not work, since surname is package-private in class A, and class A is in a different package.
//        a.surname = "Brown"; This cannot be accessed since AccessModifierSample and class A are not in the same package.
        a.age = 15;
        System.out.println(a);
        AddressFields fields = a.getAddressFields(); // AddressFields nested class is only accessible if it is defined as public or protected (package private will not work since A and AccessModifierSample are not in the same package).
        // Protected nested classes are accessible because AccessModifierSample extends A.
        // If AccessModifierSample did not extend, this line would result in a compile error.

        var b = new B();
        b.address = "Flat 1";// This can be accessed since B and AccessModifierSample are in the same package. Note that AccessModifier does not extend B.
        b.surname = "Brown"; // This can be accessed since AccessModifierSample and B are in the same package.
        b.age = 25;
        System.out.println(b);

        B.AddressFields fieldsB = b.getAddressFields(); // B.AddressFields nested class is only accessible if it is defined as public, protected or package-private.
    }
}
