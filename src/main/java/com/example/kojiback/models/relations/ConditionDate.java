package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class ConditionDate {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long condition_id;

    @Column @Getter @Setter
    private String date;

    public ConditionDate(){}
    public ConditionDate(Long condition_id,String date){
        this.condition_id = condition_id;
        this.date = date;
    }
}
