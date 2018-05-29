package com.sanver.basics.finalkeyword;

public class FinalVariableSample {
	static final double PI;
	final double E = 2.71828;

	static {
		PI = 3.14159265;// Without this line it would give an error.
	}

	public FinalVariableSample() {
		// E = 2.7182818; This line would give an error since the value of e is assigned
		// before. This is not the case with C# readonly.
	}

	public static void main(String[] args) {
		System.out.println(FinalVariableSample.PI);
		System.out.println(new FinalVariableSample().E);
	}
}
