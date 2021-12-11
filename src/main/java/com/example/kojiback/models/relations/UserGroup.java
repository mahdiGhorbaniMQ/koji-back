package com.example.kojiback.models.relations;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class UserGroup {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String username;

    @Column @Getter @Setter
    private Long group_id;

    public UserGroup(){}
    public UserGroup(String username,Long group_id){
        this.username = username;
        this.group_id = group_id;
    }
}
