-- =====================
-- TABLE: refresh_tokens
-- =====================

CREATE TABLE banking.refresh_tokens(
    token_id            UUID PRIMARY KEY,
    token               VARCHAR(255) UNIQUE NOT NULL,
    email               VARCHAR(255) NOT NULL,
    expires_at          TIMESTAMPTZ NOT NULL,
    revoked_at          TIMESTAMPTZ,
    parent_token_id     UUID,
    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (email)
        REFERENCES banking.users(email)
        ON DELETE RESTRICT
);

CREATE INDEX idx_refresh_tokens_email ON banking.refresh_tokens(email);
CREATE INDEX idx_refresh_tokens_parent_token_id ON banking.refresh_tokens(parent_token_id);


