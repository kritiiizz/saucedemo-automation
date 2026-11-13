@cart
Feature: Shopping cart
  As a logged-in customer
  I want to add and remove products
  So that my cart holds exactly what I want to buy

  Background:
    Given I am logged in as "standard_user"

  @smoke
  Scenario: Adding products updates the cart badge
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    Then the cart badge should show 2

  @regression
  Scenario: Removing a product on the products page updates the badge
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    And I remove "Sauce Labs Backpack" from the products page
    Then the cart badge should show 1

  @regression
  Scenario: Removing a product from the cart page
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    And I open the cart
    And I remove "Sauce Labs Backpack" from the cart
    Then the cart should contain only "Sauce Labs Bike Light"
