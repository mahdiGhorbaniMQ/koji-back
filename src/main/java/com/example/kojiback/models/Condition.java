package com.example.kojiback.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "\"condition\"")
public class Condition {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String username;

    @Column @Getter @Setter
    private Long eventId;

    @Column @Getter @Setter
    private String state;

    public Condition(){}
    public Condition(String username){
        this.username = username;
    }

}
