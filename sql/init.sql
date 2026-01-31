CREATE TABLE encrypted_token (
    id BIGSERIAL PRIMARY KEY,
    encrypted_token TEXT NOT NULL,
    iv VARCHAR(255),
    crypto_version VARCHAR(50),
    key_id VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE,
    last_used_at TIMESTAMP WITH TIME ZONE
);


CREATE TABLE google_user (
    id TEXT PRIMARY KEY,
    name TEXT,
    access_token_id BIGINT NOT NULL,
    token_type TEXT NOT NULL,
    refresh_token_id BIGINT,
    scope TEXT,
    expires_in_date TIMESTAMP,

    CONSTRAINT fk_google_user_access_token
        FOREIGN KEY (access_token_id)
        REFERENCES encrypted_token (id),

    CONSTRAINT fk_google_user_refresh_token
        FOREIGN KEY (refresh_token_id)
        REFERENCES encrypted_token (id)
);