# Test Summary Report – SauceDemo Regression

| Item | Detail |
|---|---|
| **Build / URL** | https://www.saucedemo.com |
| **Test cycle** | [date] |
| **Environment** | Chrome [version] & Firefox [version], headless, GitHub Actions (ubuntu-latest) |
| **Executed by** | Kriti Sharma |

## Results

| Suite | Total | Passed | Failed | Skipped |
|---|---|---|---|---|
| TestNG UI tests | [ ] | [ ] | [ ] | [ ] |
| Cucumber BDD scenarios | [ ] | [ ] | [ ] | [ ] |
| Manual test cases | [ ] | [ ] | [ ] | [ ] |

*(Copy numbers from `target/surefire-reports/index.html` and `target/cucumber-report.html` after a run.)*

## Defects

| ID | Summary | Severity | Status |
|---|---|---|---|
| BUG-001 | Same image for all products (`problem_user`) | Major | New |
| BUG-002 | Sorting has no effect (`problem_user`) | Minor | New |

## Notes & risks
- Tests run against a public demo site; outages or UI changes there can cause failures unrelated to the framework.
- Not covered: performance, security, mobile layouts.

## Conclusion
[One or two sentences: is the tested functionality ready, and what is blocking?]
