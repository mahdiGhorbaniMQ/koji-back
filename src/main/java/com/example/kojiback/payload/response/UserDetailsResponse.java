package com.example.kojiback.payload.response;

import com.example.kojiback.models.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsResponse {
    @Getter @Setter private Long id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String username;
    @Getter @Setter private String email;
    @Getter @Setter private Set<Long> groups = new HashSet<>();

    public UserDetailsResponse(){}
    public UserDetailsResponse(Long id, String firstName, String lastName, String email, String username, Set<Long> groups){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.groups = groups;
    }
}
