package org.amida.backend.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @Size(min = 5, max = 50, message = "The username must contain from 5 to 50 characters")
    @NotBlank(message = "The username must be not null")
    private String username;

    @Size(min = 5, max = 255, message = "The email address must contain from 5 to 255 characters")
    @NotBlank(message = "")
    @Email(message = "")
    private String email;

    @Size(min = 10, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$", message = "")
    private String password;
}
