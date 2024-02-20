DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    role  VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP(0) NOT NULL,
--     created_at DATE DEFAULT CURRENT_DATE,
    created_by VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(128) NOT NULL,
    owner_id INT REFERENCES users (id) ON DELETE CASCADE,
--     owner_id VARCHAR(255),
--     deadline DATE NOT NULL,
    deadline TIMESTAMP(0) NOT NULL,
    status  VARCHAR(128) DEFAULT 'NEW'
);