# Defect Reports

SauceDemo includes test accounts with deliberate defects (e.g. `problem_user`).
Reports below follow the standard format: ID, summary, environment, steps, expected vs actual, severity, priority, evidence.

> **Before publishing:** reproduce every bug yourself, attach your own screenshot,
> and edit the wording to match exactly what you observed.

---

## Template

| Field | Value |
|---|---|
| **ID** | BUG-XXX |
| **Summary** | One line: what is wrong, where |
| **Environment** | Browser + version, OS, URL, test user |
| **Preconditions** | |
| **Steps to reproduce** | 1. … 2. … 3. … |
| **Expected result** | |
| **Actual result** | |
| **Severity** | Critical / Major / Minor / Trivial (impact on the user) |
| **Priority** | High / Medium / Low (how soon to fix) |
| **Evidence** | Screenshot / video / log |
| **Related test case** | TC_… |
| **Status** | New / Open / Fixed / Verified / Closed |

---

## BUG-001 – All products show the same image for `problem_user`

| Field | Value |
|---|---|
| **Environment** | Chrome [version], Windows/macOS, https://www.saucedemo.com, user `problem_user` |
| **Steps to reproduce** | 1. Log in as `problem_user` / `secret_sauce` 2. View the Products page |
| **Expected result** | Each product shows its own product image |
| **Actual result** | [Describe what you see, e.g. every product shows the same unrelated image] |
| **Severity** | Major – customers cannot see what they are buying |
| **Priority** | High |
| **Evidence** | `screenshots/bug-001.png` (add your own) |
| **Related test case** | TC_UI_01, automated as TC_KI_01 (`KnownIssuesTest.eachProductHasItsOwnImage`) |
| **Status** | New |

## BUG-002 – Product sorting has no effect for `problem_user`

| Field | Value |
|---|---|
| **Environment** | Chrome [version], https://www.saucedemo.com, user `problem_user` |
| **Steps to reproduce** | 1. Log in as `problem_user` 2. Select "Price (low to high)" from the sort dropdown |
| **Expected result** | Products re-ordered by ascending price |
| **Actual result** | [Describe what you see, e.g. order does not change] |
| **Severity** | Minor |
| **Priority** | Medium |
| **Related test case** | TC_SORT_03 (passes for standard_user), automated as TC_KI_02 (`KnownIssuesTest.sortByPriceWorksForProblemUser`) |
| **Status** | New |

## BUG-003 – [Your own finding]

Explore the site as `problem_user`, `error_user` and `visual_user` (especially checkout)
and log one more defect you find yourself. Then add a matching test to `KnownIssuesTest`
with `description = "BUG-003: ..."` in the `known-issues` group.
