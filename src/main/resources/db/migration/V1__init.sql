CREATE
TYPE gender AS ENUM ('MALE','FEMALE');

CREATE
TYPE status AS ENUM ('NEW', 'PAID','DELIVERED','CANCELED');

CREATE
TYPE user_role AS ENUM ('USER', 'ADMIN');
--
-- CREATE TYPE category AS ENUM ('T_SHIRTS','PANTS', 'LINEN', 'HEADWEARS', 'HIKING_EQUIPMENT', 'BAGS', 'SHOES' );
create table if not exists sections
(
    id                bigint  not null
        constraint sections_pkey
        primary key,
    sectioncaption_en varchar not null,
    sectioncaption_ua varchar not null,
    sectionicon       varchar
);

alter table sections
    owner to postgres;

CREATE TABLE IF NOT EXISTS public.categories
(
    id BIGSERIAL PRIMARY KEY,
    category_name_ua   VARCHAR NOT NULL UNIQUE,
    category_name_en   VARCHAR NOT NULL UNIQUE,
    parent_category_id BIGINT
        CONSTRAINT subcategory_fk
        REFERENCES public.categories(id) ON DELETE SET NULL,
    section_id         BIGINT
        CONSTRAINT section_fk
        REFERENCES public.sections(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.users
(
    id                bigint primary key,
    name              varchar not null,
    surname           varchar not null,
    email             varchar not null unique,
    phone_number      varchar unique,
    registration_date date,
    role              varchar
);

CREATE TABLE IF NOT EXISTS public.products
(
    id              bigint primary key,
    product_name_en varchar not null,
    product_name_ua varchar not null,
    description_en  text,
    description_ua  text,
    base_price      integer not null,
    average_rating  DOUBLE precision default 0.0,
    gender gender,
    category        integer
        constraint category_fk
        references public.categories
);
CREATE TABLE IF NOT EXISTS public.products_review
(
    id          BIGINT PRIMARY KEY,
    user_name   VARCHAR,
    rating      double precision,
    comment     TEXT,
    review_date DATE,
    product_id  BIGINT NOT NULL
        CONSTRAINT fk_product
        REFERENCES public.products(id),
    likes       BIGINT DEFAULT 0,
    dislikes    BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS public.review_comments
(
    id BIGSERIAL PRIMARY KEY,
    review_id    BIGINT NOT NULL,
    user_id      BIGINT NOT NULL,
    comment_text     TEXT   NOT NULL,
    comment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review FOREIGN KEY (review_id) REFERENCES public.products_review (id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS public.review_reactions
(
    id            BIGINT PRIMARY KEY,
    user_id       BIGINT  NOT NULL,
    review_id     BIGINT  NOT NULL,
    reaction_type VARCHAR NOT NULL CHECK (reaction_type IN ('LIKE', 'DISLIKE')),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (id),
    CONSTRAINT fk_review FOREIGN KEY (review_id) REFERENCES public.products_review (id),
    CONSTRAINT unique_user_review UNIQUE (user_id, review_id)
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
    quantity        integer not null
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
    status       varchar not null
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

ALTER TABLE users
    ADD COLUMN password VARCHAR(255);

ALTER TABLE users
    ADD COLUMN verification_token VARCHAR(255) UNIQUE;

ALTER TABLE users
    ADD COLUMN token_expiry_date TIMESTAMP;

ALTER TABLE users
    ADD COLUMN street_and_house_number VARCHAR(255);

ALTER TABLE users
    ADD COLUMN city VARCHAR(255);

ALTER TABLE users
    ADD COLUMN postal_code VARCHAR(10);

ALTER TABLE users
    ADD COLUMN password_reset_token VARCHAR(255);

CREATE TABLE IF NOT EXISTS public.post
(
    id      bigint primary key,
    user_id bigint not null,
    title   varchar,
    content text,
    image   varchar,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES public.users (id)
);

CREATE
TYPE reaction_type AS ENUM ('LIKE', 'DISLIKE', 'LAUGH', 'SAD', 'ANGRY');

CREATE
SEQUENCE reaction_seq
    START
1
    INCREMENT 1;

CREATE TABLE IF NOT EXISTS public.reactions
(
    id      bigint primary key DEFAULT nextval('reaction_seq'),
    type reaction_type not null,
    post_id bigint not null,
    user_id bigint not null,
    CONSTRAINT fk_post
        FOREIGN KEY (post_id) REFERENCES public.post (id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES public.users (id)
);

ALTER TABLE product_attributes
    ADD COLUMN label VARCHAR(255);

ALTER TABLE product_attributes
    ADD COLUMN picture_url VARCHAR(255);

