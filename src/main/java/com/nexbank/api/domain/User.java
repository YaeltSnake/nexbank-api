package com.nexbank.api.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter  // agrega Getter
@Setter  // agrega Setter
@NoArgsConstructor   // agrega constructor vacio
@AllArgsConstructor  // agrega constructor con argumentos
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate birthDate;
    private LocalDateTime createdAt;

}
