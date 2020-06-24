package com.sanver.basics.modelmapper.flattening;

public class Order {
    private Customer customer;
    private Address address;

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    public Order() {
    }

    public Order(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }
}
