-- Q01: List users in a stable order; testers use this to inspect seeded account data.
SELECT user_id, first_name, last_name, email
FROM users
ORDER BY user_id;

-- Q02: Find users created in February or later; verifies date filtering.
SELECT user_id, first_name, created_at
FROM users
WHERE created_at >= '2026-02-01'
ORDER BY created_at;

-- Q03: Sort products from most to least expensive; verifies price ordering.
SELECT product_id, product_name, price
FROM products
ORDER BY price DESC, product_name;

-- Q04: Inspect active orders, excluding cancelled orders; verifies status filtering.
SELECT order_id, user_id, status, total_amount
FROM orders
WHERE status <> 'cancelled'
ORDER BY order_date DESC;

-- Q05: Join orders to users; verifies that each order identifies its customer.
SELECT o.order_id, u.first_name, u.last_name, o.status
FROM orders AS o
JOIN users AS u ON u.user_id = o.user_id
ORDER BY o.order_id;

-- Q06: Join order lines to product details; verifies product names and line extensions.
SELECT oi.order_id, p.product_name, oi.quantity, oi.unit_price,
       ROUND(oi.quantity * oi.unit_price, 2) AS line_total
FROM order_items AS oi
JOIN products AS p ON p.product_id = oi.product_id
ORDER BY oi.order_id, oi.order_item_id;

-- Q07: Sum order values by customer; testers use this to validate customer purchase totals.
SELECT u.user_id, u.first_name, COUNT(o.order_id) AS order_count,
       ROUND(COALESCE(SUM(o.total_amount), 0), 2) AS lifetime_order_total
FROM users AS u
LEFT JOIN orders AS o ON o.user_id = u.user_id
GROUP BY u.user_id, u.first_name
ORDER BY lifetime_order_total DESC, u.user_id;

-- Q08: Find customers with more than one order; verifies grouped counts using HAVING.
SELECT user_id, COUNT(*) AS order_count
FROM orders
GROUP BY user_id
HAVING COUNT(*) > 1
ORDER BY user_id;

-- Q09: Compare order count and average value by status; verifies status-level aggregates.
SELECT status, COUNT(*) AS order_count,
       ROUND(AVG(total_amount), 2) AS average_total
FROM orders
GROUP BY status
HAVING COUNT(*) >= 1
ORDER BY status;

-- Q10: Check product price and stock ranges; aggregate values can reveal bad catalog data.
SELECT COUNT(*) AS product_count, ROUND(AVG(price), 2) AS average_price,
       MIN(price) AS minimum_price, MAX(price) AS maximum_price,
       SUM(stock_quantity) AS total_stock
FROM products;

-- Q11: Find duplicate email addresses; verifies the application's email uniqueness rule.
SELECT email, COUNT(*) AS user_count
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

-- Q12: Find orders with no detail rows; verifies that checkout persisted order items.
SELECT o.order_id, o.user_id, o.status
FROM orders AS o
LEFT JOIN order_items AS oi ON oi.order_id = o.order_id
WHERE oi.order_item_id IS NULL
ORDER BY o.order_id;

-- Q13: Find order lines without a matching product; checks referential integrity defensively.
SELECT oi.order_item_id, oi.order_id, oi.product_id
FROM order_items AS oi
LEFT JOIN products AS p ON p.product_id = oi.product_id
WHERE p.product_id IS NULL;

-- Q14: Find registered users with no orders; helps check expected customer activity.
SELECT u.user_id, u.email
FROM users AS u
LEFT JOIN orders AS o ON o.user_id = u.user_id
WHERE o.order_id IS NULL
ORDER BY u.user_id;

-- Q15: Reconcile stored order totals against item sum plus tax; finds total-calculation defects.
SELECT o.order_id, o.subtotal, ROUND(COALESCE(SUM(oi.quantity * oi.unit_price), 0), 2) AS items_subtotal,
       o.tax_amount, o.total_amount,
       ROUND(COALESCE(SUM(oi.quantity * oi.unit_price), 0) + o.tax_amount, 2) AS calculated_total
FROM orders AS o
LEFT JOIN order_items AS oi ON oi.order_id = o.order_id
GROUP BY o.order_id, o.subtotal, o.tax_amount, o.total_amount
HAVING ABS(o.subtotal - COALESCE(SUM(oi.quantity * oi.unit_price), 0)) > 0.01
    OR ABS(o.total_amount - (COALESCE(SUM(oi.quantity * oi.unit_price), 0) + o.tax_amount)) > 0.01
ORDER BY o.order_id;

-- Q16: Find missing required text or date values; verifies completeness of key records.
SELECT 'users' AS source, user_id AS record_id, 'first_name' AS field
FROM users WHERE first_name IS NULL OR TRIM(first_name) = ''
UNION ALL
SELECT 'users', user_id, 'email'
FROM users WHERE email IS NULL OR TRIM(email) = ''
UNION ALL
SELECT 'products', product_id, 'product_name'
FROM products WHERE product_name IS NULL OR TRIM(product_name) = ''
UNION ALL
SELECT 'orders', order_id, 'order_date'
FROM orders WHERE order_date IS NULL OR TRIM(order_date) = '';

-- Q17: Find zero-priced or invalidly stocked products; a tester checks catalog business rules.
SELECT product_id, product_name, price, stock_quantity
FROM products
WHERE price <= 0 OR stock_quantity < 0
ORDER BY product_id;

-- Q18: Find negative order amounts or tax values; verifies financial values stay non-negative.
SELECT order_id, subtotal, tax_amount, total_amount
FROM orders
WHERE subtotal < 0 OR tax_amount < 0 OR total_amount < 0;

-- Q19: Find products priced above the catalog average; demonstrates a scalar subquery.
SELECT product_id, product_name, price
FROM products
WHERE price > (SELECT AVG(price) FROM products)
ORDER BY price DESC;

-- Q20: Compare stored order subtotal with the extended value of its lines; finds subtotal defects.
SELECT o.order_id, o.subtotal,
       ROUND(COALESCE(SUM(oi.quantity * oi.unit_price), 0), 2) AS items_subtotal
FROM orders AS o
LEFT JOIN order_items AS oi ON oi.order_id = o.order_id
GROUP BY o.order_id, o.subtotal
HAVING ABS(o.subtotal - COALESCE(SUM(oi.quantity * oi.unit_price), 0)) > 0.01
ORDER BY o.order_id;
