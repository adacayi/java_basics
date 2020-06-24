package com.sanver.basics.modelmapper.missingfields;

import java.util.UUID;

public class UserDto {
    private UUID userId;
    private UUID projectId;
    private String name;

    public UserDto() {

    }

    public UserDto(UUID userId, UUID projectId, String name) {
        this.userId = userId;
        this.projectId = projectId;
        this.name = name;
    }
}
