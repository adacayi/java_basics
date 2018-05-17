package com.sanver.basics.locale;

import java.util.Locale;

public class GetDisplayLanguageCountrySample {

	public static void main(String[] args) {
		Locale localeGB = new Locale("en", "GB");// Language and country strings are case insensitive. Language is lower
													// cased and country is upper cased internally. This works new
													// Locale("En", "gB").
		Locale localeFrance = new Locale("fr","FR");
		System.out.println("localeGB.getDisplayLanguage(): " + localeGB.getDisplayLanguage());
		System.out.println("localeGB.getDisplayCountry(): " + localeGB.getDisplayCountry());
		System.out.println("localeGB.getDisplayLanguage(localeFrance): " + localeGB.getDisplayLanguage(localeFrance));
		// The above code shows the display language of localeUK in French (language of
		// localeFrance)
		System.out.println("localeFrance.getDisplayLanguage(localeGB): " + localeFrance.getDisplayLanguage(localeGB));
		// The above code Shows the display language of localeFrance in English
		// (language of localeGB)
		System.out.println("localeGB.getDisplayCountry(localeFrance): " + localeGB.getDisplayCountry(localeFrance));
		System.out.println("localeFrance.getDisplayCountry(localeGB): " + localeFrance.getDisplayCountry(localeGB));
	}
}
