package com.sanver.basics.lambda;

import org.springframework.expression.spel.ast.MethodReference;

import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceSample {
    private final String name;

    public MethodReferenceSample(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        new MethodReferenceSample("1").print();
    }

    public static String getStaticString() {
        return "Static String";
    }

    public void print() {
        var text = "Text";
        Supplier<String> s = text::toUpperCase; // this refers to text.toUpperCase()
        System.out.println(s.get());
        s = this::getString; // this refers to this.getString()
        System.out.println(s.get());
        s = MethodReferenceSample::getStaticString; // this refers to MethodReferenceSample.getStaticString()
        System.out.println(s.get());
//        s = this::getStaticString; // This will result in a compile error, because a static method referenced through non-static qualifier
        Function<MethodReferenceSample, String> getName = MethodReferenceSample::getName2; // this refers to a static method in MethodReferenceSample static String getName2(MethodReferenceSample)
        // or a non-static method in MethodReferenceSample String getName2().
        // If both are present in MethodReferenceSample, it will result in a compile error.
        // Comment out the static getName2 to see the compile error.
        System.out.println(getName.apply(this));
        getName = this::getName; // This refers to String getName(MethodReferenceSample)
        System.out.println(getName.apply(this));
    }

//    public static String getName2(MethodReferenceSample obj) {
//        return "static ";
//    }

    public String getName2() {
        return "name2: " + name;
    }

    public String getName(MethodReferenceSample obj) {
        return "name: " + obj.name;
    }

    public String getString() {
        return "non - static string";
    }
}
