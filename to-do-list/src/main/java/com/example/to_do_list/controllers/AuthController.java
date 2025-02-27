package com.example.to_do_list.controllers;


import com.example.to_do_list.dto.LoginDTO;
import com.example.to_do_list.dto.RegisterDTO;
import com.example.to_do_list.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDto) {
        return authService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseEntity<?>> login(@RequestBody LoginDTO loginDto) {
        ResponseEntity<?> response = authService.loginUser(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
