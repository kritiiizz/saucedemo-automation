@checkout
Feature: Checkout
  As a logged-in customer
  I want to buy the products in my cart
  So that my order is placed with the correct total

  Background:
    Given I am logged in as "standard_user"

  @smoke
  Scenario: Customer completes a purchase
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    And I proceed to checkout with "Kriti", "Sharma", "700000"
    Then the order total should equal item total plus 8% tax
    When I finish the order
    Then I should see the confirmation "Thank you for your order!"

  @regression
  Scenario: Checkout requires a first name
    When I add "Sauce Labs Backpack" to the cart
    And I proceed to checkout with "", "Sharma", "700000"
    Then I should see a checkout error containing "First Name is required"
