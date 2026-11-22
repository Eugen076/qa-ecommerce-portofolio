# qa-ecommerce-portofolio

![CI](https://github.com/Eugen076/qa-ecommerce-portofolio/actions/workflows/ci.yml/badge.svg?branch=main)

QA Portofolio Project - Manual Testing, Selenium Automation, API Testing and SQL

## UI automation tests

The Maven Selenium/TestNG suite contains 11 browser-based tests:

- **Login (5):** valid login, invalid password, locked-out user, empty credentials, and logout.
- **Inventory (3):** inventory page title, add an item to the cart, and remove an item from the cart.
- **Checkout (3):** successful purchase and order totals, required postal-code validation, and canceling checkout back to the cart.

Run the suite from the project directory with `mvn -B test -Dheadless=true`.

## Continuous Integration

The **Tests** GitHub Actions workflow runs on pushes to `main` and can be started manually with `workflow_dispatch` from the repository's **Actions** tab. It runs the Maven tests on Ubuntu with Java 21 and headless Chrome. After each run, download the `test-reports-and-screenshots` artifact from the run page to view Surefire reports and any failure screenshots. Pull requests do not currently trigger this workflow.
