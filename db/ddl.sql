create database shop;
use shop;

create table if not exists user
(
    id       int auto_increment primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    constraint username unique (username)
);

create table if not exists gender
(
    id    int auto_increment primary key,
    title varchar(100)
);

create table if not exists role
(
    id    int auto_increment primary key,
    title varchar(100)
);

create table if not exists person
(
    id          int auto_increment primary key,
    name        varchar(100),
    lastname    varchar(100),
    birthdate   timestamp,
    national_id varchar(30),
    user_id     int,
    gender_id   int,
    role_id     int,
    constraint Fk_person_user foreign key (user_id) references user (id),
    constraint FK_person_gender foreign key (gender_id) references gender (id),
    constraint Fk_person_role foreign key (role_id) references role (id)
);

create table if not exists categories
(
    id    int auto_increment not null primary key,
    title varchar(100)
);


create table if not exists product
(
    id                  int auto_increment not null primary key,
    name                varchar(100),
    brand               varchar(100),
    model               varchar(100),
    made_in             varchar(100),
    year_of_manufacture year,
    design              varchar(100),
    price               decimal(10, 2),
    category_id         int,
    constraint FK_product_category foreign key (category_id) references categories (id)
);

create table if not exists orders
(
    id             int auto_increment not null primary key,
    person_id      int,
    payment_method varchar(100),
    payment_date   timestamp,
    constraint Fk_orders_person foreign key (person_id) references person (id)
);

create table if not exists orders_item
(
    id         int auto_increment not null primary key,
    order_id   int,
    product_id int,
    quantity   int default 1,
    price      decimal(100, 2),
    constraint FK_item_order foreign key (order_id) references orders (id),
    constraint FK_item_product foreign key (product_id) references product (id)
);

create table if not exists product_registration(
    id int auto_increment not null primary key,
    person_id int,
    product_id int,
    registration_date timestamp,
    constraint FK_person_registration foreign key (person_id) references person(id),
    constraint Fk_product_registration foreign key (product_id) references product(id)
);

create table if not exists location
(
    id    int auto_increment primary key,
    title varchar(100)
);

create table if not exists shop_location
(
    id              int auto_increment primary key,
    registration_id int,
    location_id     int,
    location        varchar(100),
    open_time       date,
    constraint FK_shop_location foreign key (location_id) references location (id)
);

create table if not exists wareHouse_location
(
    id              int auto_increment primary key,
    registration_id int,
    location_id     int,
    location        varchar(100),
    open_time       date,
    constraint FK_wareHouse_location foreign key (location_id) references location (id)
);