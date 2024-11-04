package org.amida.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.backend.request.SignInRequest;
import org.amida.backend.request.SignUpRequest;
import org.amida.backend.response.ApiResponse;
import org.amida.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
        return ResponseEntity.ok(authService.singIn(request));
    }
}
