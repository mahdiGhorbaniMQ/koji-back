package com.example.kojiback.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "\"message\"")
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String writer;

    @Column @Getter @Setter
    private String content;

    @Column @Getter @Setter
    private LocalDate localDate;

    @Column @Getter @Setter
    private Long eventId;

    public Message(){}
    public Message(String writer,String content,LocalDate localDate,Long eventId){
        this.writer = writer;
        this.content = content;
        this.localDate = localDate;
        this.eventId = eventId;
    }
}
