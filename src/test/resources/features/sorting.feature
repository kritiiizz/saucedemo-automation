@sorting
Feature: Product sorting
  As a customer browsing products
  I want to sort the product list
  So that I can find products by name or price

  Background:
    Given I am logged in as "standard_user"

  # Name (A to Z) is the default order, so it is not listed here: it would pass even if sorting were broken.
  # SortTest.sortByNameAscending covers it by sorting Z to A first.
  @regression
  Scenario Outline: Products are sorted by the chosen option
    When I sort the products by "<option>"
    Then the products should be sorted by <field> in <direction> order

    Examples:
      | option              | field | direction  |
      | Name (Z to A)       | name  | descending |
      | Price (low to high) | price | ascending  |
      | Price (high to low) | price | descending |
