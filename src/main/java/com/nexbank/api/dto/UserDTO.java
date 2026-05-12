package com.nexbank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexbank.api.enums.UserRole;
import com.nexbank.api.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private String role;
    private String status;

}
