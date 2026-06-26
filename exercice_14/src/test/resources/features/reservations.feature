Feature: Book reservations
  As a library member
  I want to reserve unavailable books
  So that I can borrow them once they are returned

  Scenario: Assert a member can reserve an unavailable book
    Given a book "B1" is currently borrowed
    And member "M1" is active
    When member "M1" reserves book "B1"
    Then the reservation is registered at position 1

  Scenario: Assert multiple reservations queue on the same book
    Given a book "B1" is currently borrowed
    And member "M1" is active
    And member "M2" is active
    When member "M1" reserves book "B1"
    And member "M2" reserves book "B1"
    Then book "B1" has 2 reservations
    And member "M1" holds position 1
    And member "M2" holds position 2

  Scenario: Assert returning a reserved book serves the next member
    Given a book "B1" is currently borrowed
    And member "M1" is active
    And member "M1" reserves book "B1"
    When book "B1" is returned
    Then member "M1" is the next to borrow book "B1"

  Scenario: Assert a suspended member cannot reserve
    Given a book "B1" is currently borrowed
    And member "M1" is suspended
    When member "M1" tries to reserve book "B1"
    Then the reservation is refused

  Scenario: Assert an available book cannot be reserved
    Given a book "B1" is available
    And member "M1" is active
    When member "M1" tries to reserve book "B1"
    Then the reservation is refused
