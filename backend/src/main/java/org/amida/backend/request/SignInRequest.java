package org.amida.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {

    @NotBlank(message = "")
    private String username;

    @NotBlank(message = "")
    private String password;
}
