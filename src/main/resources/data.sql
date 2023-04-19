INSERT INTO users (age, first_name, last_name, password, username)
VALUES ('35', 'admin', 'admin', '$2a$12$UoXxDkr.6z/OMCAkkL/5geoPGV0m6Up98ORVXlbEtW2leILpfUlPq', 'admin@mail.ru'); /* password: root */

INSERT INTO users (age, first_name, last_name, password, username)
VALUES ('30', 'user', 'user', '$2a$12$UoXxDkr.6z/OMCAkkL/5geoPGV0m6Up98ORVXlbEtW2leILpfUlPq', 'user@mail.ru'); /* password: root */

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1,1);

INSERT INTO users_roles (user_id, role_id) VALUES (1,2);

INSERT INTO users_roles (user_id, role_id) VALUES (2,2);