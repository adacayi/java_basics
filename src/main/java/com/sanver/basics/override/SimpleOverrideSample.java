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

	static class D{
		Number getNumber() {
			return 1;
		}

		int getPrimitiveNumber() {
			return 10;
		}

		static Number getStaticNumber() {
			return 30;
		}

		static int getStaticPrimitiveNumber() {
			return 40;
		}
	}

	static class E extends D{
		Integer getNumber() { // Overriding method return type can only be a covariant return type of the overridden method.
			// Boxing/unboxing, implicit conversion are not allowed.
			return 2;
		}

		//		Object getNumber() {return new Object();} // This won't compile
//		int getNumber() {return  1;} // This does not work.
//		byte getPrimitiveNumber() {return 3;} // This does not work, even though byte can be implicitly converted to int.
//		long getPrimitiveNumber() {return 3;} // This does not work.

		static Integer getStaticNumber() { // There is no static method override, check NoStaticOverride,
			// but still static methods should have the covariant return type of the static method in the base class they are hiding (not overriding).
			// Boxing/unboxing, implicit conversion are not allowed.
			return 3;
		}

//		static Object getStaticNumber() { return 3;} // This won't compile
//		static int getStaticNumber() { return 3;} // This won't compile
//		static byte getStaticPrimitiveNumber() { return 3;} // This won't compile
//		static long getStaticPrimitiveNumber() { return 3;} // This won't compile

	}

	static class F extends E{
//		int getNumber() {return 50;} // This does not work, although the parent method return type is Integer
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
