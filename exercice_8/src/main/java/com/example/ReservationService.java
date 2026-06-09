package com.example;

import java.util.List;

public class ReservationService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public ReservationService(RoomRepository roomRepository,
                              ReservationRepository reservationRepository,
                              NotificationService notificationService) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    /**
     * Books a meeting room.
     *
     * @param reservation the reservation request
     * @return the confirmation when the reservation is accepted
     * @throws RoomNotFoundException      if the room does not exist
     * @throws CapacityExceededException  if the number of participants exceeds the capacity
     * @throws InvalidPeriodException     if the end date is before or equal to the start date
     * @throws RoomAlreadyBookedException if the room is already booked on the requested slot
     */
    public ReservationConfirmation book(Reservation reservation) {
        Room room = roomRepository.findByCode(reservation.getRoomCode())
                .orElseThrow(() -> new RoomNotFoundException(
                        "Unknown room: " + reservation.getRoomCode()));

        if (reservation.getParticipantCount() > room.getMaxCapacity()) {
            throw new CapacityExceededException(
                    "Capacity exceeded for room " + room.getCode()
                            + " (requested: " + reservation.getParticipantCount()
                            + ", maximum: " + room.getMaxCapacity() + ")");
        }

        if (!reservation.getEndDate().isAfter(reservation.getStartDate())) {
            throw new InvalidPeriodException(
                    "Invalid period: the end date must be after the start date");
        }

        if (hasConflict(reservation)) {
            throw new RoomAlreadyBookedException(
                    "Room " + room.getCode() + " is already booked on the requested slot");
        }

        ReservationConfirmation confirmation = new ReservationConfirmation(
                room.getCode(),
                reservation.getParticipantCount(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                "Reservation confirmed for " + reservation.getUserEmail()
                        + " in room " + room.getCode());

        notificationService.sendConfirmation(reservation);

        return confirmation;
    }

    private boolean hasConflict(Reservation reservation) {
        List<Reservation> existing = reservationRepository.findByRoomCode(reservation.getRoomCode());
        return existing.stream().anyMatch(other -> overlaps(other, reservation));
    }

    private boolean overlaps(Reservation a, Reservation b) {
        // Two slots overlap when each one starts strictly before the other one ends.
        // Touching slots (a.end == b.start) are therefore not considered as a conflict.
        return a.getStartDate().isBefore(b.getEndDate())
                && b.getStartDate().isBefore(a.getEndDate());
    }
}
