CREATE
SEQUENCE user_seq
    start
1
    increment 1;

CREATE
SEQUENCE user_role_seq
    start
1
    increment 1;

CREATE
SEQUENCE product_seq
    start
1
    increment 1;
CREATE
SEQUENCE product_review_seq
START
1
INCREMENT 1;


CREATE
SEQUENCE product_review_reaction_seq
START
1
INCREMENT 1;

CREATE
SEQUENCE product_attr_seq
    start
1
    increment 1;

CREATE
SEQUENCE product_content_seq
    start
1
    increment 1;

CREATE
SEQUENCE product_categories_seq
    start
1
    increment 1;

CREATE
SEQUENCE order_seq
    start
1
    increment 1;

CREATE
SEQUENCE order_list_seq
    start
1
    increment 1;

CREATE
SEQUENCE categories_seq
    start
1
    increment 1;

CREATE
SEQUENCE sections_seq
    start
1
    increment 1;

CREATE
SEQUENCE post_seq
    START
1
    INCREMENT 1;



insert into sections(id, sectioncaption_en, sectioncaption_ua, sectionicon)
values (nextval('sections_seq'), 'Footwear', 'Взуття', 'icon1');

insert into sections(id, sectioncaption_en, sectioncaption_ua, sectionicon)
values (nextval('sections_seq'), 'Clothing', 'Одяг', 'icon2');

insert into sections(id, sectioncaption_en, sectioncaption_ua, sectionicon)
values (nextval('sections_seq'), 'Headwears', 'Головні убори', 'icon3');

insert into sections(id, sectioncaption_en, sectioncaption_ua, sectionicon)
values (nextval('sections_seq'), 'Outdoor Equipment', 'Похідне обладнання', 'icon4');


insert into users (id, name, surname, email, phone_number, password, role)
values (nextval('user_seq'), 'Danylo', 'Berkovskyi', 'serhio3347@gmail.com', '+380980648928', 'Password1@', 'ADMIN');

insert into users (id, name, surname, email, phone_number, password, role)
values (nextval('user_seq'), 'Andrii', 'Len', 'len_andrey@gmail.com', '+380972555455', 'Password2#', 'USER');

insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Sneakers', 'Кросівки', 1);

insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Boots', 'Чоботи', 1);

insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Sandals', 'Сандалі', 1);


insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'T-shirts', 'Футболки', 2);

insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Jeans', 'Джинси', 2);

insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Shorts', 'Шорти', 2);


insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Hats', 'Кепки', 3);

insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Caps', 'Шапки', 3);


insert into categories(id, category_name_en, category_name_ua, section_id)
values (nextval('categories_seq'), 'Tents', 'Палатки', 4);




insert into products (id, product_name_en, product_name_ua, description_en, description_ua, base_price, gender,
                      category)
values (nextval('product_seq'), 'T-Shirt', 'Футболка', 'Common shirt', 'Звичайна футболка', 200, 'MALE', 4);

insert into products (id, product_name_en, product_name_ua, description_en, description_ua, base_price, gender,
                      category)
values (nextval('product_seq'), 'T-Shirt', 'Футболка', 'Common shirt', 'Звичайна футболка', 175, 'FEMALE', 4);

insert into products (id, product_name_en, product_name_ua, description_en, description_ua, base_price, gender,
                      category)
values (nextval('product_seq'), 'Sneakers Hike Model', 'Кросівки для походів', 'Best choice for your feet',
        'Найкращий вибір для вашої стопи', 350, 'FEMALE', 1);

insert into products (id, product_name_en, product_name_ua, description_en, description_ua, base_price, gender,
                      category)
values (nextval('product_seq'), 'Sneakers Hike Model', 'Кросівки для походів', 'Best choice for your feet',
        'Найкращий вибір для вашої стопи', 350, 'MALE', 1);

insert into products (id, product_name_en, product_name_ua, description_en, description_ua, base_price, category)
values (nextval('product_seq'), 'Butcher knife', 'Ніж', 'Common knife', 'Звичайний ніж', 120, 5);

insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 1, 'img1_1');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 1, 'img1_2');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 2, 'img2_1');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 2, 'img2_2');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 3, 'img3');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 4, 'img4');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 5, 'img5_1');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 5, 'img5_2');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 5, 'img5_3');
insert into product_content (id, product_id, source)
values (nextval('product_content_seq'), 5, 'img5_4');

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


INSERT INTO public.post (id, user_id, title, content, image)
VALUES (nextval('post_seq'), 1, 'First Post', 'This is the content of the first post.',
        'https://example.com/image1.jpg'),
       (nextval('post_seq'), 2, 'Second Post', 'This is the content of the second post.',
        'https://example.com/image2.jpg');

INSERT INTO public.reactions (id, type, post_id, user_id)
VALUES (nextval('reaction_seq'), 'LIKE', 1, 1),
       (nextval('reaction_seq'), 'DISLIKE', 1, 2),
       (nextval('reaction_seq'), 'LAUGH', 2, 1),
       (nextval('reaction_seq'), 'SAD', 2, 2);