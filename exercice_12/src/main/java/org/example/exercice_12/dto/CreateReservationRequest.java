package org.example.exercice_12.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateReservationRequest {

    @NotNull
    private Long roomId;

    @NotBlank
    private String personName;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    public CreateReservationRequest() {
    }

    public CreateReservationRequest(Long roomId, String personName, LocalDateTime start, LocalDateTime end) {
        this.roomId = roomId;
        this.personName = personName;
        this.start = start;
        this.end = end;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
