package com.example.kojiback.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ConditionResponse {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String username;
    @Getter @Setter private Set<String> dates;
    @Getter @Setter private Set<String> places;
    @Getter @Setter private String state;
    public ConditionResponse(){}
    public ConditionResponse(String firstName, String lastName, String username,Set<String> dates, Set<String> places,String state){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.dates = dates;
        this.places = places;
    	this.state = state;
    }
}
