package org.example.exercice_11.service;

import org.example.exercice_11.dto.CreateTicketRequest;
import org.example.exercice_11.exception.InvalidStatusTransitionException;
import org.example.exercice_11.exception.TicketNotFoundException;
import org.example.exercice_11.model.Priority;
import org.example.exercice_11.model.Status;
import org.example.exercice_11.model.Ticket;
import org.example.exercice_11.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldCreateTicketWithProvidedTitleAndPriority() {
        CreateTicketRequest request = new CreateTicketRequest("Printer is broken", Priority.HIGH);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket toSave = invocation.getArgument(0);
            toSave.setId(1L);
            return toSave;
        });

        Ticket created = ticketService.create(request);

        assertEquals(1L, created.getId());
        assertEquals("Printer is broken", created.getTitle());
        assertEquals(Priority.HIGH, created.getPriority());
    }

    @Test
    void shouldCreateTicketWithOpenStatusByDefault() {
        CreateTicketRequest request = new CreateTicketRequest("New incident", Priority.LOW);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket created = ticketService.create(request);

        assertEquals(Status.OPEN, created.getStatus());
    }

    @Test
    void shouldReturnTicketWhenItExists() {
        Ticket existing = new Ticket(42L, "Login fails", Priority.MEDIUM, Status.OPEN);
        when(ticketRepository.findById(42L)).thenReturn(Optional.of(existing));

        Ticket found = ticketService.getById(42L);

        assertEquals(42L, found.getId());
        assertEquals("Login fails", found.getTitle());
    }

    @Test
    void shouldThrowWhenTicketDoesNotExist() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getById(999L));
    }

    @Test
    void shouldReturnAllTickets() {
        when(ticketRepository.findAll()).thenReturn(List.of(
                new Ticket(1L, "A", Priority.LOW, Status.OPEN),
                new Ticket(2L, "B", Priority.HIGH, Status.RESOLVED)));

        List<Ticket> tickets = ticketService.getAll();

        assertEquals(2, tickets.size());
    }

    @Test
    void shouldAllowTransitionFromOpenToInProgress() {
        Ticket ticket = new Ticket(1L, "Issue", Priority.LOW, Status.OPEN);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updated = ticketService.changeStatus(1L, Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, updated.getStatus());
    }

    @Test
    void shouldAllowTransitionFromOpenToResolved() {
        Ticket ticket = new Ticket(1L, "Issue", Priority.LOW, Status.OPEN);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updated = ticketService.changeStatus(1L, Status.RESOLVED);

        assertEquals(Status.RESOLVED, updated.getStatus());
    }

    @Test
    void shouldAllowTransitionFromInProgressToResolved() {
        Ticket ticket = new Ticket(1L, "Issue", Priority.LOW, Status.IN_PROGRESS);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updated = ticketService.changeStatus(1L, Status.RESOLVED);

        assertEquals(Status.RESOLVED, updated.getStatus());
    }

    @Test
    void shouldRejectTransitionFromResolved() {
        Ticket ticket = new Ticket(1L, "Issue", Priority.LOW, Status.RESOLVED);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(InvalidStatusTransitionException.class,
                () -> ticketService.changeStatus(1L, Status.IN_PROGRESS));
    }

    @Test
    void shouldRejectTransitionFromInProgressToOpen() {
        Ticket ticket = new Ticket(1L, "Issue", Priority.LOW, Status.IN_PROGRESS);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(InvalidStatusTransitionException.class,
                () -> ticketService.changeStatus(1L, Status.OPEN));
    }
}
