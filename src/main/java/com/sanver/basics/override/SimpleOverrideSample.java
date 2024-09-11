/**
 * 
 */
package com.sanver.basics.override;


/**
 * @author Abdullah
 *
 */
public class SimpleOverrideSample {
	static class A {
		void getName() {
			System.out.println("A");
			System.out.println();
		}
	}

	static class B extends A {
		@Override
		void getName() {
			System.out.println("B");
			super.getName();
		}
	}

	static class C extends B {
		@Override
		void getName() {
			System.out.println("C");
			super.getName();
		}
	}

	/**
	 * @param args
	 */
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
