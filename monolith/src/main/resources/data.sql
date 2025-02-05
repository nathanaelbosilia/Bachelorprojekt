-- Insert articles only if the table is empty
INSERT INTO articles (id, name, price, available_quantity)
SELECT * FROM (
                  SELECT gen_random_uuid(), 'iPhone 14', 999.99, 100 UNION ALL
                  SELECT gen_random_uuid(), 'Samsung Galaxy S23', 849.99, 150 UNION ALL
                  SELECT gen_random_uuid(), 'Google Pixel 7', 599.99, 120 UNION ALL
                  SELECT gen_random_uuid(), 'OnePlus 11', 729.99, 80 UNION ALL
                  SELECT gen_random_uuid(), 'Sony Xperia 1 V', 1199.99, 50 UNION ALL
                  SELECT gen_random_uuid(), 'Xiaomi Mi 13', 699.99, 200 UNION ALL
                  SELECT gen_random_uuid(), 'Huawei P50 Pro', 799.99, 90 UNION ALL
                  SELECT gen_random_uuid(), 'Motorola Edge 40', 549.99, 70 UNION ALL
                  SELECT gen_random_uuid(), 'Nokia XR20', 499.99, 110 UNION ALL
                  SELECT gen_random_uuid(), 'Asus ROG Phone 6', 1099.99, 40
              ) AS new_articles
WHERE NOT EXISTS (SELECT 1 FROM articles);

-- Insert specific article with fixed UUID only if it doesn't exist
INSERT INTO articles (id, name, price, available_quantity)
SELECT '25984125-ebb5-4d43-81a7-033bee9cf6df', 'iPhone 13', 599.99, 200
WHERE NOT EXISTS (SELECT 1 FROM articles WHERE id = '25984125-ebb5-4d43-81a7-033bee9cf6df');

-- Insert couriers only if the table is empty
INSERT INTO couriers (id, name)
SELECT gen_random_uuid(), 'DHL'
WHERE NOT EXISTS (SELECT 1 FROM couriers);

-- Insert customers only if they don't exist
INSERT INTO customers (id, address, city, country, email, first_name, last_name, zip_code)
SELECT 'b8b4a824-89d0-432a-ae60-718928d78e99', 'Teststraße 14', 'Wien', 'Österreich',
       'test@test.com', 'Max', 'Mustermann', '1010'
WHERE NOT EXISTS (SELECT 1 FROM customers WHERE id = 'b8b4a824-89d0-432a-ae60-718928d78e99');
