package com.example;

public interface NotificationService {

    /**
     * Sends a confirmation for an accepted reservation.
     *
     * @param reservation the accepted reservation
     */
    void sendConfirmation(Reservation reservation);
}
