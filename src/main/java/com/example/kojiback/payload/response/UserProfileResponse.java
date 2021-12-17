package com.example.kojiback.payload.response;

import lombok.Getter;
import lombok.Setter;

public class UserProfileResponse {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String username;
    @Getter @Setter private String email;

    public UserProfileResponse(){}
    public UserProfileResponse(String firstName, String lastName, String email, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }
}
