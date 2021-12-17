package com.example.kojiback.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class EventDetailsResponse {
    @Getter @Setter private Long id;
    @Getter @Setter private String title;
    @Getter @Setter private String descriptions;
    @Getter @Setter private String owner;
    @Getter @Setter private String finalDate;
    @Getter @Setter private String finalPlace;
    @Getter @Setter private Set<String> dates = new HashSet<>();
    @Getter @Setter private Set<String> places = new HashSet<>();

    public EventDetailsResponse(){}
    public EventDetailsResponse(Long id, String title, String descriptions, String owner, String finalDate,String finalPlace, Set<String> dates, Set<String> places){
        this.id = id;
        this.title = title;
        this.descriptions = descriptions;
        this.owner = owner;
        this.dates = dates;
        this.places = places;
        this.finalDate = finalDate;
        this.finalPlace = finalPlace;
    }
}
