# QA E-commerce Portfolio

![CI](https://github.com/Eugen076/qa-ecommerce-portofolio/actions/workflows/ci.yml/badge.svg?branch=main)

Browser-based QA portfolio for the SauceDemo e-commerce application. The repository currently contains a Java/Selenium/TestNG UI regression suite and practical QA documentation.

## Tech stack

- Java 21
- Selenium 4 and Chrome with Selenium Manager
- TestNG and Maven
- GitHub Actions for CI

## Project structure

```text
.
├── .github/workflows/ci.yml
├── QAAutomationProject/
│   ├── pom.xml
│   ├── src/main/java/com/qa/portfolio/
│   │   ├── pages/
│   │   └── utils/
│   ├── src/test/java/com/qa/portfolio/
│   │   ├── base/BaseTest.java
│   │   └── tests/
│   │       ├── CheckoutTest.java
│   │       ├── InventoryTest.java
│   │       └── LoginTest.java
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

## Documentation

- [Test plan](QAAutomationProject/docs/test-plan/test-plan.md)
- [Test cases](QAAutomationProject/docs/test-cases/test-cases.md)
- [Test execution report](QAAutomationProject/docs/test-execution-report.md)
- [Bug report template](QAAutomationProject/docs/bug-reports/bug-report-template.md)
- [Verified bug reports](QAAutomationProject/docs/bug-reports/)

## Continuous Integration

The **Tests** GitHub Actions workflow runs on pushes to `main` and can also be started manually from the repository's **Actions** tab. It runs `mvn -B test -Dheadless=true` on Ubuntu with Java 21. Download the `test-reports-and-screenshots` artifact from a workflow run to view Surefire reports and any failure screenshots. Pull requests do not currently trigger the workflow.

## Roadmap

- API testing
- SQL testing
