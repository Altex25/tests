Feature: Category navigation
  As a user
  I want to browse products by category
  So that I can discover what is available

  Scenario: Browse products by category
    Given the category "Electronics" contains the product "Headphones"
    When the user selects the category "Electronics"
    Then the results contain the product "Headphones"
    And the matching product "Headphones" belongs to the category "Electronics"
