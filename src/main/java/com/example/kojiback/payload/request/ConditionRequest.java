package com.example.kojiback.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ConditionRequest {
    @Getter @Setter public String username;
    @Getter @Setter public Set<String> dates;
    @Getter @Setter public Set<String> places;
    @Getter @Setter public String state;
}
