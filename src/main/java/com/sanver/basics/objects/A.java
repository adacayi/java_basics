package com.sanver.basics.objects;

import java.util.StringJoiner;

/**
 * This class is for the use of {@link com.sanver.basics.objects.access.AccessModifierSample} class
 */
public class A {
    private String name;
    String surname;
    protected String address;
    public int age;

    @Override
    public String toString() {
        return new StringJoiner(", ", A.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("address='" + address + "'")
                .add("age=" + age)
                .toString();
    }
}
