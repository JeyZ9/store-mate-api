package com.sm.jeyz9.storemateapi.controllers;

import com.sm.jeyz9.storemateapi.dto.LoginDTO;
import com.sm.jeyz9.storemateapi.dto.PasswordResetDTO;
import com.sm.jeyz9.storemateapi.dto.RegisterDTO;
import com.sm.jeyz9.storemateapi.services.AuthService;
import com.sm.jeyz9.storemateapi.services.ThaibluksmsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ThaibluksmsService thaibluksmsService;

    @Autowired
    public AuthController(AuthService authService, ThaibluksmsService thaibluksmsService) {
        this.authService = authService;
        this.thaibluksmsService = thaibluksmsService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer"
        ));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<>(thaibluksmsService.sendEmailResetPassword(email), HttpStatus.CREATED);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetDTO request) {
        return new ResponseEntity<>(authService.resetPassword(request), HttpStatus.CREATED);
    }
}
