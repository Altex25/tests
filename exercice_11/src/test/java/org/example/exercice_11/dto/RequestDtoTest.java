package org.example.exercice_11.dto;

import org.example.exercice_11.model.Priority;
import org.example.exercice_11.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestDtoTest {
    @Test
    void createTicketRequestShouldExposeItsFields() {
        CreateTicketRequest request = new CreateTicketRequest("Title", Priority.MEDIUM);
        assertEquals("Title", request.getTitle());
        assertEquals(Priority.MEDIUM, request.getPriority());

        request.setTitle("Updated");
        request.setPriority(Priority.HIGH);
        assertEquals("Updated", request.getTitle());
        assertEquals(Priority.HIGH, request.getPriority());

        assertNull(new CreateTicketRequest().getTitle());
    }

    @Test
    void updateStatusRequestShouldExposeItsField() {
        UpdateStatusRequest request = new UpdateStatusRequest(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, request.getStatus());

        request.setStatus(Status.RESOLVED);
        assertEquals(Status.RESOLVED, request.getStatus());

        assertNull(new UpdateStatusRequest().getStatus());
    }
}
