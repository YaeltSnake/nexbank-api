package com.nexbank.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponseDTO {

    private Long userId;
    private String emial;
    private String token;
    private LocalDateTime issuedAt;

}
