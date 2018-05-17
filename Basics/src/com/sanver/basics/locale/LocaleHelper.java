package com.sanver.basics.locale;

import java.util.Locale;

public class LocaleHelper {
	public static void show(Locale locale) {
		System.out.println("Language: " + locale.getDisplayLanguage());
		System.out.println("Country: " + locale.getDisplayCountry());
		System.out.println("Variant: " + locale.getDisplayVariant());
		System.out.println("Display Name: " + locale.getDisplayName());
	}
}
