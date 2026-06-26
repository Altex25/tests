Feature: Bank account management
  As a bank customer
  I want to manage my accounts
  So that I can create accounts and move money safely

  Scenario: Assert a new account is created
    When an account "ACC001" is created for "Alice"
    Then the account "ACC001" exists with balance 0
    And the response status code is 201

  Scenario: Assert money is deposited on an account
    Given an account "ACC001" exists for "Bob"
    When 100 is deposited on account "ACC001"
    Then the account "ACC001" has balance 100
    And the response status code is 200

  Scenario: Assert withdrawal succeeds with sufficient funds
    Given an account "ACC001" exists for "Carol"
    And 200 is deposited on account "ACC001"
    When 50 is withdrawn from account "ACC001"
    Then the account "ACC001" has balance 150
    And the response status code is 200

  Scenario: Assert withdrawal is refused with insufficient funds
    Given an account "ACC001" exists for "Dave"
    And 30 is deposited on account "ACC001"
    When 100 is withdrawn from account "ACC001"
    Then the operation is refused with status code 422

  Scenario: Assert money is transferred between two accounts
    Given an account "ACC001" exists for "Eve"
    And an account "ACC002" exists for "Frank"
    And 200 is deposited on account "ACC001"
    When 100 is transferred from account "ACC001" to account "ACC002"
    Then the account "ACC001" has balance 100
    And the account "ACC002" has balance 100
    And the response status code is 200

  Scenario: Assert transfer is refused for insufficient funds
    Given an account "ACC001" exists for "Grace"
    And an account "ACC002" exists for "Heidi"
    And 30 is deposited on account "ACC001"
    When 100 is transferred from account "ACC001" to account "ACC002"
    Then the operation is refused with status code 422
