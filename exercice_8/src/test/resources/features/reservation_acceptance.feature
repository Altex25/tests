Feature: Room reservation acceptance

  As the reservation module
  I want to accept valid reservation requests
  So that users can book available meeting rooms

  Scenario: Reservation accepted for an available room
    Given a room with code "R1" named "Mercury" with a maximum capacity of 10
    When "alice@example.com" books the room "R1" for 4 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is accepted
    And the confirmation shows the room "R1"
    And the confirmation shows 4 participants

  Scenario: Reservation accepted at maximum capacity
    Given a room with code "R2" named "Venus" with a maximum capacity of 4
    When "bob@example.com" books the room "R2" for 4 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is accepted
    And the confirmation shows 4 participants

  Scenario: Reservation accepted when the slot starts after an existing reservation
    Given a room with code "R1" named "Mercury" with a maximum capacity of 10
    And the room "R1" is already booked from "2026-06-10 08:00" to "2026-06-10 09:00"
    When "carol@example.com" books the room "R1" for 3 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is accepted
