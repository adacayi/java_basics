package com.sanver.basics.locale;

import java.util.Locale;

public class GetDisplayLanguageCountrySample {

    public static void main(String[] args) {
        var format = "%-41s : %s%n";
        Locale localeGB = new Locale("en", "GB");// Language and country strings are case-insensitive.
        // Language is lower cased and country is uppercased internally. This works new Locale("En", "gB").
        Locale localeFrance = new Locale("fr", "FR");
        System.out.printf(format, "localeGB = new Locale(\"en\", \"GB\")", localeGB);
        System.out.printf(format, "localeFrance = new Locale(\"fr\", \"FR\")", localeFrance);
        System.out.printf(format, "localeGB.getDisplayLanguage()", localeGB.getDisplayLanguage());
        System.out.printf(format, "localeGB.getDisplayCountry()", localeGB.getDisplayCountry());
        System.out.printf(format, "localeGB.getDisplayLanguage(localeFrance)", localeGB.getDisplayLanguage(localeFrance));
        // The above code shows the display language of localeGB in French (language of localeFrance)
        System.out.printf(format, "localeFrance.getDisplayLanguage(localeGB)", localeFrance.getDisplayLanguage(localeGB));
        // The above code shows the display language of localeFrance in English (language of localeGB)
        System.out.printf(format, "localeGB.getDisplayCountry(localeFrance)", localeGB.getDisplayCountry(localeFrance));
        System.out.printf(format, "localeFrance.getDisplayCountry(localeGB)", localeFrance.getDisplayCountry(localeGB));
        System.out.printf(format, "localeFrance.getDisplayCountry()", localeFrance.getDisplayCountry());
    }
}
