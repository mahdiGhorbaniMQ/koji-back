package com.example.kojiback.models.lists;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Place {
    @Id
    @Column @Getter @Setter
    private String place;

    public Place(){}
    public Place(String place){
        this.place = place;
    }
}
