package org.example.exercice_13.bdd;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.exercice_13.repository.InMemoryAccountRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountSteps {

    private final MockMvc mockMvc;
    private final InMemoryAccountRepository repository;

    private ResultActions lastAction;

    public AccountSteps(MockMvc mockMvc, InMemoryAccountRepository repository) {
        this.mockMvc = mockMvc;
        this.repository = repository;
    }

    @Before
    public void resetState() {
        repository.clear();
    }

    @Given("an account {string} exists for {string}")
    public void anAccountExistsFor(String number, String owner) throws Exception {
        createAccount(number, owner);
    }

    @When("an account {string} is created for {string}")
    public void anAccountIsCreatedFor(String number, String owner) throws Exception {
        lastAction = createAccount(number, owner);
    }

    @When("{int} is deposited on account {string}")
    public void isDepositedOnAccount(int amount, String number) throws Exception {
        lastAction = mockMvc.perform(post("/accounts/" + number + "/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(amountJson(amount)));
    }

    @When("{int} is withdrawn from account {string}")
    public void isWithdrawnFromAccount(int amount, String number) throws Exception {
        lastAction = mockMvc.perform(post("/accounts/" + number + "/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(amountJson(amount)));
    }

    @When("{int} is transferred from account {string} to account {string}")
    public void isTransferredFromAccountToAccount(int amount, String from, String to) throws Exception {
        lastAction = mockMvc.perform(post("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"amount\":" + amount + "}"));
    }

    @Then("the account {string} exists with balance {int}")
    public void assert_account_exists_with_balance(String number, int balance) throws Exception {
        assert_account_has_balance(number, balance);
    }

    @Then("the account {string} has balance {int}")
    public void assert_account_has_balance(String number, int balance) throws Exception {
        String response = mockMvc.perform(get("/accounts/" + number))
                .andReturn()
                .getResponse()
                .getContentAsString();
        double actual = ((Number) JsonPath.read(response, "$.balance")).doubleValue();
        assertEquals(balance, actual);
    }

    @Then("the response status code is {int}")
    public void assert_response_status_code_is(int code) throws Exception {
        lastAction.andExpect(status().is(code));
    }

    @Then("the operation is refused with status code {int}")
    public void assert_operation_is_refused_with_status_code(int code) throws Exception {
        lastAction.andExpect(status().is(code));
    }

    private ResultActions createAccount(String number, String owner) throws Exception {
        return mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\":\"" + number + "\",\"owner\":\"" + owner + "\"}"));
    }

    private String amountJson(int amount) {
        return "{\"amount\":" + amount + "}";
    }
}
