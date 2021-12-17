package com.example.kojiback.payload.response;

import lombok.Getter;
import lombok.Setter;

public class VoteResponse {
    @Getter @Setter private String username;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    public VoteResponse(){}
    public VoteResponse(String username,String firstName,String lastName){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
