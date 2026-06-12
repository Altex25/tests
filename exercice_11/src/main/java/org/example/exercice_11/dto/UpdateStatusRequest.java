package org.example.exercice_11.dto;

import org.example.exercice_11.model.Status;

public class UpdateStatusRequest {
    private Status status;

    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
