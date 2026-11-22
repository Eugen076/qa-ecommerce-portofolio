INSERT INTO users (user_id, first_name, last_name, email, created_at) VALUES
    (1, 'Taylor', 'Morgan', 'taylor.morgan@example.test', '2026-01-08'),
    (2, 'Jordan', 'Lee', 'jordan.lee@example.test', '2026-01-12'),
    (3, 'Casey', 'Patel', 'casey.patel@example.test', '2026-02-02'),
    (4, 'Riley', 'Chen', 'riley.chen@example.test', '2026-02-18'),
    (5, 'Avery', 'Brown', 'avery.brown@example.test', '2026-03-05'),
    (6, 'Sam', 'Morgan', 'taylor.morgan@example.test', '2026-03-11');

INSERT INTO products (product_id, product_name, description, price, stock_quantity) VALUES
    (1, 'Sauce Labs Backpack', 'Everyday carry backpack', 29.99, 18),
    (2, 'Sauce Labs Bike Light', 'Compact bicycle light', 9.99, 42),
    (3, 'Sauce Labs Bolt T-Shirt', 'Crew-neck cotton shirt', 15.99, 25),
    (4, 'Sauce Labs Fleece Jacket', 'Warm full-zip fleece', 49.99, 11),
    (5, 'Sauce Labs Onesie', 'Infant bodysuit', 7.99, 30),
    (6, 'Test Sample Sticker', 'Sample item with an invalid zero price', 0.00, 100),
    (7, 'Sauce Labs Red T-Shirt', 'Red crew-neck shirt', 15.99, 14),
    (8, 'Sauce Labs Backpack - Large', 'Large-capacity backpack', 39.99, 7);

INSERT INTO orders (order_id, user_id, order_date, status, subtotal, tax_amount, total_amount) VALUES
    (1001, 1, '2026-04-02', 'delivered', 29.99, 2.40, 32.39),
    (1002, 2, '2026-04-05', 'shipped', 19.98, 1.60, 21.58),
    (1003, 1, '2026-04-09', 'processing', 49.99, 4.00, 53.99),
    (1004, 3, '2026-04-12', 'pending', 15.99, 1.28, 22.27),
    (1005, 4, '2026-04-14', 'pending', 0.00, 0.00, 0.00),
    (1006, 5, '2026-04-17', 'cancelled', 0.00, 0.00, 0.00);

INSERT INTO order_items (order_item_id, order_id, product_id, quantity, unit_price) VALUES
    (1, 1001, 1, 1, 29.99),
    (2, 1002, 2, 2, 9.99),
    (3, 1003, 4, 1, 49.99),
    (4, 1004, 3, 1, 15.99),
    (5, 1006, 6, 1, 0.00);
