package com.example;

import java.time.LocalDateTime;

public class ReservationConfirmation {

    private final String roomCode;
    private final int participantCount;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String confirmationMessage;

    public ReservationConfirmation(String roomCode, int participantCount,
                                   LocalDateTime startDate, LocalDateTime endDate,
                                   String confirmationMessage) {
        this.roomCode = roomCode;
        this.participantCount = participantCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.confirmationMessage = confirmationMessage;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }
}
