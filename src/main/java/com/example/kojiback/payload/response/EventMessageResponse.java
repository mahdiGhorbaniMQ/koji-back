package com.example.kojiback.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class EventMessageResponse {
    @Getter
    @Setter
    private String writer;

    @Getter @Setter private String content;

    @Getter @Setter private Date date;

    EventMessageResponse(){}
    public EventMessageResponse(String writer,String content,Date date) {
        this.writer = writer;
        this.content = content;
        this.date = date;
    }
}
