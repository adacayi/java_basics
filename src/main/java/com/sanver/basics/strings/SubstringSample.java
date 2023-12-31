package com.sanver.basics.strings;

public class SubstringSample {
    public static void main(String[] args) {
        var str = "123456789";
        var result = str.substring(0, 3) + "-" + str.substring(3);
        System.out.println(result);
    }
}
