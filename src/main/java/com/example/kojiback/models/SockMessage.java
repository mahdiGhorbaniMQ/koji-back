package com.example.kojiback.models;

import lombok.Getter;
import lombok.Setter;

public class SockMessage {
    @Getter
    @Setter
    private String greeting;

    public SockMessage() {
    }

    public SockMessage(String greeting) {
        this.greeting = greeting;
    }
}
