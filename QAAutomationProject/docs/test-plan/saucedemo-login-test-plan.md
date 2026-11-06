# SauceDemo Login Test Plan

## Scope

Validate the login behavior on [SauceDemo](https://www.saucedemo.com) using Selenium 4, TestNG, and the Page Object Model.

## Test scenarios

| Test | Credentials | Expected result |
|---|---|---|
| `validLogin` | `standard_user` / `secret_sauce` | Browser opens the inventory page |
| `invalidLogin` | `standard_user` / `wrong_password` | Error message says the username and password do not match |
| `lockedOutUser` | `locked_out_user` / `secret_sauce` | Error message says the user is locked out |

## Run

Run the suite from the project root:

```shell
mvn test -Dheadless=true
```

ChromeDriver is managed by Selenium Manager. Set the `headless` system property to `true` to run Chrome without a visible window.
