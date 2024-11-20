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

    public AddressFields getAddressFields() {
        return new AddressFields("City", "Street", "Building");
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", A.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("address='" + address + "'")
                .add("age=" + age)
                .toString();
    }

    protected class AddressFields{
        private final String city;
        private final String street;
        private final String building;

        public AddressFields(String city, String street, String building) {
            this.city = city;
            this.street = street;
            this.building = building;
        }
    }
}
