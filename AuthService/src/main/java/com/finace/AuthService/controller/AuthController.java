package com.finace.AuthService.controller;

import com.finace.AuthService.dto.AuthResponse;
import com.finace.AuthService.dto.UserDTO;
import com.finace.AuthService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO request) throws Exception {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

//    @GetMapping("/login")
//    public String login() {
//        return "Hi Shambu";
//    }
}
