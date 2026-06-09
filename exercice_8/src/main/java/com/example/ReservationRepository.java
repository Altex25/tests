package com.example;

import java.util.List;

public interface ReservationRepository {

    /**
     * @param roomCode the room code
     * @return the reservations already registered for the given room
     */
    List<Reservation> findByRoomCode(String roomCode);
}
