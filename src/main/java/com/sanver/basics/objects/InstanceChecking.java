package com.sanver.basics.objects;

/**
 * The <code>instanceof</code> construction is a preferred way to check whether a variable can be cast to some type statically
 * because a compile-time error will occur in case of incompatible types.
 * The method <code>isInstance()</code> from java.lang.Class works differently and does type check at runtime only, incompatible types will therefore not be detected early in the development, potentially resulting in dead code.
 * The <code>isInstance()</code> method should only be used in dynamic cases when the instanceof operator can’t be used.
 */
public class InstanceChecking {
    public static void main(String[] args) {
        Integer i = 5;
        int j = 10;
        printInstanceInformation(i);
        printInstanceInformation(j);
    }

    private static void printInstanceInformation(Integer i) {
        var format = "%-54s: %s%n";
        System.out.printf("i is Integer%n%n");
        System.out.printf(format, "i instanceof Object", i instanceof Object);
        System.out.printf(format, "Object.class.isInstance(i)", Object.class.isInstance(i)); // if this Class object (Object.class in our example) represents a declared class, this method returns true if the specified Object argument (i in our case) is an instance of the represented class (or of any of its subclasses); it returns false otherwise.
        System.out.printf(format, "Object.class.isAssignableFrom(i.getClass())", Object.class.isAssignableFrom(i.getClass()));// This method tests whether the type represented by the specified Class parameter (i.getClass() in this example) can be converted to the type represented by this Class (Object.class in this example) object via an identity conversion or via a widening reference conversion.
        System.out.printf(format, "i.getClass().isInstance(new Object())", i.getClass().isInstance(new Object()));
        System.out.printf(format, "i.getClass().isAssignableFrom(new Object().getClass())", i.getClass().isAssignableFrom(new Object().getClass()));
    }

    private static void printInstanceInformation(int i) {
        var format = "%-54s: %s%n";
        System.out.printf("%ni is int%n%n");
        //System.out.printf(format, "i instanceof Object", i instanceof Object); // This does not compile
        System.out.printf(format, "Object.class.isInstance(i)", Object.class.isInstance(i)); // if this Class object (Object.class in our example) represents a declared class, this method returns true if the specified Object argument (i in our case) is an instance of the represented class (or of any of its subclasses); it returns false otherwise.
//        System.out.printf(format, "Object.class.isAssignableFrom(i.getClass())", Object.class.isAssignableFrom(i.getClass()));// This does not compile since int is primitive and does not have getClass method.
//        System.out.printf(format, "i.getClass().isInstance(new Object())", i.getClass().isInstance(new Object())); // This does not compile
//        System.out.printf(format, "i.getClass().isAssignableFrom(new Object().getClass())", i.getClass().isAssignableFrom(new Object().getClass())); // This does not compile
    }
}
