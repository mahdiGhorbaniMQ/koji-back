package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class EventCondition {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long event_id;

    @Column @Getter @Setter
    private Long condition_id;

    public EventCondition(){}
    public EventCondition(Long event_id,Long condition_id){
        this.event_id = event_id;
        this.condition_id = condition_id;
    }
}
