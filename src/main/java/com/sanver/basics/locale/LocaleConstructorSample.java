package com.sanver.basics.locale;

import java.util.Locale;

public class LocaleConstructorSample {

	public static void main(String[] args) {
		// String codes for language and country are case-insensitive. Internally
		// language is lower cased and country is uppercased.
		Locale localeEnglish = new Locale("en");
		Locale localeUkranian = new Locale("UK");
		// Note that the code above does not generate a locale with country as United
		// Kingdom. It is perceived as a language which is Ukrainian.
		Locale localeGB = new Locale("en", "GB");
		Locale localeNotExisting = new Locale("not existing code");
		show(localeEnglish);
		show(localeUkranian);
		show(localeGB);
		show(localeNotExisting);
	}

	private static void show(Locale locale) {
		LocaleHelper.show(locale);
		System.out.println();
	}

}
