package org.example.exercice_12.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateRoomRequest {

    @NotBlank
    private String name;

    @Min(1)
    private int capacity;

    public CreateRoomRequest() {
    }

    public CreateRoomRequest(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
