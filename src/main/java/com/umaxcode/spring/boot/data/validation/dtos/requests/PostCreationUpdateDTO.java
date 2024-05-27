package com.umaxcode.spring.boot.data.validation.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PostCreationUpdateDTO(
        @NotNull(message = "This file can't be null") @NotEmpty(message = "This field is required") String title,
        @NotNull(message = "This field can't be null") @NotEmpty(message = "This field is required") String description) {
}
