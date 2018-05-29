package com.sanver.basics.locale;

import java.util.Locale;

public class SetDefaultGetDefaultSample {

	public static void main(String[] args) {
		Locale defaultLocale = Locale.getDefault();
		Locale localeFrance = new Locale("fr", "FR");
		Locale.setDefault(localeFrance);
		LocaleHelper.show(defaultLocale);
		System.out.println();
		LocaleHelper.show(localeFrance);
		System.out.println();
		LocaleHelper.show(Locale.getDefault());
	}
}
