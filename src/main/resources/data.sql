INSERT INTO users (email, password, first_name, last_name, role)
VALUES ('user1@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User1', 'User1','USER'),
       ('user2@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User2', 'User2','USER'),
       ('user3@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User3', 'User3','USER'),
       ('user4@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User4', 'User4','USER'),
       ('user5@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User5', 'User5','USER'),
       ('user6@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User6', 'User6','USER'),
       ('user7@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User7', 'User7','USER'),
       ('user8@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User8', 'User8','USER'),
       ('user9@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy', 'User9', 'User9','USER'),
       ('chubaievskyi@gmail.com', '$2a$12$vU7CZFhEkeUPOAbYcFhatOYr0lzYRfYMR7AEfP9A7VgFK09KvbkRy','Pavlo', 'Chubaievskyi', 'ADMIN');


INSERT INTO tasks (created_at, created_by, name, owner, description, deadline)
VALUES ('2024-02-20 21:57', 'chubaievskyi@gmail.com', 'task1', 'user1@gmail.com', 'some description1', '2023-02-20'),
       ('2024-02-20 21:57', 'chubaievskyi@gmail.com', 'task2', 'user2@gmail.com', 'some description2', '2023-02-20'),
       ('2024-02-20 21:57', 'user1@gmail.com', 'task3', 'user3@gmail.com', 'some description3', '2023-02-20');