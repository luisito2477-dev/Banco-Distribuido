package com.banco.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {

    @NotBlank(message = "Name can not be empty.")
    @Size(max = 50, message = "Name can not be longer than 100 characters.")
    private String name;

    @NotBlank(message = "Email can not be empty.")
    @Email(message = "Email format is not valid.")
    private String email;

    @NotBlank(message = "Password can not be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least a lowercase, an uppercase, a number and a special character."
    )
    private String password;

    @NotBlank(message = "CURP can not be empty.")
    @Pattern(
            regexp = "^[A-Z]{1}[AEIOUX]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[HM]{1}(AS|BC|BS|CC|CH|CH|CL|CM|CS|DF|DG|GR|GT|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9]{1}[0-9]{1}$",
            message = "CURP format is not valid."
    )
    private String curp;

    //Constructors
    public RegisterRequest(){}


    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
}
