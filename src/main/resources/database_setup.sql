DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    ipn        VARCHAR(128) NOT NULL
);

INSERT INTO users (first_name, last_name, ipn)
VALUES ('User1', 'User1', 1234567899),
       ('User2', 'User2', 4785145879),
       ('User3', 'User3', 4567891233),
       ('User4', 'User4', 1587458265),
       ('User5', 'User5', 7891234566),
       ('User6', 'User6', 3216549877),
       ('User7', 'User7', 1112223333),
       ('User8', 'User8', 6549873211),
       ('User9', 'User9', 3305310591),
       ('Pavlo', 'Chubaievskyi', 3305318591);

DROP INDEX IF EXISTS idx_first_name;
DROP INDEX IF EXISTS idx_last_name;

CREATE INDEX IF NOT EXISTS idx_first_name ON users (first_name);
CREATE INDEX IF NOT EXISTS idx_last_name ON users (last_name);

SELECT pg_size_pretty(pg_total_relation_size('users')) AS total_size;

SELECT id, first_name, last_name, ipn
FROM users
WHERE id < 10
LIMIT 10;