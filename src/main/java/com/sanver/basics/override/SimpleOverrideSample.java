/**
 * 
 */
package com.sanver.basics.override;

class A {
	void getName() {
		System.out.println("A");
		System.out.println();
	}

	static void getStatic() {
		System.out.println("A Static");
	}
}

class B extends A {
	void getName() {
		System.out.println("B");
		super.getName();
	}

	static void getStatic() {
		System.out.println("B Static");
	}
}

class C extends B {
	void getName() {
		System.out.println("C");
		super.getName();
	}

	static void getStatic() {
		System.out.println("C Static");
	}
}

/**
 * @author Abdullah
 *
 */
public class SimpleOverrideSample {

	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		C c = new C();
		A d = new C();
		a.getName();
		b.getName();
		c.getName();
		d.getName();
		((A) b).getName();
		((A) c).getName();
		System.out.println("For statics: ");
		a.getStatic();
		b.getStatic();
		c.getStatic();
		d.getStatic();
		((A) b).getStatic();
		((A) c).getStatic();
	}
}
