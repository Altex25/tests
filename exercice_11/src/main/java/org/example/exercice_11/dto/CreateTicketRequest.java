package org.example.exercice_11.dto;

import org.example.exercice_11.model.Priority;

public class CreateTicketRequest {
    private String title;
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
