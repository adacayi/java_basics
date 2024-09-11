/**
 * 
 */
package com.sanver.basics.constructors;

class A {
	String name;// if we made this field private we couldn't access it in derived classes.

	public A() {
		name = "Default";
	}

	public A(String name) {
		this(); // this must be the first line of the constructor
		this.name += " Class A " + name;
	}

	public String getString() {
		return name;
	}
}

class B extends A {
	public B(String name, int age) {
		super(name);// Constructor call must be the first statement in a constructor
		this.name += " at age " + age;
	}

	public B(int age) {
		name += " at age " + age;
	}
}

/**
 * @author Abdullah
 *
 */
public class CallingOtherConstructors {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// B b = new B();
		// B b = new B("Ahmet");
		// These give error since there are no constructors in B explicitly defined that
		// has 0 parameters and that takes 1 string parameter.
		// Deriving does not make available the base class constructors automatically.
		B b = new B("Ahmet", 1);
		B b2 = new B(3);// This automatically calls the base class's parameterless constructor. If it
						// had no parameterless constructor that would give an error, and we had to call
						// a constructor explicitly with keyword super i.e. super("Default").

		System.out.println(b.getString());// Despite not being able to use the constructors of the base class
											// automatically we can use the base class's methods (getString() in this
											// example).
		System.out.println(b2.getString());
	}

}
