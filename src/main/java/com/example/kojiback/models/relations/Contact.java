package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Contact {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Getter @Setter @Column
    private String username;

    @Getter @Setter @Column
    private String contactUsername;

    public Contact(){}
    public Contact(String username, String contactUsername){
        this.username = username;
        this.contactUsername = contactUsername;
    }
}
