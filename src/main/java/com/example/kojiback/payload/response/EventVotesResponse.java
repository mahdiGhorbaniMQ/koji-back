package com.example.kojiback.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class EventVotesResponse {

    @Getter @Setter private Set<VoteResponse> agrees;
    @Getter @Setter private Set<VoteResponse> disagrees;

    public EventVotesResponse(){}
    public EventVotesResponse(Set<VoteResponse> agrees, Set<VoteResponse> disagrees){
        this.agrees = agrees;
        this.disagrees = disagrees;
    }
}
