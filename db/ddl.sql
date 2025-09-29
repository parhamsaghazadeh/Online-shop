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

create table if not exists product_categories(
    id int auto_increment not null primary key ,
    title varchar(100)
);

create table if not exists stationery(
    id int auto_increment primary key ,
    name varchar(100),
    year_of_manufacture timestamp,
    brand varchar(100),
    made_in varchar(100),
    Product_design varchar(100),
    product_id int,
    constraint FK_stationery_product foreign key (product_id) references product_categories(id)
);