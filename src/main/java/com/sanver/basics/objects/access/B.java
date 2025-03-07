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

    AddressFields getAddressFields() {
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

    class AddressFields{
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
