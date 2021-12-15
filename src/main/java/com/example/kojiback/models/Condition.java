package com.example.kojiback.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity()
public class Conditionn {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String username;

    @Column @Getter @Setter
    private Long eventId;

    public Conditionn(){}
    public Conditionn(String username){
        this.username = username;
    }

}
