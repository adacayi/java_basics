/**
 * 
 */
package com.sanver.basics.variable_scope;

/**
 * @author Abdullah
 *
 */
public class VariableScopeSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int value = 3;

		if (value > 2) {
			int second = 2;
			System.out.println(second);
		}

		// System.out.println(second); // This line would give an error, since second is
		// not defined in this scope yet.
		int second = 3;
		System.out.println(second);
	}

}
