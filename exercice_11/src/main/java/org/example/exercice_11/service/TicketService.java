package org.example.exercice_11.service;

import org.example.exercice_11.dto.CreateTicketRequest;
import org.example.exercice_11.exception.InvalidStatusTransitionException;
import org.example.exercice_11.exception.TicketNotFoundException;
import org.example.exercice_11.model.Status;
import org.example.exercice_11.model.Ticket;
import org.example.exercice_11.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TicketService {
    private static final Map<Status, Set<Status>> ALLOWED_TRANSITIONS = new EnumMap<>(Status.class);

    static {
        ALLOWED_TRANSITIONS.put(Status.OPEN, EnumSet.of(Status.IN_PROGRESS, Status.RESOLVED));
        ALLOWED_TRANSITIONS.put(Status.IN_PROGRESS, EnumSet.of(Status.RESOLVED));
        ALLOWED_TRANSITIONS.put(Status.RESOLVED, EnumSet.noneOf(Status.class));
    }

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket create(CreateTicketRequest request) {
        Ticket ticket = new Ticket(null, request.getTitle(), request.getPriority(), Status.OPEN);
        return ticketRepository.save(ticket);
    }

    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket " + id + " not found"));
    }

    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    public Ticket changeStatus(Long id, Status newStatus) {
        Ticket ticket = getById(id);
        Set<Status> allowed = ALLOWED_TRANSITIONS.getOrDefault(ticket.getStatus(), EnumSet.noneOf(Status.class));
        if (!allowed.contains(newStatus)) {
            throw new InvalidStatusTransitionException(
                    "Cannot change status from " + ticket.getStatus() + " to " + newStatus);
        }
        ticket.setStatus(newStatus);
        return ticketRepository.save(ticket);
    }
}
