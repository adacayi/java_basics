package com.sanver.basics.streamapi;

public class CharsSample {
    public static void main(String[] args) {
        System.out.println("Note that since we don't have a CharStream of primitive char type, chars() returns an IntStream instead.");
        var sentence = "This sentence contains %d s characters";
        var count = sentence.chars().filter(x -> x == 's').count();
        System.out.printf(sentence, count);
    }
}
