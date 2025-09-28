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