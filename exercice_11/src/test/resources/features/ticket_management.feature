Feature: Support ticket management
  As a support agent
  I want to manage support tickets
  So that customer issues are tracked and resolved

  Scenario: Create a valid ticket
    Given a valid ticket with title "Printer not working" and priority "HIGH"
    When the ticket is created
    Then the ticket is saved with status "OPEN"
    And the response status code is 201

  Scenario: Resolve a ticket
    Given an existing ticket with title "Login issue" and priority "MEDIUM"
    When its status is changed to "RESOLVED"
    Then the ticket status becomes "RESOLVED"
    And the response status code is 200

  Scenario: Refuse to change the status of an already resolved ticket
    Given a resolved ticket with title "Old defect" and priority "LOW"
    When its status is changed to "IN_PROGRESS"
    Then the change is refused with status code 409

  Scenario: Consult a ticket that does not exist
    When ticket number 999 is requested
    Then the response status code is 404
