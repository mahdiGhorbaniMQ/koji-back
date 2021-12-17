package com.example.kojiback.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class EventRequest {
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String descriptions;
    @Getter @Setter
    private Long groupId;
    @Getter @Setter
    private Set<String> places;
    @Getter @Setter
    private Set<String> dates;
}
