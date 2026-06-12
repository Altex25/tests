package org.example.exercice_11.bdd;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketSteps {
    private final MockMvc mockMvc;

    private String title;
    private String priority;
    private Long currentTicketId;
    private ResultActions lastAction;

    public TicketSteps(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Given("a valid ticket with title {string} and priority {string}")
    public void aValidTicketWithTitleAndPriority(String title, String priority) {
        this.title = title;
        this.priority = priority;
    }

    @Given("an existing ticket with title {string} and priority {string}")
    public void anExistingTicketWithTitleAndPriority(String title, String priority) throws Exception {
        currentTicketId = createTicket(title, priority);
    }

    @Given("a resolved ticket with title {string} and priority {string}")
    public void aResolvedTicketWithTitleAndPriority(String title, String priority) throws Exception {
        currentTicketId = createTicket(title, priority);
        changeStatus(currentTicketId, "RESOLVED");
    }

    @When("the ticket is created")
    public void theTicketIsCreated() throws Exception {
        lastAction = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson(title, priority)));
    }

    @When("its status is changed to {string}")
    public void itsStatusIsChangedTo(String status) throws Exception {
        lastAction = changeStatus(currentTicketId, status);
    }

    @When("ticket number {int} is requested")
    public void ticketNumberIsRequested(int id) throws Exception {
        lastAction = mockMvc.perform(get("/api/tickets/" + id));
    }

    @Then("the ticket is saved with status {string}")
    public void theTicketIsSavedWithStatus(String status) throws Exception {
        lastAction.andExpect(jsonPath("$.status").value(status));
    }

    @Then("the ticket status becomes {string}")
    public void theTicketStatusBecomes(String status) throws Exception {
        lastAction.andExpect(jsonPath("$.status").value(status));
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int code) throws Exception {
        lastAction.andExpect(status().is(code));
    }

    @Then("the change is refused with status code {int}")
    public void theChangeIsRefusedWithStatusCode(int code) throws Exception {
        lastAction.andExpect(status().is(code));
    }

    private long createTicket(String title, String priority) throws Exception {
        String response = mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJson(title, priority)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return ((Number) JsonPath.read(response, "$.id")).longValue();
    }

    private ResultActions changeStatus(Long ticketId, String status) throws Exception {
        return mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"" + status + "\"}"));
    }

    private String ticketJson(String title, String priority) {
        return "{\"title\":\"" + title + "\",\"priority\":\"" + priority + "\"}";
    }
}
