
INSERT INTO users (age, email, last_name, password, username)
values (12, 'a','Adminov','a','Admin');

INSERT INTO users (age, email, last_name, password, username)
values (15, 'email2@email','Pupkin','password','Vasya');

INSERT INTO users (age, email, last_name, password, username)
values (12, 'email3@email','Petr','password2','Petrov');

INSERT INTO users (age, email, last_name, password, username)
values (17, 'email4@email','Ivanov','password3','Ivan');

INSERT INTO users (age, email, last_name, password, username)
values (20, 'b','Userov','b','User');

INSERT INTO roles (name) values('ADMIN');
INSERT INTO roles (name) values('USER');

INSERT INTO users_roles(user_id, role_id) values (1, 1);
INSERT INTO users_roles(user_id, role_id) values (2, 2);
INSERT INTO users_roles(user_id, role_id) values (3, 2);
INSERT INTO users_roles(user_id, role_id) values (4, 1);
INSERT INTO users_roles(user_id, role_id) values (5, 2);