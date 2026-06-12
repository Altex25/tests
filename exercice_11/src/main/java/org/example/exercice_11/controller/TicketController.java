package org.example.exercice_11.controller;

import org.example.exercice_11.dto.CreateTicketRequest;
import org.example.exercice_11.dto.UpdateStatusRequest;
import org.example.exercice_11.model.Ticket;
import org.example.exercice_11.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody CreateTicketRequest request) {
        throw new UnsupportedOperationException("TODO: implement TicketController.create");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        throw new UnsupportedOperationException("TODO: implement TicketController.getById");
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        throw new UnsupportedOperationException("TODO: implement TicketController.getAll");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ticket> changeStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        throw new UnsupportedOperationException("TODO: implement TicketController.changeStatus");
    }
}
