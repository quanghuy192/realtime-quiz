package org.example.realtimequiz.controllers.request;

import java.util.UUID;

import lombok.Data;

@Data
public class UserRequest {
    private UUID id;
    private long score;
    private String name;
    private long createdAt;
    private long updatedAt;
    private long deletedAt;
}
