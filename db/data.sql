insert into users value (1,'parham86','p123456');
insert into gender value (1,'مرد '),(2,'زن');
insert into role value (1,'buyer'),(2,'the seller');
insert into person value (1,'parham','saghazadeh','2007-09-12','5100339160',1,1,2);
rename table shop.user to users;

alter table Home_Appliances modify price decimal(10,3);
DROP table cars;
DROP table stationery;
drop table digital_product;
drop table Home_Appliances;