package com.nexbank.api.infrastructure.security;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "refresh_tokens", schema = "banking")
public class RefreshToken {

    @Id
    @Column(name = "token_id", unique = true, nullable = false)
    private UUID tokenId

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at", nullable = true)
    private Instant revokedAt;

    @Column(name = "parent_token_id", nullable = true)
    private UUID parentTokenId;

    @PrePersist
    void gerateUuid(){
        if (tokenId == null){
            tokenId = UUID.randomUUID();
        }
    }

    public void revoke() {
        this.revokedAt = Instant.now();
    }

}
