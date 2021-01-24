CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    nickname   VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) UNIQUE NOT NULL,
    last_name  VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    age        TINYINT
);
CREATE INDEX IX_users_nickname ON users (nickname);
CREATE INDEX IX_users_email ON users (email);

CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);
CREATE INDEX IX_roles_name ON roles (name);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    UNIQUE (user_id, role_id)
);

INSERT INTO users (id, nickname, first_name, last_name, password, email, age)
# 123
VALUES (1, 'ivan@gmail.com', 'Ivan', 'Ivanov', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'ivan@gmail.com', 30),
       (2, 'fedorka_1@gmail.com', 'Fedorka', 'Fedorkov', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'fedorka@gmail.com', 30),
       (3, 'pupka@gmail.com', 'Pupka', 'Pupkin', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'pupka@gmail.com', 37),
       (4, 'vaska@gmail.com', 'Vaska', 'Vasilev', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'vaska@gmail.com', 20),
       (5, 'vitka@gmail.com', 'Vitka', 'Vitiaev', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'vitka@gmail.com', 35),
       (6, 'andreika@gmail.com', 'Andreika', 'Andreev', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'andreika@gmail.com', 30),
       (7, 'petr@gmail.com', 'Petr', 'Petrov', '$2y$12$57MhjlAQd0AlfYLt1MqWYOAwq/z/vXMwN4TKPOWwXTVPw7z8eTcsG',
        'petr@gmail.com', 30),
# admin
       (8, 'admin@gmail.com', 'Admin', 'Admin', '$2y$12$kp49xTAg1BDn64.9Yr6v4..A74nhXP84m2GMLzTEVSvH/MHqFcrGK',
        'admin@gmail.com', 31);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),
       (8, 1);