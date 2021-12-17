package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class EventPlace {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long event_id;

    @Column @Getter @Setter
    private String place;

    public EventPlace(){}
    public EventPlace(Long event_id,String place){
        this.event_id = event_id;
        this.place = place;
    }
}
