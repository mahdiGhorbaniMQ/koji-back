package com.example.kojiback.models.lists;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ConditionPlace {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private Long condition_id;

    @Column @Getter @Setter
    private String place;

    public ConditionPlace(){}
    public ConditionPlace(Long condition_id,String place){
        this.condition_id = condition_id;
        this.place = place;
    }
}
