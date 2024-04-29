package com.sanver.basics.regex;

import java.util.regex.Pattern;

public class GroupSample {
    // Look at the GroupSampleTest class for tests
    public String findPath(String url) {
        var pattern = Pattern.compile("www\\.(\\w+\\.)+(com|co\\.uk)/?(((\\w+\\.?)+/)*(\\w+\\.?)+)?");
        // Note when we have some text "name surname lastname " and regex ((\\w+ )+), group(1) will be "name surname lastname" (the outer group), group(2) will be the inner group, where inner group is (\\w+ )+, however, that only returns the last matched group which is "lastname ". That is why we need to wrap (\\w+ )+ with (), so we get the complete match.
        var matcher = pattern.matcher(url);

        if(matcher.matches()) { // If we used matcher.find() instead, it would not require the url to match exactly the pattern, hence an invalid url would be able to return path value
            return matcher.groupCount() >= 3 ? matcher.group(3) : null;
        }

        return null;
    }
}
