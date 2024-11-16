package com.authentication.auth_service.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull(message = "Email cant be null")
    private String email;

    @NotNull(message = "Password cant be null")
    private String password;
}
