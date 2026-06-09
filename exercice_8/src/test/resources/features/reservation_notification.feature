Feature: Reservation notification

  As the reservation module
  I want to notify the user only when the reservation succeeds
  So that no confirmation is sent for a refused reservation

  Scenario: A confirmation notification is sent on success
    Given a room with code "R1" named "Mercury" with a maximum capacity of 10
    When "alice@example.com" books the room "R1" for 4 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is accepted
    And a confirmation notification is sent

  Scenario: No notification is sent on failure
    Given a room with code "R1" named "Mercury" with a maximum capacity of 5
    When "erin@example.com" books the room "R1" for 6 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is refused because the capacity is insufficient
    And no confirmation notification is sent
