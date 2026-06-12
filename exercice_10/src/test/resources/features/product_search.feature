Feature: Product search
  As a user
  I want to search for products
  So that I can quickly find what I need

  Scenario: Search products by keyword
    Given the catalogue returns the product "Laptop" for the keyword "lap"
    When the user searches for the keyword "lap"
    Then the results contain the product "Laptop"

  Scenario: Search products by maximum price
    Given the catalogue returns the product "Mouse" for a maximum price of 25.0
    When the user searches with a maximum price of 25.0
    Then the results contain the product "Mouse"
    And the matching product "Mouse" costs at most 25.0
