CREATE TABLE IF NOT EXISTS conferences(
    id BIGSERIAL PRIMARY KEY,
    conference_id BIGINT UNIQUE NOT NULL,
    name VARCHAR(255)
);