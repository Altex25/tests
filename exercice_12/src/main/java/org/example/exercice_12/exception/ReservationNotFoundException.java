package org.example.exercice_12.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
