package org.example.exercice_14.repository;

import org.example.exercice_14.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findByBookId(String bookId);

    void delete(Reservation reservation);
}
