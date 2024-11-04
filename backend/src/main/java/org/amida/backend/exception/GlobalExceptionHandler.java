package org.amida.backend.exception;

import org.amida.backend.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ApiResponse handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ApiResponse handleEmailAlreadyTaken(EmailAlreadyTakenException ex) {
        return ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ApiResponse handleInvalidCredentials(InvalidCredentialsException ex) {
        return ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .build();
    }
}
