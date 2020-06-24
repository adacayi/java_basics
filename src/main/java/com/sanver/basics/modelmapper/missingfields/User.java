package com.sanver.basics.modelmapper.missingfields;

import java.util.UUID;

public class User {
    private String id;
    private UUID userId;
    private UUID projectId;
    private String name;
    private String address;

    public String getId() {
        return id;
    }
}
