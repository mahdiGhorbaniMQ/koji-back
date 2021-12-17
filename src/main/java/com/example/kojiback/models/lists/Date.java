package com.example.kojiback.models.lists;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Date {

    @Id
    @Column @Getter @Setter
    private String date;

    public Date(){}
    public Date(String date){
        this.date = date;
    }
}
