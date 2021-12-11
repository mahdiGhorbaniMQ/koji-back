package com.example.kojiback.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "\"group\"")
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String name;

    @Column @Getter @Setter
    private String bio;

    @Column @Getter @Setter
    private String owner;

    public Group(){}
    public Group(String name,String bio,String owner){
        this.name = name;
        this.bio = bio;
        this.owner = owner;
    }
}
