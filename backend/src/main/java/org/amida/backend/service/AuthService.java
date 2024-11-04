package org.amida.backend.service;

import lombok.RequiredArgsConstructor;
import org.amida.backend.model.User;
import org.amida.backend.request.SignInRequest;
import org.amida.backend.request.SignUpRequest;
import org.amida.backend.response.ApiResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public void signUp(SignUpRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userService.registerUser(user);
    }

    public ApiResponse singIn(SignInRequest request){
        return ApiResponse.builder()
                .token(userService.authUser(request.getUsername(), request.getPassword()))
                .success(true)
                .message(String.format("The user %s logged in successfully", request.getUsername()))
                .build();
    }

}
