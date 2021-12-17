package com.example.kojiback.payload.request;

import lombok.Getter;
import lombok.Setter;

public class SetFinalCondition {
    @Getter @Setter
    private String finalPlace;
    @Getter @Setter
    private String finalDate;
}
