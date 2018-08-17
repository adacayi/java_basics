/**
 * 
 */
package com.sanver.basics.override;

class A {
	void getName() {
		System.out.println("A");
		System.out.println();
	}
}

class B extends A {
	void getName() {
		System.out.println("B");
		super.getName();
	}
}

class C extends B {
	void getName() {
		System.out.println("C");
		super.getName();
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
	}
}
