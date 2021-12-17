package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class EventAgree {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long event_id;

    @Column @Getter @Setter
    private String username;

    public EventAgree(){}
    public EventAgree(Long event_id,String username){
        this.event_id = event_id;
        this.username = username;
    }
}
