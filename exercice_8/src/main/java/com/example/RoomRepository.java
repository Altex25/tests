package com.example;

import java.util.Optional;

public interface RoomRepository {

    /**
     * @param code the room code to look up
     * @return the room if it exists, otherwise {@link Optional#empty()}
     */
    Optional<Room> findByCode(String code);
}
