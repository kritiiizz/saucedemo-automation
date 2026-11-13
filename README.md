# SauceDemo Web Test Automation Framework

![UI Tests](https://github.com/YOUR_USERNAME/saucedemo-automation/actions/workflows/ui-tests.yml/badge.svg)

End-to-end UI test automation for the [SauceDemo](https://www.saucedemo.com) e-commerce site, built with
**Java, Selenium WebDriver, TestNG, Cucumber (BDD) and Maven**, running on every push via **GitHub Actions**.

## Tech stack

| Area | Tool |
|---|---|
| Language | Java 17 |
| Browser automation | Selenium WebDriver 4 (Selenium Manager handles drivers) |
| Test runner | TestNG (groups, data providers) |
| BDD | Cucumber + Gherkin |
| Build tool | Maven |
| CI | GitHub Actions (Chrome + Firefox matrix, headless) |
| Reporting | Allure (TestNG + Cucumber), Cucumber HTML report, screenshots attached on failure |
| Logging | SLF4J + Logback (console and `target/logs/test-run.log`) |
| Parallel runs | TestNG classes and Cucumber scenarios, 3 threads |

## Design

- **Page Object Model** – each page has its own class with locators and actions (`src/main/java/.../pages`).
- **BasePage** – all clicks, typing and reads go through explicit waits; no `Thread.sleep`, no implicit waits.
- **Locators** – SauceDemo's `data-test` attributes for cart buttons and errors, so tests don't depend on layout or product-name XPath.
- **Shared components** – the error banner used by the login and checkout forms lives in `pages/components/ErrorBanner`.
- **DriverFactory** – `ThreadLocal` WebDriver, browser chosen by config; the suite runs in parallel (3 threads).
- **ConfigReader** – settings in `config.properties`, overridable with `-D` flags (used by CI).
- **Data-driven tests** – TestNG `@DataProvider` and Cucumber `Scenario Outline`.
- **Independent tests** – fresh browser for every test; screenshot saved and attached to the report on failure.
- **Cucumber state sharing** – PicoContainer injects a per-scenario `ScenarioContext` into step classes.
- **Test data** – one `TestData` class (customer, products, tax rate).
- **Business-rule checks** – checkout verifies the 8% tax, not just that the numbers add up; soft assertions report every wrong total at once.
- **Known-defect tests** – `KnownIssuesTest` links each test to a bug ID in `docs/BUG_REPORTS.md` and runs in its own suite.

```
src
├── main/java/com/kriti/automation
│   ├── config/ConfigReader.java
│   ├── driver/DriverFactory.java
│   ├── pages/        BasePage, LoginPage, InventoryPage, CartPage, Checkout*Page
│   │   └── components/ErrorBanner.java
│   └── utils/ScreenshotUtil.java
└── test
    ├── java/com/kriti/automation
    │   ├── base/BaseTest.java
    │   ├── data/TestData.java
    │   ├── tests/    LoginTest, AccessControlTest, CartTest, SortTest, CheckoutTest, KnownIssuesTest
    │   ├── bdd/      Hooks, ScenarioContext, LoginSteps, CartSteps, SortSteps, CheckoutSteps
    │   └── runners/CucumberRunner.java
    └── resources
        ├── config.properties
        ├── logback-test.xml
        └── features/ login, cart, sorting, checkout
docs/  TEST_CASES.md · BUG_REPORTS.md · TEST_SUMMARY_REPORT.md
```

## Coverage

27 documented test cases (26 automated, 1 manual) across login, access control, sorting, cart, checkout and known defects –
see [docs/TEST_CASES.md](docs/TEST_CASES.md).

## Running the tests

Requirements: JDK 17+, Maven 3.9+, Chrome or Firefox.

```bash
mvn clean test                                   # full suite (TestNG + Cucumber), Chrome
mvn clean test -Dbrowser=firefox                 # another browser
mvn clean test -Dheadless=true                   # no browser window
mvn clean test -DsuiteXmlFile=smoke.xml          # smoke tests only
mvn clean test -Dcucumber.filter.tags="@smoke"   # filter Cucumber scenarios by tag
mvn clean test -DsuiteXmlFile=known-issues.xml   # tests for open defects (expected to fail)
mvn clean test -DLOG_LEVEL=DEBUG                 # log every click, wait and read
mvn allure:serve                                 # open the Allure report after a run
```

Reports after a run:
- `mvn allure:serve` or `target/site/allure-maven-plugin/` (after `mvn allure:report`) – Allure report with screenshots
- `target/surefire-reports/index.html` – TestNG results
- `target/cucumber-report.html` – Cucumber results
- `target/screenshots/` – screenshots of failed tests
- `target/logs/test-run.log` – full log of the run

In GitHub Actions, the same reports are uploaded as build artifacts.

## Test documentation

- [Test cases](docs/TEST_CASES.md)
- [Defect reports](docs/BUG_REPORTS.md)
- [Test summary report](docs/TEST_SUMMARY_REPORT.md)

## Execution Proof
* **Full Maven Execution Log:** [View `testng.xml` Run Log](docs/execution-logs/testng-regression-run.txt)
* **Defect Evidence Screenshots:** [`docs/images/`](docs/images/)
