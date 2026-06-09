package com.example.steps;

import com.example.exception.ProduitInconnuException;
import com.example.exception.StockInsuffisantException;
import com.example.model.Commande;
import com.example.model.ProfilClient;
import com.example.model.Produit;
import com.example.model.RecuCommande;
import com.example.repository.ProduitRepository;
import com.example.service.CommandeService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandeSteps {

    private ProduitRepository produitRepository;
    private CommandeService commandeService;

    private RecuCommande receipt;
    private RuntimeException error;

    @Before
    public void initialize() {
        // A fresh mock and a fresh service before each scenario.
        produitRepository = mock(ProduitRepository.class);
        commandeService = new CommandeService(produitRepository);
        receipt = null;
        error = null;
    }

    @Given("the catalogue contains the product {string} named {string} priced at {double} euros with a stock of {int}")
    public void assert_catalogue_contains_product(String reference, String name, double price, int stock) {
        Produit produit = new Produit(reference, name, price, stock);
        when(produitRepository.findByReference(reference)).thenReturn(Optional.of(produit));
    }

    @Given("the catalogue contains no product with reference {string}")
    public void assert_catalogue_contains_no_product(String reference) {
        when(produitRepository.findByReference(reference)).thenReturn(Optional.empty());
    }

    @When("the customer {string} with profile {string} orders {int} unit(s) of product {string}")
    public void assert_customer_orders(String email, String profile, int quantity, String reference) {
        Commande commande = new Commande(email, reference, quantity, ProfilClient.valueOf(profile));
        try {
            receipt = commandeService.placeOrder(commande);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @Then("the order is accepted")
    public void assert_order_is_accepted() {
        assertNull(error, "The order should have been accepted");
        assertNotNull(receipt, "A receipt should have been generated");
    }

    @Then("the receipt shows the reference {string}")
    public void assert_receipt_shows_the_reference(String reference) {
        assertEquals(reference, receipt.getProductReference());
    }

    @Then("the receipt shows a quantity of {int}")
    public void assert_receipt_shows_a_quantity(int quantity) {
        assertEquals(quantity, receipt.getQuantity());
    }

    @Then("the receipt shows a total amount of {double} euros")
    public void assert_receipt_shows_a_total_amount(double amount) {
        assertEquals(amount, receipt.getTotalAmount(), 0.001);
    }

    @Then("the receipt contains a confirmation message")
    public void assert_receipt_contains_a_confirmation_message() {
        assertNotNull(receipt.getConfirmationMessage(), "The confirmation message is missing");
        assertFalse(receipt.getConfirmationMessage().isBlank(), "The confirmation message is empty");
    }

    @Then("the order is refused because the product is unknown")
    public void assert_order_is_refused_for_unknown_product() {
        assertNotNull(error, "The order should have been refused");
        assertInstanceOf(ProduitInconnuException.class, error,
                "The expected error is ProduitInconnuException");
        assertNull(receipt, "No receipt must be generated for a refused order");
    }

    @Then("the order is refused because the stock is insufficient")
    public void assert_order_is_refused_for_insufficient_stock() {
        assertNotNull(error, "The order should have been refused");
        assertInstanceOf(StockInsuffisantException.class, error,
                "The expected error is StockInsuffisantException");
        assertNull(receipt, "No receipt must be generated for a refused order");
    }
}
