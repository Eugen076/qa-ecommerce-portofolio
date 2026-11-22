# SQL Data Validation Practice

These scripts are for manual QA practice with SQLite; they are not automated application tests. The sample data intentionally includes data-quality defects for investigation.

## Schema

| Table | Purpose | Key fields and constraints |
|---|---|---|
| `users` | Customer profiles | `user_id` primary key; first name, last name, email, and created date are required. |
| `products` | Product catalog | `product_id` primary key; required name, non-negative price and stock. |
| `orders` | Customer orders and stored amounts | `order_id` primary key; required `user_id` foreign key; constrained status and non-negative subtotal, tax, and total. |
| `order_items` | Product lines belonging to orders | `order_item_id` primary key; foreign keys to orders and products; positive quantity and non-negative unit price. |

Email is indexed but not declared unique so the duplicate-email seed can be loaded and detected by the QA query. The zero-price product is allowed by the schema's non-negative constraint, while query Q17 flags it for business-rule review.

## Run the scripts

From the `QAAutomationProject` directory, use the SQLite command-line tool:

```shell
sqlite3 qa-practice.db ".read sql/schema.sql"
sqlite3 qa-practice.db ".read sql/seed-data.sql"
sqlite3 qa-practice.db ".read sql/queries.sql"
```

Alternatively, open `sql/schema.sql`, `sql/seed-data.sql`, and `sql/queries.sql` in DB Browser for SQLite and execute them in that order. The scripts were also executed against an in-memory SQLite database using Python's built-in `sqlite3` module; all 20 queries completed without errors.

## Validation scenarios and observed results

| Scenario | Query reference | Expected result | Actual result with seeded data | Defect found |
|---|---|---|---|---|
| Detect duplicate customer emails | Q11 | No duplicate rows if email uniqueness holds | `taylor.morgan@example.test` appears twice (user IDs 1 and 6) | Yes |
| Find an order without persisted lines | Q12 | Every placed order should have its expected order items | Order `1005` for user `4` has no items | Yes |
| Reconcile stored order total to items plus tax | Q15 | `total_amount` equals item sum plus tax | Order `1004`: stored `22.27`, calculated `17.27` (`15.99` items + `1.28` tax) | Yes |
| Find zero-priced or invalidly stocked catalog items | Q17 | Prices and stock comply with catalog business rules | Product `6`, Test Sample Sticker, has price `0.00` | Yes |
| Detect order lines pointing to missing products | Q13 | No orphan order-item rows | 0 rows | No |
| Identify customers without orders | Q14 | Results match expected account activity | User `6` has no orders | Yes |
| Find missing required user, product, or order text/date | Q16 | No required values are missing | 0 rows | No |
| Check for negative stored order amounts | Q18 | No negative subtotal, tax, or total | 0 rows | No |
| Compare each stored subtotal with its item lines | Q20 | No mismatched order subtotals | 0 rows; order `1004` subtotal matches its line, though Q15 finds its incorrect total | No |
| Confirm sample schema volumes | Q01, Q03, Q05 | Six users, eight products, and six user-linked orders | 6 users, 8 products, 6 joined orders | No |

After UI checkout tests, a tester can run Q12 and Q15 to confirm that an order and its line items were saved and that the stored financial total agrees with the item sum and tax.
