drop table if exists users_roles;
drop table if exists users;
drop table if exists roles;


create table users
(
    id        int auto_increment
        primary key,
    age       int          not null,
    email     varchar(255) not null unique,
    last_name varchar(255) null,
    password  varchar(255) null,
    username  varchar(255) null
);

create table roles
(
    id   int auto_increment
        primary key,
    name varchar(255) not null
);
create table users_roles
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id)
);
