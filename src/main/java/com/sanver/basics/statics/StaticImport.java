package com.sanver.basics.statics;

import static java.lang.Math.PI;// To get all the static fields and methods we can use import static java.lang.Math.*;

public class StaticImport {

	public static double getPerimeter(double radius) {
		return 2 * PI * radius;
	}

	public static void main(String[] args) {
		System.out.println("The perimeter of a circle with radius 1 is " + getPerimeter(1));
	}

}
