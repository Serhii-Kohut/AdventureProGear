
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

CREATE SEQUENCE product_storage_seq
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

insert into users (id, name, surname, email, phone_number)
values (nextval('user_seq'), 'Danylo', 'Berkovskyi', 'serhio3347@gmail.com', '+380980648928');

insert into users (id, name, surname, email, phone_number)
values (nextval('user_seq'), 'Andrii', 'Len', 'len_andrey@gmail.com', '+380972555455');

insert into user_roles (id, user_id, role)
values (nextval('user_role_seq'), 1, 'user');

insert into user_roles (id, user_id, role)
values (nextval('user_role_seq'), 2, 'user');

insert into user_roles (id, user_id, role)
values (nextval('user_role_seq'), 2, 'admin');
insert into products (id, product_name, description, base_price, gender, category)
values (nextval('product_seq'), 'T-Shirt', 'Common shirt', 200, 'FEMALE', 'T_SHIRTS');

insert into products (id, product_name, description, base_price, gender, category)
values (nextval('product_seq'), 'T-Shirt', 'Common shirt', 200, 'MALE', 'T_SHIRTS');

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

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'S', 0, 1);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'M', 0, 1);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'L', 0, 1);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'XL', 0, 1);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'S', 0, 2);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'M', 0, 2);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'L', 0, 2);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'XL', 0, 2);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'S', 0, 3);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'M', 0, 3);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'L', 0, 3);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'XL', 0, 3);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'S', 0, 4);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'M', 0, 4);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'L', 0, 4);

insert into product_attributes (id, size, price_deviation, product_id)
values (nextval('product_attr_seq'), 'XL', 0, 4);

insert into product_attributes (id, additional, price_deviation, product_id)
values (nextval('product_attr_seq'), 'mini', -10, 5);

insert into product_attributes (id, additional, price_deviation, product_id)
values (nextval('product_attr_seq'), 'extended', 20, 5);

insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 1, 1, 10);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 1, 2, 5);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 1, 3, 0);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 1, 4, 4);

insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 2, 5, 10);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 2, 6, 5);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 2, 7, 0);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 2, 8, 4);

insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 3, 9, 7);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 3, 10, 11);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 3, 11, 3);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 3, 12, 0);

insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 4, 13, 7);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 4, 14, 11);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 4, 15, 3);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 4, 16, 0);

insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 4, 17, 2);
insert into product_storage (id, product_id, product_attribute_id, quantity)
values (nextval('product_storage_seq'), 4, 18, 2);

insert into orders (id, user_id, city, post_address, comment, is_paid, is_delivered, is_canceled)
values (nextval('order_seq'), 1, 'Kyiv', '10285', 'Call me back pls.', false, false, false);
insert into orders (id, user_id, city, post_address, is_paid, is_delivered, is_canceled)
values (nextval('order_seq'), 2, 'Kharkiv', '22222', true, false, true);

insert into orders_list (id, order_id, product_id, product_attribute_id, quantity)
values (nextval('order_list_seq'), 1, 2, 5, 2);
insert into orders_list (id, order_id, product_id, product_attribute_id, quantity)
values (nextval('order_list_seq'), 1, 4, 14, 1);
insert into orders_list (id, order_id, product_id, product_attribute_id, quantity)
values (nextval('order_list_seq'), 2, 1, 1, 3);
insert into orders_list (id, order_id, product_id, quantity)
values (nextval('order_list_seq'), 1, 5, 1);
