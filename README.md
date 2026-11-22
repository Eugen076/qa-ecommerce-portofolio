# QA E-commerce Portfolio

![CI](https://github.com/Eugen076/qa-ecommerce-portofolio/actions/workflows/ci.yml/badge.svg?branch=main)

QA portfolio for the SauceDemo e-commerce application, with Selenium/TestNG UI regression coverage, Rest Assured API tests, and practical QA documentation.

## Tech stack

- Java 21
- Selenium 4 and Chrome with Selenium Manager
- Rest Assured, TestNG, and Maven
- GitHub Actions for CI

## Project structure

```text
.
├── .github/workflows/ci.yml
├── QAAutomationProject/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/qa/portfolio/
│   │   │   ├── pages/
│   │   │   └── utils/
│   │   └── test/
│   │       ├── resources/config.properties
│   │       └── java/com/qa/portfolio/
│   │           ├── api/
│   │           │   ├── BaseApiTest.java
│   │           │   ├── CommentsApiTest.java
│   │           │   ├── PostsApiTest.java
│   │           │   └── UsersApiTest.java
│   │           ├── base/BaseTest.java
│   │           └── tests/
│   │               ├── CheckoutTest.java
│   │               ├── InventoryTest.java
│   │               └── LoginTest.java
│   └── docs/
│       ├── bug-reports/
│       ├── test-cases/
│       ├── test-plan/
│       └── test-execution-report.md
└── README.md
```

## How to run

Run commands from `QAAutomationProject`.

```shell
# Headed Chrome
mvn -B test

# Headless Chrome
mvn -B test -Dheadless=true
```

The suite contains 11 TestNG UI tests: five login tests, three inventory tests, and three checkout tests.

## API tests

The Rest Assured API suite checks JSONPlaceholder posts, users, and comments, including collection filtering, response fields and types, not-found behavior, response time, and simulated create/update/delete responses. JSONPlaceholder does not persist writes, so write tests assert only on each response.

The API group contains 10 tests. Run only the API tests from `QAAutomationProject`:

```shell
mvn -B test -Dgroups=api
```

Run only Selenium UI tests with `mvn -B test -Dgroups=ui`; plain `mvn test` runs both groups.

## Documentation

- [Test plan](QAAutomationProject/docs/test-plan/test-plan.md)
- [Test cases](QAAutomationProject/docs/test-cases/test-cases.md)
- [Test execution report](QAAutomationProject/docs/test-execution-report.md)
- [Bug report template](QAAutomationProject/docs/bug-reports/bug-report-template.md)
- [Verified bug reports](QAAutomationProject/docs/bug-reports/)
- [API test cases](QAAutomationProject/docs/test-cases/api-test-cases.md)

## Continuous Integration

The **Tests** GitHub Actions workflow runs on pushes to `main` and can also be started manually from the repository's **Actions** tab. It runs `mvn -B test -Dheadless=true` on Ubuntu with Java 21. Download the `test-reports-and-screenshots` artifact from a workflow run to view Surefire reports and any failure screenshots. Pull requests do not currently trigger the workflow.

## Roadmap

- SQL testing
