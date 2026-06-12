package org.example.exercice_11.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.exercice_11.model.Priority;

public class CreateTicketRequest {
    @NotBlank
    @Size(min = 3, message = "The title must contain at least 3 characters")
    private String title;

    @NotNull
    private Priority priority;

    public CreateTicketRequest() {
    }

    public CreateTicketRequest(String title, Priority priority) {
        this.title = title;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
