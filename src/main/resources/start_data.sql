
INSERT INTO users (age, email, last_name, password, username)
values (12, 'a','Adminov','a','Admin');
INSERT INTO roles (name) values('ADMIN');
INSERT INTO roles (name) values('USER');
INSERT INTO users_roles(user_id, role_id) values (1, 1);