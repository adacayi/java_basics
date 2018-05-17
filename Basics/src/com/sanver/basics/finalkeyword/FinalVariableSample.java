package com.sanver.basics.finalkeyword;

public class FinalVariableSample {
	final double PI;
	final double e = 2.71828;

	public FinalVariableSample() {
		PI = 3.14159265;
		// e = 2.7182818; This line would give an error since the value of e is assigned
		// before. This is not the case with C# readonly.
	}

	public static void main(String[] args) {
		System.out.println(new FinalVariableSample().PI);
	}
}
