package org.example.exercice_12.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String message) {
        super(message);
    }
}
