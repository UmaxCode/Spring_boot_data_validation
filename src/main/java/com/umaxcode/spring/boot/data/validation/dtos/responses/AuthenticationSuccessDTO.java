package com.umaxcode.spring.boot.data.validation.dtos.responses;

import java.util.Map;

public record AuthenticationSuccessDTO(Map<String, String> user, String accessToken, String refreshToken) {
}
