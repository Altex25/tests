package org.example.exercice_11.service;

import org.example.exercice_11.dto.CreateTicketRequest;
import org.example.exercice_11.model.Status;
import org.example.exercice_11.model.Ticket;
import org.example.exercice_11.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket create(CreateTicketRequest request) {
        throw new UnsupportedOperationException("TODO: implement TicketService.create");
    }

    public Ticket getById(Long id) {
        throw new UnsupportedOperationException("TODO: implement TicketService.getById");
    }

    public List<Ticket> getAll() {
        throw new UnsupportedOperationException("TODO: implement TicketService.getAll");
    }

    public Ticket changeStatus(Long id, Status newStatus) {
        throw new UnsupportedOperationException("TODO: implement TicketService.changeStatus");
    }
}
