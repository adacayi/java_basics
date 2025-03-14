package com.sanver.basics.locale;

import java.util.Locale;

public class BuilderSample {

    public static void main(String[] args) {
        Locale locale = new Locale.Builder().setLanguage("en").setRegion("GB").build();
        show(locale);
        Locale locale2 = new Locale.Builder().setRegion("gb").build();
        show(locale2);
        Locale locale3 = new Locale.Builder().setLanguage("en").build();
        show(locale3);
    }

    private static void show(Locale locale) {
        LocaleHelper.show(locale);// LocaleHelper is my class to show detailed info about locales.
        System.out.println();
    }
}
