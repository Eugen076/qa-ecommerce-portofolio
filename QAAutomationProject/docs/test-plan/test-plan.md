# SauceDemo UI Test Plan

## Objective

Define a focused manual and automated regression plan for the core customer journey on SauceDemo, from authentication through order completion and logout. The plan records current coverage and provides a basis for extending the portfolio with repeatable QA artifacts.

## Application under test

| Item | Details |
|---|---|
| Application | [SauceDemo](https://www.saucedemo.com) |
| Application type | Public e-commerce demonstration site |
| Primary flows | Login, product inventory, cart, checkout, logout |
| Project under test | `QAAutomationProject` |

## Scope

- **Login:** valid and invalid credentials, locked-out account, and empty fields.
- **Inventory:** page content, product actions, cart badge, and sorting.
- **Cart:** item visibility and count, removal, navigation, and empty-cart boundary behavior.
- **Checkout:** required customer information, order summary, completion, and cancellation.
- **Logout:** end a session and check that browser back does not restore authenticated inventory.

## Out of scope

- Performance, load, and stress testing.
- Security testing, penetration testing, and vulnerability assessment.
- Mobile-device and responsive-layout certification. Headless Chrome is used for CI, but this is not a mobile test suite.
- Payment-provider integration and production transactions; SauceDemo is a demonstration application.

## Test approach

- **Manual exploratory testing:** use the listed scenarios to explore page state, validation messages, navigation, sorting, and account-specific behavior. Record reproducible issues with the bug-report template.
- **Automated regression:** run the existing Selenium/TestNG UI suite against Chrome with Maven. Tests use page objects and exercise the primary login, inventory, and checkout paths.
- Use the dedicated SauceDemo user accounts only; do not enter real personal or payment information.

## Test levels and types

| Classification | Coverage |
|---|---|
| Test level | End-to-end system testing through the browser UI |
| Functional types | Positive, negative, boundary, validation, navigation, and cart/order calculations |
| Regression types | Automated login, inventory, checkout, and logout checks |
| Exploratory types | Manual workflow, sorting, empty-cart, and test-account behavior checks |
| Not covered | Unit tests, API-level integration tests, performance tests, and security tests |

## Tools

| Tool | Use |
|---|---|
| Java 21 | Test runtime |
| Selenium 4 | Browser automation and ChromeDriver management via Selenium Manager |
| TestNG | Test definitions and assertions |
| Maven | Dependency management and test execution |
| GitHub Actions | CI execution on Ubuntu |
| Chrome | Local and CI browser |
| Markdown | Test plans, cases, reports, and defect records |

## Test environment

| Environment | Configuration |
|---|---|
| Local | Windows 11, Java 21, Maven, Chrome; headed by default, headless with `-Dheadless=true` |
| CI | GitHub Actions `ubuntu-latest`, Temurin Java 21, headless Chrome |
| Browser management | Chrome is available locally or on the CI runner; Selenium Manager resolves the driver |
| Target | Public site at `https://www.saucedemo.com` |

## Test data

| Username | Password | Purpose |
|---|---|---|
| `standard_user` | `secret_sauce` | Normal customer flows |
| `locked_out_user` | `secret_sauce` | Locked-account validation |
| `problem_user` | `secret_sauce` | Account-specific inventory behavior |
| `performance_glitch_user` | `secret_sauce` | Slower account behavior |
| `error_user` | `secret_sauce` | Account-specific functional behavior |
| `visual_user` | `secret_sauce` | Account-specific visual behavior |

Additional negative data: `wrong_password` for `standard_user`, blank username/password, and incomplete checkout information. The automated happy-path checkout uses Taylor Tester, postal code `12345`, and the Sauce Labs Backpack.

## Entry and exit criteria

| Entry criteria | Exit criteria |
|---|---|
| SauceDemo is reachable; Chrome, Java 21, and Maven are available. | All selected tests have a recorded result and failures include enough detail to investigate. |
| Test accounts and documented data are available. | Automated regression completes with no unexplained failures; reproducible defects are documented. |
| CI has access to the repository and can install Maven dependencies. | Reports and any failure screenshots are retained as CI artifacts. |

## Risks and mitigation

| Risk | Mitigation |
|---|---|
| Public demo content or behavior changes without notice. | Recheck selectors and expected behavior against the live site; date execution reports and keep defect evidence. |
| Network or account slowness causes timeouts. | Prefer explicit waits, use a reasonable wait limit, and rerun only after recording the original failure. |
| Timing-sensitive UI interactions create flaky results. | Wait for URLs and visible elements instead of fixed sleeps; keep each test's browser state isolated. |
| CI headless viewport differs from a local desktop. | Set an explicit 1920x1080 headless window and avoid maximizing the headless window. |

## Deliverables

- This test plan and the scenario catalogue in [test-cases.md](../test-cases/test-cases.md).
- Automated TestNG regression suite in `src/test/java/com/qa/portfolio/tests`.
- Reproducible defect reports in `docs/bug-reports`.
- A dated execution summary and coverage matrix in [test-execution-report.md](../test-execution-report.md).
- CI Surefire reports and failure screenshots as workflow artifacts.

## Lessons learned

CI-only checkout failures were traced to headless Chrome using a narrow viewport and to the checkout click not reliably taking effect. CI now supplies explicit headless window and Linux flags; the headed path still maximizes normally. The cart checkout action scrolls the button into view, tries a normal click, and retries with JavaScript only if navigation does not occur within five seconds. These changes improved repeatability without changing test assertions or workflow steps.
