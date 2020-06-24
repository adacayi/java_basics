package com.sanver.basics.modelmapper.missingfields;

import java.util.UUID;

public class ModelMapperMissingFieldsUsage {
    public static void main(String[] args) {
        var userDto = new UserDto(UUID.randomUUID(), UUID.randomUUID(), "user1");
        var modelMapper = new ModelMapperConfig().modelMapper();
        var user = modelMapper.map(userDto, User.class);
    }
}
