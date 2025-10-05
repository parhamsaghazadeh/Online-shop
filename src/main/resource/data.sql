insert into users value (1,'parham86','p123456');
insert into gender value (1,'مرد '),(2,'زن');
insert into role value (1,'buyer'),(2,'the seller');
insert into person value (1,'parham','saghazadeh','2007-09-12','5100339160',1,1,2);
insert into categories value (1,'لوازم دیجیتالی'),(2,'لوازم خانگی');
insert into product value (1,'موبایل','سامسونگ','A56','chine',2025,'blue',1200.25,1);
insert into orders value (1,1,'cash','2025-01-10 12:25:23');
insert into orders_item value (1,1,1,1,1200.25);
insert into product_registration value (1,1,1,'2025-02-02 10:20:35');
insert into location value (1,'خیابان فردوسی','shop','10:00:00'),(2,'خیابان عطار ','warehouse','12:00:00');
insert into order_location value (1,1,2);
SELECT p.name              AS person_name,
    p.lastname          AS person_lastname,
    p.national_id       AS person_national_id,
    c.title             AS category_title,
    po.name             AS product_name,
    po.brand            AS product_brand,
    po.model            AS product_model,
    po.made_in          AS product_made_in,
    po.year_of_manufacture AS product_year,
    po.design           AS product_design,
    po.price            AS product_price,
    o.payment_method    AS order_payment_method,
    o.payment_date      AS order_payment_date,
    orr.quantity        AS order_quantity,
    orr.price           AS order_price,
    pr.registration_date AS product_registration_date,
    l.title             AS location_title,
    l.type              AS location_type,
    l.open_time         AS location_open_time
From product po join categories c on po.category_id = c.id
join orders_item orr on po.id = orr.product_id
join product_registration pr on po.id = pr.product_id
join orders o on orr.order_id = o.id
join person p on o.person_id = p.id
join order_location lo on o.id = lo.order_id
join location l on lo.location_id = l.id