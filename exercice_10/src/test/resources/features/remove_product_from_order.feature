Feature: Remove a product from an order
  As a user
  I want to remove products from my order
  So that I can adjust what I buy

  Scenario: Decreasing the quantity when several units are present
    Given an existing order "O1" already containing 2 units of product "P1"
    When the user removes the product "P1" from the order "O1"
    Then the order "O1" contains 1 unit of product "P1"

  Scenario: Removing the last unit takes the product out of the order
    Given an existing order "O1" already containing 1 unit of product "P1"
    When the user removes the product "P1" from the order "O1"
    Then the order "O1" no longer contains the product "P1"

  Scenario: Removing a product absent from the order is rejected
    Given an existing order "O1"
    When the user removes the product "P1" from the order "O1"
    Then the order operation is rejected because the product is not in the order

  Scenario: Removing from a non-existing order is rejected
    Given no order exists with id "O404"
    When the user removes the product "P1" from the order "O404"
    Then the order operation is rejected because the order does not exist
