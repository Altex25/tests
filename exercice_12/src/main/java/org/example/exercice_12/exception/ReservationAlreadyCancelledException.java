package org.example.exercice_12.exception;

public class ReservationAlreadyCancelledException extends RuntimeException {

    public ReservationAlreadyCancelledException(String message) {
        super(message);
    }
}
