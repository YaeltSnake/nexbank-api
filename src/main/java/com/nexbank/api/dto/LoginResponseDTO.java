package com.nexbank.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponseDTO {

    private LocalDateTime issuedAt;
    private Long userId;
    private String email;
    private String role;

    private String tokenType;
    private String token;
    private String refreshToken;
    private LocalDateTime expiresAt;

}
