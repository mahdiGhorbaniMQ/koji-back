package com.example.kojiback.payload.request;

import lombok.Getter;
import lombok.Setter;

public class CreateGroupRequest {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String bio;
}
