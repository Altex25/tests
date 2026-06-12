package org.example.exercice_11.controller;

import jakarta.validation.Valid;
import org.example.exercice_11.dto.CreateTicketRequest;
import org.example.exercice_11.dto.UpdateStatusRequest;
import org.example.exercice_11.model.Ticket;
import org.example.exercice_11.service.TicketService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Ticket> create(@Valid @RequestBody CreateTicketRequest request) {
        Ticket created = ticketService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ticket> changeStatus(@PathVariable Long id,
                                               @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(ticketService.changeStatus(id, request.getStatus()));
    }
}
