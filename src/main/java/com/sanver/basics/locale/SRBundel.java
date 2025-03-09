package com.sanver.basics.locale;

import java.util.ListResourceBundle;

public class SRBundel extends ListResourceBundle { // It is necessary for the class to be public. Also be careful to
													// return resource array without null keys.
	public Object[][] getContents() {
		Object[][] resources = new Object[2][2];
		resources[0][0] = "Hello";
		resources[0][1] = "Hello {0}";
		resources[1][0] = "Bye";
		resources[1][1] = "Bye {0}";
		return resources;
	}
}