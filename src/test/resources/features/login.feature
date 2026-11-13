@login
Feature: Login
  As a registered SauceDemo customer
  I want to log in with my credentials
  So that I can browse and buy products

  Background:
    Given I am on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When I log in with username "standard_user" and password "secret_sauce"
    Then I should see the products page

  @regression
  Scenario Outline: Login is rejected for invalid credentials
    When I log in with username "<username>" and password "<password>"
    Then I should see a login error containing "<error>"

    Examples:
      | username        | password     | error                              |
      | standard_user   | wrong_pass   | Username and password do not match |
      | locked_out_user | secret_sauce | this user has been locked out      |
      |                 | secret_sauce | Username is required               |
      | standard_user   |              | Password is required               |
