package com.sanver.basics.locale;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleSample {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("de", "CH"));
		var bundle = ResourceBundle.getBundle("com.sanver.basics.locale.SRBundel", Locale.of("en")); // Since there is no SRBundle_en and SRBundle_en_En is not the parent of SRBundle_en, it will try to get the resource based on the default locale which is set to Locale.of("de", "CH").
		System.out.println(MessageFormat.format(bundle.getString("Hello"), "Bob"));
		System.out.println();
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
		bundle.keySet().forEach(k -> System.out.printf("Key: %-5s Value: %s%n", k, bundle.getString(k)));
		System.out.println(MessageFormat.format(bundle.getString("Hello"), "Bob"));
		System.out.println(MessageFormat.format(bundle.getString("Bye"), "Bob")); // Since Bye key does not exist, it will fall to SRBundel, not the default locale bundle class (i.e. SRBundel_en_GB).
//		System.out.println(MessageFormat.format(bundle.getString("Non-Existent"), "Bob")); // Since this key doesn't exist in the locale bundle and SRBundel, this will result in java.util.MissingResourceException: Can't find resource for bundle com.sanver.basics.locale.SRBundel, key Non-Existent
		System.out.println();
	}
}
