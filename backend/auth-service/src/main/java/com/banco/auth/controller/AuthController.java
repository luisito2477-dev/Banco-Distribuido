package com.banco.auth.controller;

import com.banco.auth.dto.AuthResponse;
import com.banco.auth.dto.ForgotPasswordRequest;
import com.banco.auth.dto.RegisterRequest;
import com.banco.auth.dto.LoginRequest;
import com.banco.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid     //Anotacion para jakarta validation
            @RequestBody //anotacion para deserializar de JSON al objeto RegisterRequest
            RegisterRequest request
    ){
        String response = userService.registerUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid
            @RequestBody
            LoginRequest request
    ){
        AuthResponse response = userService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @Valid
            @RequestBody
            ForgotPasswordRequest request
    ){


        return null;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(){

        return null;
    }

}
