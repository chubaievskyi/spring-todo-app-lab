INSERT INTO users (email, password, first_name, last_name, role)
VALUES ('user1@gmail.com', '123', 'User1', 'User1','USER'),
       ('user2@gmail.com', '123', 'User2', 'User2','USER'),
       ('user3@gmail.com', '123', 'User3', 'User3','USER'),
       ('user4@gmail.com', '123', 'User4', 'User4','USER'),
       ('user5@gmail.com', '123', 'User5', 'User5','USER'),
       ('user6@gmail.com', '123', 'User6', 'User6','USER'),
       ('user7@gmail.com', '123', 'User7', 'User7','USER'),
       ('user8@gmail.com', '123', 'User8', 'User8','USER'),
       ('user9@gmail.com', '123', 'User9', 'User9','USER'),
       ('chubaievskyi@gmail.com', '123','Pavlo', 'Chubaievskyi', 'ADMIN');


INSERT INTO tasks (created_by, name, owner_id, description, deadline)
VALUES ('chubaievskyi@gmail.com', 'task1', 1, 'some description1', '2023-02-20'),
       ('chubaievskyi@gmail.com', 'task2', 2, 'some description2', '2023-02-20'),
       ('Rod@gmail.com', 'task3', 3, 'some description3', '2023-02-20');