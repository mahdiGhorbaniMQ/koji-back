package com.example.kojiback.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class GroupRequest {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String bio;

    @Getter @Setter
    private Set<String> users;
}
