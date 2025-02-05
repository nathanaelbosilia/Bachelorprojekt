-- Insert couriers only if the table is empty
INSERT INTO couriers (id, name)
SELECT gen_random_uuid(), 'DHL'
    WHERE NOT EXISTS (SELECT 1 FROM couriers);