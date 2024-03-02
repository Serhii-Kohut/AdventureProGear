INSERT INTO orders (id, user_id, city, post_address, comment, price, status)
VALUES (45, 1, 'city', 'TestAddress', 'TestComment', 100, 'DELIVERED');

INSERT INTO products (id, product_name, description, base_price, category)
VALUES (48, 'Test', 'Test', 1, 'HIKING_EQUIPMENT');

INSERT INTO product_attributes (id, size, color, price_deviation, product_id, quantity)
VALUES (42, 'M', 'green', 0, 4, 1);

INSERT INTO orders_list (id, order_id, product_id, product_attribute_id, quantity)
VALUES (88, 45, 48, 42, 1);