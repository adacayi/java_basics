package com.sanver.basics.codeblocks;


/**
 * Interface with default method to demonstrate interface initialization
 */
interface InterfaceWithDefault {
    default void defaultMethod() {
        System.out.println("Default method called");
    }
}

/**
 * Represents a class demonstrating initialization rules as specified in
 * Section 12.4.1 of the Java Language Specification (JLS).
 *
 * <p>A class or interface type T will be initialized immediately before the first
 * occurrence of any one of the following conditions:</p>
 * <ul>
 *     <li>T is a class and an instance of T is created</li>
 *     <li>A static method declared by T is invoked</li>
 *     <li>A static field declared by T is assigned</li>
 *     <li>A static field declared by T is used and the field is not a constant variable (i.e. not marked final)</li>
 * </ul>
 *
 * <p>When a class is initialized:</p>
 * <ul>
 *     <li>Its superclasses are initialized (if not previously initialized)</li>
 *     <li>Any superinterfaces declaring default methods are initialized
 *         (if not previously initialized)</li>
 *     <li>Initialization of an interface does not trigger initialization of its superinterfaces</li>
 * </ul>
 *
 * <p>Additional initialization rules:</p>
 * <ul>
 *     <li>A static field reference only initializes the class or interface that
 *         declares it, regardless of whether it's accessed through a subclass,
 *         subinterface, or implementing class</li>
 *     <li>Certain reflective methods in {@link java.lang.Class} and
 *         {@link java.lang.reflect} package can trigger initialization</li>
 *     <li>No initialization occurs under any other circumstances</li>
 * </ul>
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se21/html/jls-12.html#jls-12.4.1">
 * JLS Section 12.4.1: Class Initialization</a>
 */
public class ClassInitialization {
    static int get(String variable, int value) {
        System.out.printf("Setting %s to %d%n", variable, value);
        return value;
    }

    public static void main(String[] args) {
        System.out.println("Main method started");

        // This will NOT trigger initialization
        System.out.println("CONSTANT_VALUE: " + SubClass.CONSTANT_VALUE);

        // These WILL trigger initialization (Comment out the previous codes to see initialization for each case
        System.out.println("SECOND_COUNTER value: " + SubClass.SECOND_COUNTER);
        System.out.println("Constant value: " + SubClass.getConstantValue());
        System.out.println("--- Creating instance ---");
        new SubClass();

        System.out.println("--- Calling static method ---");
        SubClass.staticMethod();

        System.out.println("--- Accessing static field ---");
        System.out.println("Counter: " + SubClass.getCounter());

        System.out.println("--- Assigning static field ---");
        SubClass.setCounter(10);

        // Interface initialization
        System.out.println("--- Interface initialization ---");
        System.out.println("C.a = " + C.a);
        System.out.println("C.b = " + C.b); // Only initializes B, since bb static field is defined in the B interface.
        System.out.println("C.b = " + C.bb);
        System.out.println("C.cc = " + C.cc);
    }

    interface A {
        int a = 1; // Accessing this won't trigger any initialization, since this is a compile time constant.
        int aa = get("aa", 2); // Although this is also public static final, since it is value is not defined at compile time, accessing this triggers initialization
    }

    interface B extends A {
        int b = get("b", 3);
        int bb = get("bb", 4); // This will trigger initialization, but unlike classes, it won't trigger super interfaces' initializations.
    }

    interface C extends B {
        int c = get("c", 6);
        int cc = get("cc", 7);
    }
}

/**
 * Superclass to demonstrate inheritance initialization
 */
class SuperClass {
    static {
        System.out.println("SuperClass static code block is executed");
    }

    {
        System.out.println("SuperClass non-static code block is executed");
    }
}

class SubClass extends SuperClass implements InterfaceWithDefault {
    // Accessing this will trigger initialization
    static final int SECOND_COUNTER = initializeCounter2(); // Even though this is final, its value is not defined in compile time.
    // Constant field (won't trigger initialization when referenced)
    static final int CONSTANT_VALUE = 42;
    // Static field (value not defined in compile time)
    static int counter = initializeCounter();

    // Static block to demonstrate initialization
    static {  // Code execution  order in static blocks and static field initialization is dependent on the order they are written.
        // Because of that we cannot access `counter` or `counter2` at this point.
//        System.out.println(counter); // This would result in a compile error.
        System.out.println("SubClass static code block is executed");
    }

    String field1 = getField1Value();

    {
        System.out.println("SubClass non-static code block is executed. field1 value = " + field1); // Note that if this block is to be defined above the field1, we won't be able to access it.
    }

    /**
     * Constructor that triggers initialization through instance creation
     */
    public SubClass() {
        System.out.println("Instance of SubClass created. field1 = " + field1); // Even though field1 is defined below, unlike the non-static code blocks, we can access its value here, because the constructor is executed after field values are initialized.
    }

    /**
     * Static method that triggers initialization when invoked
     */
    public static void staticMethod() {
        System.out.println("Static method called");
    }

    /**
     * Returns the counter value, triggers initialization when accessed
     *
     * @return current counter value
     */
    public static int getCounter() {
        return counter;
    }

    /**
     * Sets the counter value, triggers initialization when assigned
     *
     * @param value new counter value
     */
    public static void setCounter(int value) {
        counter = value;
    }

    /**
     * Returns the constant value, also trigger initializations
     *
     * @return constant value
     */
    public static int getConstantValue() {
        return CONSTANT_VALUE;
    }

    static int initializeCounter() {
        System.out.println("Static field counter being initialized");
        return 0;
    }

    static int initializeCounter2() {
        System.out.println("Static field counter2 being initialized");
        return 1;
    }

    String getField1Value() {
        System.out.println("Non-static field value is calculated");
        return "field1 value";
    }
}
