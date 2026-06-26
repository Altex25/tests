package org.example.exercice_14.repository;

import org.example.exercice_14.model.Reservation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public Reservation save(Reservation reservation) {
        reservations.removeIf(existing -> existing.getId().equals(reservation.getId()));
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findByBookId(String bookId) {
        return reservations.stream()
                .filter(reservation -> reservation.getBookId().equals(bookId))
                .sorted(Comparator.comparingInt(Reservation::getPosition))
                .toList();
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.removeIf(existing -> existing.getId().equals(reservation.getId()));
    }
}
