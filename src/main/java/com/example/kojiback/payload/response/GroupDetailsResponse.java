package com.example.kojiback.payload.response;

import com.example.kojiback.models.Event;
import com.example.kojiback.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class GroupDetailsResponse {
    @Getter @Setter private  Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String bio;
    @Getter @Setter private String owner;
    @Getter @Setter private String link;
    @Getter @Setter private Set<String> users = new HashSet<>();
    @Getter @Setter private Set<Long> events = new HashSet<>();

    public GroupDetailsResponse(){}
    public GroupDetailsResponse(Long id, String name, String bio, String owner,String link, Set<String> users, Set<Long> events){
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.owner = owner;
        this.link = link;
        this.users = users;
        this.events = events;
    }
}
