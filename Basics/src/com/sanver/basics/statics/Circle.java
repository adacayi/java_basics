package com.sanver.basics.statics;

public class Circle {
	static final double PI = Math.PI;

	public double getArea(double radius) {
		return 2 * PI * radius * radius;
	}
}
