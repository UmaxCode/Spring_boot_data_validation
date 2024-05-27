package com.umaxcode.spring.boot.data.validation.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record UserAuthenticationDTO(
        @NotNull String email,
        @NotNull String password) {
}
