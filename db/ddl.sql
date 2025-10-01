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
    national_id varchar(30),
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
    year_of_manufacture date,
    brand varchar(100),
    made_in varchar(100),
    Product_design varchar(100),
    price decimal(10,3),
    product_id int,
    constraint FK_stationery_product foreign key (product_id) references product_categories(id)
);

create table if not exists digital_product(
    id int auto_increment primary key ,
    name varchar(100),
    year_of_manufacture timestamp,
    brand varchar(100),
    model varchar(100),
    made_in varchar(100),
    price decimal(10,3),
    product_id int,
    constraint FK_digital_product foreign key (product_id) references product_categories(id)
);

create table if not exists Home_Appliances (
    id int auto_increment primary key ,
    name varchar(100),
    year_of_manufacture timestamp,
    brand varchar(100),
    made_in varchar(100),
    price varchar(100),
    product_id int,
    constraint FK_home_product foreign key (product_id) references product_categories(id)
);

create table if not exists cars (
    id int auto_increment primary key ,
    name varchar(100),
    year_of_manufacture timestamp,
    brand varchar(100),
    model varchar(100),
    made_in varchar(100),
    price decimal(10,3),
    product_id int,
    constraint Fk_cars_product foreign key (product_id) references product_categories(id)
);

create table if not exists order_registration(
    id int auto_increment primary key,
    name varchar(100),
    person_id int ,
    product_id int ,
    brand varchar(100),
    price decimal(10,3),
    pay_with varchar(100),
    payment_date timestamp,
    constraint FK_order_person foreign key (person_id) references person(id),
    constraint FK_order_product foreign key (product_id) references product_categories(id)
);

create table if not exists product_registration(
    id int auto_increment primary key ,
    person_id int,
    product_id int,
    name_product varchar(100),
    registration_date timestamp,
    constraint FK_person_registration foreign key (person_id) references person(id),
    constraint FK_product_registration foreign key (product_id) references product_categories(id)
);

create table if not exists location(
    id int auto_increment primary key ,
    title varchar(100)
);

create table if not exists shop_location(
    id int auto_increment primary key ,
    registration_id int,
    location_id int,
    location varchar(100),
    open_time timestamp,
    constraint Fk_shop_registration foreign key (registration_id) references order_registration(id),
    constraint FK_shop_location foreign key (location_id) references location(id)
);

create table if not exists wareHouse_location(
    id int auto_increment primary key ,
    registration_id int,
    location_id int,
    location varchar(100),
    open_time timestamp,
    constraint Fk_wareHouse_registration foreign key (registration_id) references order_registration(id),
    constraint FK_wareHouse_location foreign key (location_id) references location(id)
);