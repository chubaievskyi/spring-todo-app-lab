DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    username VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    role  VARCHAR(128) NOT NULL
);

INSERT INTO users (username, password, role)
VALUES ('User1', '{noop}123', 'USER'),
       ('User2', '{noop}123', 'USER'),
       ('User3', '{noop}123', 'USER'),
       ('User4', '{noop}123', 'USER'),
       ('User5', '{noop}123', 'USER'),
       ('User6', '{noop}123', 'USER'),
       ('User7', '{noop}123', 'USER'),
       ('User8', '{noop}123', 'USER'),
       ('User9', '{noop}123', 'USER'),
       ('User10', '{noop}123', 'USER');

ALTER TABLE users
    ADD COLUMN password VARCHAR(128) DEFAULT '{noop}123';

ALTER TABLE users
    ADD COLUMN role VARCHAR(128) DEFAULT 'USER';

ALTER TABLE users
    DROP COLUMN password;

ALTER TABLE users
    DROP COLUMN role;

DROP INDEX IF EXISTS idx_first_name;
DROP INDEX IF EXISTS idx_last_name;

CREATE INDEX IF NOT EXISTS idx_first_name ON users (username);
CREATE INDEX IF NOT EXISTS idx_last_name ON users (password);

SELECT pg_size_pretty(pg_total_relation_size('users')) AS total_size;

SELECT id, username, password, role
FROM users
WHERE id < 10
LIMIT 10;

SELECT u.* FROM users u WHERE u.username = :username

SELECT id, username, password, role
FROM users
WHERE username = 'User1';

SELECT column_name, data_type
FROM information_schema.columns
WHERE table_name = 'users'
  AND column_name = 'password';

SELECT current_schema();