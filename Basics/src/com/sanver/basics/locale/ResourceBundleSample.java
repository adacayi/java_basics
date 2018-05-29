package com.sanver.basics.locale;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleSample {

	public static void main(String[] args) {
		Locale[] locales = { Locale.getDefault(), new Locale("de", "ch"), new Locale("hi", "in") };
		Arrays.asList(locales).forEach(l -> showBundle(l));
	}

	public static void showBundle(Locale locale) {
		LocaleHelper.show(locale);
		ResourceBundle bundle = ResourceBundle.getBundle("com.sanver.basics.locale.SRBundel", locale);// We have to use
																										// package name
																										// plus class
																										// name to
																										// access the
																										// bundle class
		System.out.println();
		bundle.keySet().forEach(k -> System.out.printf("Key: %s Value: %s\n", k, bundle.getString(k)));
		System.out.println();
	}
}
