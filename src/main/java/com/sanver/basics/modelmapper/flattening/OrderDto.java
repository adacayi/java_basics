package com.sanver.basics.modelmapper.flattening;

public class OrderDto {
    private String customerName;
    private String addressStreet;
    private String addressCity;

    public String getCustomerName() {
        return customerName;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public OrderDto() {
    }

    public OrderDto(String customerName, String addressStreet, String addressCity) {
        this.customerName = customerName;
        this.addressStreet = addressStreet;
        this.addressCity = addressCity;
    }
}
