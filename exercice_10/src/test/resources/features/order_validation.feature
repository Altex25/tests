Feature: Order validation
  As a user
  I want to validate my order
  So that I receive an order confirmation

  Scenario: Validating an existing order returns a confirmation
    Given an existing order "O1"
    When the user validates the order "O1"
    Then the order is confirmed

  Scenario: Validating a non-existing order is rejected
    Given no order exists with id "O404"
    When the user validates the order "O404"
    Then the order operation is rejected because the order does not exist
