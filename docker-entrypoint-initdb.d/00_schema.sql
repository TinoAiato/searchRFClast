CREATE TABLE users
(
    login    TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    roles    TEXT[] NOT NULL DEFAULT '{}' -- '{ROLE1, ROLE2}' - массивы в PostgreSQL
);

CREATE TABLE tokens
(
    value       TEXT PRIMARY KEY,
    user_login TEXT NOT NULL REFERENCES users
);

CREATE TABLE requests
(
    id     BIGSERIAL PRIMARY KEY,
    user_login TEXT    NOT NULL REFERENCES users,
    q       TEXT    NOT NULL,
    status      BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE results
(
    result_id  BIGSERIAL PRIMARY KEY,
    task_id BIGINT  NOT NULL REFERENCES requests,
    file TEXT NOT NULL,
    line TEXT NOT NULL,
    lineNumber BIGINT NOT NULL
);