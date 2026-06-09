package com.example;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReservationSteps {

    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;
    private NotificationService notificationService;
    private ReservationService reservationService;

    private final List<Reservation> existingReservations = new ArrayList<>();
    private ReservationConfirmation confirmation;
    private RuntimeException error;

    @Before
    public void initialize() {
        // Fresh mocks and a fresh service before each scenario.
        roomRepository = mock(RoomRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        notificationService = mock(NotificationService.class);
        reservationService = new ReservationService(roomRepository, reservationRepository, notificationService);

        existingReservations.clear();
        confirmation = null;
        error = null;
    }

    @Given("a room with code {string} named {string} with a maximum capacity of {int}")
    public void assert_room_with_code_name_with_capacity(String code, String name, int maxCapacity) {
        Room room = new Room(code, name, maxCapacity);
        when(roomRepository.findByCode(code)).thenReturn(Optional.of(room));
    }

    @Given("no room exists with code {string}")
    public void assert_no_room_exists_with_code(String code) {
        when(roomRepository.findByCode(code)).thenReturn(Optional.empty());
    }

    @Given("the room {string} is already booked from {string} to {string}")
    public void assert_room_is_already_booked(String code, String start, String end) {
        existingReservations.add(new Reservation("occupant@example.com", code, 1, parse(start), parse(end)));
        when(reservationRepository.findByRoomCode(code)).thenReturn(existingReservations);
    }

    @When("{string} books the room {string} for {int} participants from {string} to {string}")
    public void assert_books_room_for_participants(String email, String code, int participants, String start, String end) {
        Reservation reservation = new Reservation(email, code, participants, parse(start), parse(end));
        try {
            confirmation = reservationService.book(reservation);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @Then("the reservation is accepted")
    public void assert_reservation_is_accepted() {
        assertNull(error, "The reservation should have been accepted");
        assertNotNull(confirmation, "A confirmation should have been generated");
    }

    @Then("the confirmation shows the room {string}")
    public void assert_confirmation_shows_room(String code) {
        assertEquals(code, confirmation.getRoomCode());
    }

    @Then("the confirmation shows {int} participants")
    public void assert_confirmation_shows_participants(int participants) {
        assertEquals(participants, confirmation.getParticipantCount());
    }

    @Then("the reservation is refused because the room is unknown")
    public void assert_reservation_is_refused_because_room_is_unknown() {
        assert_reservation_refused_with(RoomNotFoundException.class);
    }

    @Then("the reservation is refused because the capacity is insufficient")
    public void assert_reservation_is_refused_because_capacity_insufficient() {
        assert_reservation_refused_with(CapacityExceededException.class);
    }

    @Then("the reservation is refused because the period is invalid")
    public void assert_reservation_refused_because_period_invalid() {
        assert_reservation_refused_with(InvalidPeriodException.class);
    }

    @Then("the reservation is refused because the room is already booked")
    public void assert_reservation_refused_because_room_already_booked() {
        assert_reservation_refused_with(RoomAlreadyBookedException.class);
    }

    @Then("a confirmation notification is sent")
    public void assert_confirmation_notification_is_sent() {
        verify(notificationService, times(1)).sendConfirmation(any(Reservation.class));
    }

    @Then("no confirmation notification is sent")
    public void assert_no_confirmation_notification_is_sent() {
        verify(notificationService, never()).sendConfirmation(any(Reservation.class));
    }

    private void assert_reservation_refused_with(Class<? extends RuntimeException> expected) {
        assertNotNull(error, "The reservation should have been refused");
        assertInstanceOf(expected, error, "Unexpected error type");
        assertNull(confirmation, "No confirmation must be generated for a refused reservation");
    }

    private static LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, DATE_TIME);
    }
}
