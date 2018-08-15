package com.sanver.basics.strings;

public class StringBufferSample {

	public static void main(String[] args) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Abdullah");
		buffer.append(" Sanver");
		System.out.println(buffer.toString());
		System.out.println(buffer.charAt(3));
	}
}
