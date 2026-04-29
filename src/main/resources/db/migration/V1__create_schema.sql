-- =====================
-- TABLE: users
-- =====================
CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    birth_date  DATE NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =====================
-- TABLE: bank_accounts
-- =====================
CREATE TABLE bank_accounts (
    id             BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(50)    NOT NULL UNIQUE,
    account_type   VARCHAR(20)    NOT NULL,
    status         VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE',
    balance        NUMERIC(19, 4) NOT NULL DEFAULT 0,
    opened_at      TIMESTAMP      NOT NULL DEFAULT NOW(),
    owner_id       BIGINT         NOT NULL,
    CONSTRAINT fk_account_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- =====================
-- TABLE: transactions
-- =====================
CREATE TABLE transactions (
    id                    BIGSERIAL PRIMARY KEY,
    type                  VARCHAR(20)    NOT NULL,
    amount                NUMERIC(19, 4) NOT NULL,
    description           VARCHAR(255),
    target_account_number VARCHAR(50),
    executed_at           TIMESTAMP      NOT NULL DEFAULT NOW(),
    account_id            BIGINT         NOT NULL,
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES bank_accounts(id)
);