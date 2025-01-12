INSERT INTO articles (id, name, price, available_quantity)
VALUES (gen_random_uuid(), 'iPhone 14', 999.99, 100),
       (gen_random_uuid(), 'Samsung Galaxy S23', 849.99, 150),
       (gen_random_uuid(), 'Google Pixel 7', 599.99, 120),
       (gen_random_uuid(), 'OnePlus 11', 729.99, 80),
       (gen_random_uuid(), 'Sony Xperia 1 V', 1199.99, 50),
       (gen_random_uuid(), 'Xiaomi Mi 13', 699.99, 200),
       (gen_random_uuid(), 'Huawei P50 Pro', 799.99, 90),
       (gen_random_uuid(), 'Motorola Edge 40', 549.99, 70),
       (gen_random_uuid(), 'Nokia XR20', 499.99, 110),
       (gen_random_uuid(), 'Asus ROG Phone 6', 1099.99, 40);

INSERT INTO couriers (id, name) VALUES (gen_random_uuid(), 'DHL');

INSERT INTO articles (id, name, price, available_quantity)
VALUES ('25984125-ebb5-4d43-81a7-033bee9cf6df', 'iPhone 13', 599.99, 200);

INSERT INTO customers (id, address, city, country, email, first_name, last_name, zip_code)
VALUES ('b8b4a824-89d0-432a-ae60-718928d78e99', 'Teststraße 14', 'Wien', 'Österreich',
        'test@test.com', 'Max', 'Mustermann', '1010');
