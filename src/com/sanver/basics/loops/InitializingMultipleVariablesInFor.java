package com.sanver.basics.loops;

public class InitializingMultipleVariablesInFor {

	public static void main(String[] args) {
		for (int i = 1, j = 3; i < j; i++)
			System.out.println("i= " + i);
		// For different types of variables only arrays of same kind of variables are
		// approved
		// Hence this won't compile for (int i = 1,long j = 3; i < j; i++)
		// Event this won't compile for (int i = 1,int j = 3; i < j; i++)
		for (int i = 1, j[] = { 3 }; i < j[0]; i++)
			System.out.println("i= " + i);
	}
}
