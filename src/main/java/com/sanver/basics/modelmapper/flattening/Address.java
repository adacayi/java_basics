package com.sanver.basics.modelmapper.flattening;

public class Address {
    private String street;
    private String city;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public Address() {
    }

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }
}
