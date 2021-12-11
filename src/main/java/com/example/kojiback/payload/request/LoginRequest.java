package com.example.kojiback.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @Getter @Setter
    @NotBlank
    private String username;

    @Getter @Setter
    @NotBlank
    private String password;

}