package org.example.exercice_11.repository;

import org.example.exercice_11.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {
    public Ticket save(Ticket ticket) {
        throw new UnsupportedOperationException("TODO: implement TicketRepository.save");
    }

    public Optional<Ticket> findById(Long id) {
        throw new UnsupportedOperationException("TODO: implement TicketRepository.findById");
    }

    public List<Ticket> findAll() {
        throw new UnsupportedOperationException("TODO: implement TicketRepository.findAll");
    }
}
