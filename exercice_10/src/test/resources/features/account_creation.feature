Feature: Account creation
  As a user
  I want to create an account
  So that I can place orders

  Scenario: A new user registers successfully
    Given no account exists with username "alice"
    When the user registers with email "alice@shop.com", username "alice" and password "secret"
    Then the registration is confirmed for "alice"

  Scenario: Registration is rejected for an already existing username
    Given an account already exists with username "bob"
    When the user registers with email "bob@shop.com", username "bob" and password "secret"
    Then the registration is rejected because the account already exists

  Scenario: Registration is rejected for an already existing email
    Given an account already exists with email "taken@shop.com"
    When the user registers with email "taken@shop.com", username "newbie" and password "secret"
    Then the registration is rejected because the account already exists
