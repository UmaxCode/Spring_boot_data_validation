package com.umaxcode.spring.boot.data.validation.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record UserCreationDTO(
        @NotNull String username,
        @NotNull String email,
        @NotNull String password,
        @NotNull String confirmPassword) {


}
