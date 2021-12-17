package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class EventMessage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long event_id;

    @Column @Getter @Setter
    private Long message_id;

    public EventMessage(){}
    public EventMessage(Long event_id,Long message_id){
        this.event_id = event_id;
        this.message_id = message_id;
    }
}
