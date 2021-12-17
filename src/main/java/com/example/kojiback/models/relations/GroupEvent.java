package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class GroupEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long group_id;

    @Column @Getter @Setter
    private Long event_id;

    public GroupEvent(){}
    public GroupEvent(Long group_id,Long event_id){
        this.group_id = group_id;
        this.event_id = event_id;
    }
}
