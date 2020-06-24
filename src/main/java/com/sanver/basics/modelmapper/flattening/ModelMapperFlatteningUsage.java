package com.sanver.basics.modelmapper.flattening;

import org.modelmapper.ModelMapper;

public class ModelMapperFlatteningUsage {
    public static void main(String[] args) {
        Customer customer = new Customer("Abdullah");
        Address address1 = new Address("First street", "First city");
        Order order = new Order(customer, address1);
        ModelMapper modelMapper = new ModelMapperConfig().modelMapper();
        OrderDto dto = modelMapper.map(order, OrderDto.class);
    }
}
