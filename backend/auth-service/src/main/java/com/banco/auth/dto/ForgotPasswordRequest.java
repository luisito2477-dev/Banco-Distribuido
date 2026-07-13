package com.banco.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

    @NotBlank(message = "Email can not be empty.")
    @Email(message = "Email format is not valid.")
    private String email;

    //constructor
    public ForgotPasswordRequest(){}

    //getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
