package org.amida.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.backend.request.SignInRequest;
import org.amida.backend.request.SignUpRequest;
import org.amida.backend.response.ApiResponse;
import org.amida.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register") // fix http method
    public ResponseEntity<ApiResponse> register(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SignInRequest request){
        log.info("username: {}, password {}", request.getUsername(), request.getPassword());
        ApiResponse response = authService.signIn(request);
        log.info("Generated token {}", response.token());
        return ResponseEntity.ok(authService.signIn(request));
    }
}
