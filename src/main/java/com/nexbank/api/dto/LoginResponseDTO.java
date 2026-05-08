package com.nexbank.api.dto;

import com.nexbank.api.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponseDTO {

    private Long userId;
    private String email;
    private String token;
    private LocalDateTime issuedAt;
    private UserRole role;

}
