package com.example.kojiback.models.lists;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Agree {
    @Id @Column @Getter @Setter
    private String username;

    public Agree(){}
    public Agree(String username){
        this.username = username;
    }
}
