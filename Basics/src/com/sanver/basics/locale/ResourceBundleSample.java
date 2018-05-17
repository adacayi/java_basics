package com.sanver.basics.locale;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

class SRBundel extends ListResourceBundle {
	public Object[][] getContents() {
		Object[][] resources = new Object[2][2];
		resources[0][0] = "Hello";
		resources[0][1] = "Hello";
		return resources;
	}
}

class SRBundel_de_CH extends ListResourceBundle {
	protected Object[][] getContents() {
		Object[][] resources = new Object[2][2];
		resources[0][0] = "Hello";
		resources[0][1] = "Guten Tag";
		return resources;
	}
}

class SRBundel_hi_IN extends ListResourceBundle {
	protected Object[][] getContents() {
		Object[][] resources = new Object[2][2];
		resources[0][0] = "Hello";
		resources[0][1] = "Namaste";
		return resources;
	}
}

public class ResourceBundleSample {

	public static void main(String[] args) throws ClassNotFoundException {
		
		// I couldn't make ResourceBundle work. I have to search thoroughly.
		Locale indian = new Locale("hi");
		LocaleHelper.show(indian);
		ResourceBundle bundle = ResourceBundle.getBundle("SRBundel", indian);
		System.out.println(bundle.keySet());
	}
}
