package com.sanver.basics.objects.access;

import com.sanver.basics.objects.A;

import java.util.StringJoiner;

/**
 * This class is for the use of {@link AccessModifierSample} class
 */
public class B {
    public int age;
    protected String address;
    String surname;
    private String name;

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
