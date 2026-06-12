package org.example.exercice_11.controller;

import org.example.exercice_11.exception.InvalidStatusTransitionException;
import org.example.exercice_11.exception.TicketNotFoundException;
import org.example.exercice_11.model.Priority;
import org.example.exercice_11.model.Status;
import org.example.exercice_11.model.Ticket;
import org.example.exercice_11.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    @Test
    void shouldReturn201AndTicketJsonWhenCreatingValidTicket() throws Exception {
        when(ticketService.create(any())).thenReturn(new Ticket(1L, "Printer down", Priority.HIGH, Status.OPEN));

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Printer down\",\"priority\":\"HIGH\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Printer down"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void shouldReturn400WhenCreatingTicketWithInvalidTitle() throws Exception {
        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"ab\",\"priority\":\"HIGH\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200AndTicketJsonWhenTicketExists() throws Exception {
        when(ticketService.getById(1L)).thenReturn(new Ticket(1L, "Login issue", Priority.MEDIUM, Status.OPEN));

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Login issue"));
    }

    @Test
    void shouldReturn404WhenTicketDoesNotExist() throws Exception {
        when(ticketService.getById(999L)).thenThrow(new TicketNotFoundException("Ticket 999 not found"));

        mockMvc.perform(get("/api/tickets/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200AndListWhenListingTickets() throws Exception {
        when(ticketService.getAll()).thenReturn(List.of(
                new Ticket(1L, "A", Priority.LOW, Status.OPEN)));

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn200WhenStatusChangeIsAllowed() throws Exception {
        when(ticketService.changeStatus(1L, Status.RESOLVED))
                .thenReturn(new Ticket(1L, "A", Priority.LOW, Status.RESOLVED));

        mockMvc.perform(patch("/api/tickets/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"RESOLVED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RESOLVED"));
    }

    @Test
    void shouldReturn409WhenStatusChangeIsForbidden() throws Exception {
        when(ticketService.changeStatus(eq(1L), any(Status.class)))
                .thenThrow(new InvalidStatusTransitionException("Ticket is already resolved"));

        mockMvc.perform(patch("/api/tickets/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isConflict());
    }
}
