package com.example.kojiback.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "\"event\"")
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String title;

    @Column @Getter @Setter
    private String description;

    @Column @Getter @Setter
    private String owner;

    public Event(){}
    public Event(String title,String description,String owner){
        this.title = title;
        this.description = description;
        this.owner = owner;
    }
}
