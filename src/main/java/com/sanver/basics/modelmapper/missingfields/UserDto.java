package com.sanver.basics.modelmapper.missingfields;

import java.util.UUID;

public class UserDto {
    private UUID userId;
    private UUID projectId;
    private String name;
    private String city;

    public UserDto() {

    }

    public UserDto(UUID userId, UUID projectId, String name, String city) {
        this.userId = userId;
        this.projectId = projectId;
        this.name = name;
        this.city = city;
    }
}
