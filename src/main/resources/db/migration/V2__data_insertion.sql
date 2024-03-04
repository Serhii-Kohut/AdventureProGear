
CREATE SEQUENCE user_seq
    start 1
    increment 1;

CREATE SEQUENCE user_role_seq
    start 1
    increment 1;

CREATE SEQUENCE product_seq
    start 1
    increment 1;

CREATE SEQUENCE product_attr_seq
    start 1
    increment 1;

CREATE SEQUENCE product_content_seq
    start 1
    increment 1;

CREATE SEQUENCE product_categories_seq
    start 1
    increment 1;

CREATE SEQUENCE order_seq
    start 1
    increment 1;

CREATE SEQUENCE order_list_seq
    start 1
    increment 1;

insert into users (id, name, surname, email, phone_number, role)
values (nextval('user_seq'), 'Danylo', 'Berkovskyi', 'serhio3347@gmail.com', '+380980648928', 'ADMIN');

insert into users (id, name, surname, email, phone_number, role)
values (nextval('user_seq'), 'Andrii', 'Len', 'len_andrey@gmail.com', '+380972555455', 'USER');

insert into products (id, product_name, description, base_price, gender, category)
values (nextval('product_seq'), 'T-Shirt', 'Common shirt', 200, 'MALE', 'T_SHIRTS');

insert into products (id, product_name, description, base_price, gender, category)
values (nextval('product_seq'), 'T-Shirt', 'Common shirt', 175, 'FEMALE', 'T_SHIRTS');

insert into products (id, product_name, description, base_price, gender, category)
values (nextval('product_seq'), 'Sneakers Hike Model', 'Best choice for your feet', 350, 'FEMALE', 'SHOES');

insert into products (id, product_name, description, base_price, gender, category)
values (nextval('product_seq'), 'Sneakers Hike Model', 'Best choice for your feet', 350, 'MALE', 'SHOES');

insert into products (id, product_name, description, base_price, category)
values (nextval('product_seq'), 'Butcher knife', 'Common knife', 120, 'HIKING_EQUIPMENT');

insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 1, 'img1_1');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 1, 'img1_2');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 2, 'img2_1');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 2, 'img2_2');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 3, 'img3');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 4, 'img4');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 5, 'img5_1');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 5, 'img5_2');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 5, 'img5_3');
insert into product_content (id, product_id, source) values (nextval('product_content_seq'), 5, 'img5_4');

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'S', 0, 1, 10);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'M', 0, 1, 0);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'L', 0, 1, 5);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'XL', 0, 1, 1);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'S', 0, 2, 0);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'M', 0, 2, 0);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'L', 0, 2, 7);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'XL', 0, 2, 2);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'S', 0, 3, 10);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'M', 0, 3, 0);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'L', 0, 3, 8);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'XL', 0, 3, 0);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'S', 0, 4, 1);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'M', 0, 4, 2);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'L', 0, 4, 0);

insert into product_attributes (id, size, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'XL', 0, 4, 4);

insert into product_attributes (id, additional, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'mini', -10, 5, 5);

insert into product_attributes (id, additional, price_deviation, product_id, quantity)
values (nextval('product_attr_seq'), 'extended', 20, 5, 0);

insert into orders (id, user_id, city, post_address, comment, status)
values (nextval('order_seq'), 1, 'Kyiv', '10285', 'Call me back pls.', 'DELIVERED');
insert into orders (id, user_id, city, post_address, status)
values (nextval('order_seq'), 2, 'Kharkiv', '22222', 'PAID');

insert into orders_list (id, order_id, product_id, product_attribute_id, quantity)
values (nextval('order_list_seq'), 1, 2, 5, 2);
insert into orders_list (id, order_id, product_id, product_attribute_id, quantity)
values (nextval('order_list_seq'), 1, 4, 14, 1);
insert into orders_list (id, order_id, product_id, product_attribute_id, quantity)
values (nextval('order_list_seq'), 2, 1, 1, 3);
insert into orders_list (id, order_id, product_id, quantity)
values (nextval('order_list_seq'), 1, 5, 1);
