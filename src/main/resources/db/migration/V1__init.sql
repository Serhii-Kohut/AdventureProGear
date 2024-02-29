CREATE TYPE gender AS ENUM ('MALE','FEMALE');

CREATE TYPE status AS ENUM('PAID','DELIVERED','CANCELED');

CREATE TYPE user_role AS ENUM('USER', 'ADMIN');

CREATE TYPE category AS ENUM ('T_SHIRTS','PANTS', 'LINEN', 'HEADWEARS', 'HIKING_EQUIPMENT', 'BAGS', 'SHOES' );

CREATE TABLE IF NOT EXISTS public.users
(
    id                bigint primary key,
    name              varchar not null,
    surname           varchar not null,
    email             varchar not null unique,
    phone_number      varchar not null unique,
    registration_date date,
    role user_role
);

CREATE TABLE IF NOT EXISTS public.products
(
    id           bigint primary key,
    product_name varchar not null,
    description  text,
    base_price   integer not null,
    gender       gender,
    category category not null
);

CREATE TABLE IF NOT EXISTS public.product_attributes
(
    id              bigint primary key,
    size            varchar,
    color           varchar,
    additional      varchar,
    price_deviation integer not null,
    product_id      integer not null
        constraint prodatr_prod_fk
            references public.products,
    quantity             integer not null
);

CREATE TABLE IF NOT EXISTS public.product_content
(
    id         bigint primary key,
    product_id integer not null
        constraint prodcont_prod_fk
            references public.products,
    source     varchar not null
);

CREATE TABLE IF NOT EXISTS public.orders
(
    id           bigint primary key,
    user_id      integer not null
        constraint order_user_fk
            references public.users,
    order_date   date,
    city         varchar not null,
    post_address varchar not null,
    comment      text,
    price        int,
    status status not null
);

CREATE TABLE IF NOT EXISTS public.orders_list
(
    id                   bigint primary key,
    order_id             integer not null
        constraint orderls_order_fk
            references public.orders,
    product_id           integer not null
        constraint orderls_prod_fk
            references public.products,
    product_attribute_id integer
        constraint orderls_prodatr_fk
            references public.product_attributes,
    quantity             integer not null
);

ALTER TABLE users
    ADD COLUMN verified BOOLEAN;
