# Test Cases – SauceDemo (https://www.saucedemo.com)

**Scope:** Login, product listing & sorting, cart, checkout.
**Test user:** `standard_user` / `secret_sauce` unless stated.
**Status legend:** Automated (TestNG / Cucumber) · Manual

| ID | Title | Preconditions | Steps | Test data | Expected result | Priority | Type | Status |
|---|---|---|---|---|---|---|---|---|
| TC_LOGIN_01 | Valid login | On login page | 1. Enter username 2. Enter password 3. Click Login | standard_user / secret_sauce | Products page shown | High | Functional, Smoke | Automated – `LoginTest`, `login.feature` |
| TC_LOGIN_02 | Wrong password | On login page | Same as TC_LOGIN_01 | standard_user / wrong_pass | Error "Username and password do not match…"; stays on login page | High | Negative | Automated |
| TC_LOGIN_03 | Locked-out user | On login page | Same as TC_LOGIN_01 | locked_out_user / secret_sauce | Error "Sorry, this user has been locked out." | High | Negative | Automated |
| TC_LOGIN_04 | Empty username | On login page | Leave username empty, enter password, Login | – / secret_sauce | Error "Username is required" | Medium | Negative | Automated |
| TC_LOGIN_05 | Empty password | On login page | Enter username, leave password empty, Login | standard_user / – | Error "Password is required" | Medium | Negative | Automated |
| TC_LOGIN_06 | Logout | Logged in | 1. Open menu 2. Click Logout | – | Login page shown | Medium | Functional | Automated |
| TC_CART_01 | Add one product | Logged in | Click "Add to cart" on Sauce Labs Backpack | – | Badge shows 1; button changes to "Remove" | High | Functional, Smoke | Automated – `CartTest` |
| TC_CART_02 | Cart lists added products | Logged in | Add Backpack + Bike Light, open cart | – | Exactly those 2 products listed | High | Functional | Automated |
| TC_CART_03 | Remove from inventory page | 2 products in cart | Click "Remove" on Backpack | – | Badge shows 1; button back to "Add to cart" | Medium | Functional | Automated |
| TC_CART_04 | Remove from cart page | 2 products in cart | Open cart, Remove Backpack | – | Only Bike Light remains | Medium | Functional | Automated |
| TC_SORT_01 | Sort name A→Z | Logged in | Select "Name (A to Z)" | – | Names in ascending order | Medium | Functional | Automated – `SortTest` (sorts Z→A first, compares to the known product list) |
| TC_SORT_02 | Sort name Z→A | Logged in | Select "Name (Z to A)" | – | Names in descending order | Medium | Functional | Automated |
| TC_SORT_03 | Sort price low→high | Logged in | Select "Price (low to high)" | – | Prices ascending | Medium | Functional, Smoke | Automated |
| TC_SORT_04 | Sort price high→low | Logged in | Select "Price (high to low)" | – | Prices descending | Medium | Functional | Automated |
| TC_CHK_01 | End-to-end order | 2 products in cart | Checkout → fill info → Continue → Finish | Kriti / Sharma / 700000 | "Thank you for your order!" shown | High | E2E, Smoke | Automated – `CheckoutTest`, `checkout.feature` |
| TC_CHK_02 | Overview lists cart items | 2 products in cart | Checkout → fill info → Continue | Valid info | Same 2 products listed | Medium | Functional | Automated |
| TC_CHK_03 | Totals are correct | 2 products in cart | Reach overview page | Valid info | Item total = sum of prices; Tax = 8% of item total; Total = item total + tax | High | Functional | Automated |
| TC_CHK_04 | First name required | Product in cart | Leave first name empty, Continue | – / Sharma / 700000 | Error "First Name is required" | Medium | Negative | Automated |
| TC_CHK_05 | Last name required | Product in cart | Leave last name empty, Continue | Kriti / – / 700000 | Error "Last Name is required" | Medium | Negative | Automated |
| TC_CHK_06 | Postal code required | Product in cart | Leave postal code empty, Continue | Kriti / Sharma / – | Error "Postal Code is required" | Medium | Negative | Automated |
| TC_UI_01 | Product images match products | Logged in | Compare each product image to its name | Run as standard_user and problem_user | Each product shows its own image | Medium | UI | Manual (problem_user part automated as TC_KI_01) |
| TC_UI_02 | Cart persists after logout/login | 1 product in cart | Log out, log back in | – | Cart badge still shows 1 | Low | Functional | Automated – `CartTest` |
| TC_SEC_01 | Products page needs login | Not logged in | Open `/inventory.html` directly | – | Login page shown with "only access … when you are logged in" | High | Security, Negative | Automated – `AccessControlTest` |
| TC_SEC_02 | Cart page needs login | Not logged in | Open `/cart.html` directly | – | Same as TC_SEC_01 | Medium | Security, Negative | Automated |
| TC_SEC_03 | Checkout needs login | Not logged in | Open `/checkout-step-one.html` directly | – | Same as TC_SEC_01 | Medium | Security, Negative | Automated |
| TC_KI_01 | Product images for problem_user | Logged in as problem_user | Read every product image | – | 6 different images (fails: BUG-001) | Medium | Known defect | Automated – `KnownIssuesTest` |
| TC_KI_02 | Price sort for problem_user | Logged in as problem_user | Select "Price (low to high)" | – | Prices ascending (fails: BUG-002) | Medium | Known defect | Automated – `KnownIssuesTest` |

Cucumber feature files also cover login, cart, sorting and checkout (`src/test/resources/features`).
