-- V3__add_status_to_users.sql
-- Add status column to users table
ALTER TABLE banking.users
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE banking.users
    ADD CONSTRAINT chk_users_status CHECK (status IN ('ACTIVE', 'SUSPENDED', 'BLOCKED', 'CLOSED'));