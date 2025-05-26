CREATE TABLE account_tb (
    id BIGSERIAL PRIMARY KEY,
    account_number BIGINT NOT NULL UNIQUE,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES user_tb(id),
    CONSTRAINT chk_balance_positive CHECK (balance >= 0),
    CONSTRAINT chk_account_number_positive CHECK (account_number > 0)
);

CREATE UNIQUE INDEX idx_account_number ON account_tb(account_number);
CREATE INDEX idx_account_user_id ON account_tb(user_id);
CREATE INDEX idx_account_balance ON account_tb(balance);