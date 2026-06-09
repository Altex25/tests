Feature: Room reservation refusal

  As the reservation module
  I want to refuse invalid reservation requests
  So that rooms are never double-booked or over capacity

  Scenario: Reservation refused when the room is unknown
    Given no room exists with code "R404"
    When "dave@example.com" books the room "R404" for 2 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is refused because the room is unknown

  Scenario: Reservation refused when the capacity is insufficient
    Given a room with code "R1" named "Mercury" with a maximum capacity of 5
    When "erin@example.com" books the room "R1" for 6 participants from "2026-06-10 09:00" to "2026-06-10 10:00"
    Then the reservation is refused because the capacity is insufficient

  Scenario: Reservation refused when the period is invalid
    Given a room with code "R1" named "Mercury" with a maximum capacity of 10
    When "frank@example.com" books the room "R1" for 2 participants from "2026-06-10 10:00" to "2026-06-10 09:00"
    Then the reservation is refused because the period is invalid

  Scenario: Reservation refused when the room is already booked on the slot
    Given a room with code "R1" named "Mercury" with a maximum capacity of 10
    And the room "R1" is already booked from "2026-06-10 09:00" to "2026-06-10 11:00"
    When "grace@example.com" books the room "R1" for 2 participants from "2026-06-10 10:00" to "2026-06-10 12:00"
    Then the reservation is refused because the room is already booked
