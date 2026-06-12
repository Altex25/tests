Feature: Add a product to an order
  As a user
  I want to add products to my order
  So that I can buy them

  Scenario: Adding a new product confirms the addition
    Given an existing order "O1"
    When the user adds the product "P1" to the order "O1"
    Then the product addition is confirmed
    And the order "O1" contains 1 unit of product "P1"

  Scenario: Adding a product already in the order increases its quantity
    Given an existing order "O1" already containing 1 unit of product "P1"
    When the user adds the product "P1" to the order "O1"
    Then the order "O1" contains 2 units of product "P1"

  Scenario: Adding a product to a non-existing order is rejected
    Given no order exists with id "O404"
    When the user adds the product "P1" to the order "O404"
    Then the order operation is rejected because the order does not exist
