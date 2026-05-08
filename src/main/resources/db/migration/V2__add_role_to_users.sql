-- V2__add_role_to_users.sql
-- Add role column to users table for Spring Security integration

ALTER TABLE users
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'ROLE_CLIENT';

ALTER TABLE users
    ADD CONSTRAINT chk_users_role CHECK (role IN ('ROLE_CLIENT', 'ROLE_ADMIN'));