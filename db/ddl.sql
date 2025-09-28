create database shop;
use shop;

create table if not exists user
(
    id       int auto_increment primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    constraint username unique (username)
);

create table if not exists gender(
    id int auto_increment primary key ,
    title varchar(100)
);

create table if not exists role (
    id int auto_increment primary key ,
    title varchar(100)
);

create table if not exists person (
    id int auto_increment primary key ,
    name varchar(100),
    lastname varchar(100),
    birthdate timestamp,
    national int,
    user_id int,
    gender_id int,
    role_id int ,
    constraint Fk_person_user foreign key (user_id) references user(id),
    constraint FK_person_gender foreign key (gender_id) references gender(id),
    constraint Fk_person_role foreign key (role_id) references role(id)
);