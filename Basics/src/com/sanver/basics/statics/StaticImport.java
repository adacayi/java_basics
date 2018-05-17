package com.sanver.basics.statics;

import static java.lang.Math.PI;

public class StaticImport {

	public static double getPerimeter(double radius) {
		return 2 * PI * radius;
	}

	public static void main(String[] args) {
		System.out.println("The perimeter of a radius 1 circle is " + getPerimeter(1));
	}

}
