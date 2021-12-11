package com.example.kojiback.payload.response;

import com.example.kojiback.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class GroupProfileResponse {
    @Getter @Setter private String name;
    @Getter @Setter private String bio;
    @Getter @Setter private String owner;
    @Getter @Setter private Set<String> users = new HashSet<>();

    public GroupProfileResponse(){}

    public GroupProfileResponse(String name, String bio, String owner, Set<String> users){
        this.name = name;
        this.bio = bio;
        this.owner = owner;
        this.users = users;
    }
}
