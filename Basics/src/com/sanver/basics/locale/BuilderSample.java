package com.sanver.basics.locale;

import java.util.Locale;

public class BuilderSample {

	public static void main(String[] args) {
		Locale locale = new Locale.Builder().setLanguage("en").setRegion("GB").build();
		LocaleHelper.show(locale);// LocaleHelper is my class to show detailed info about locales.
		Locale locale2=new Locale.Builder().setRegion("gb").build();
		LocaleHelper.show(locale2);
	}
}
