package org.example.exercice_11.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketTest {
    @Test
    void shouldExposeValuesGivenToTheAllArgsConstructor() {
        Ticket ticket = new Ticket(1L, "Printer down", Priority.HIGH, Status.OPEN);

        assertEquals(1L, ticket.getId());
        assertEquals("Printer down", ticket.getTitle());
        assertEquals(Priority.HIGH, ticket.getPriority());
        assertEquals(Status.OPEN, ticket.getStatus());
    }

    @Test
    void shouldUpdateValuesThroughSetters() {
        Ticket ticket = new Ticket();

        ticket.setId(2L);
        ticket.setTitle("Network outage");
        ticket.setPriority(Priority.LOW);
        ticket.setStatus(Status.RESOLVED);

        assertEquals(2L, ticket.getId());
        assertEquals("Network outage", ticket.getTitle());
        assertEquals(Priority.LOW, ticket.getPriority());
        assertEquals(Status.RESOLVED, ticket.getStatus());
    }
}
