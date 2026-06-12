package com.example.steps;

import com.example.exception.OrderNotFoundException;
import com.example.exception.ProductNotInOrderException;
import com.example.model.AddToOrderResult;
import com.example.model.Order;
import com.example.model.OrderConfirmation;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

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

public class OrderSteps {

    private OrderRepository orderRepository;
    private OrderService orderService;

    private Order order;
    private AddToOrderResult addResult;
    private OrderConfirmation confirmation;
    private RuntimeException error;

    @Before
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
        order = null;
        addResult = null;
        confirmation = null;
        error = null;
    }

    @Given("an existing order {string}")
    public void anExistingOrder(String orderId) {
        order = new Order(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    }

    @Given("an existing order {string} already containing {int} unit(s) of product {string}")
    public void anExistingOrderContaining(String orderId, int quantity, String productId) {
        order = new Order(orderId);
        for (int i = 0; i < quantity; i++) {
            order.addProduct(productId);
        }
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    }

    @Given("no order exists with id {string}")
    public void noOrderExistsWithId(String orderId) {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
    }

    @When("the user adds the product {string} to the order {string}")
    public void addsProduct(String productId, String orderId) {
        try {
            addResult = orderService.addProduct(orderId, productId);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @When("the user removes the product {string} from the order {string}")
    public void removesProduct(String productId, String orderId) {
        try {
            orderService.removeProduct(orderId, productId);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @When("the user validates the order {string}")
    public void validatesOrder(String orderId) {
        try {
            confirmation = orderService.validate(orderId);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @Then("the product addition is confirmed")
    public void theProductAdditionIsConfirmed() {
        assertNull(error, "The addition should have succeeded");
        assertNotNull(addResult, "An addition result should have been returned");
        assertFalse(addResult.getMessage().isBlank(), "The addition message should not be blank");
    }

    @Then("the order {string} contains {int} unit(s) of product {string}")
    public void theOrderContainsUnitsOfProduct(String orderId, int quantity, String productId) {
        assertEquals(quantity, order.getQuantity(productId));
    }

    @Then("the order {string} no longer contains the product {string}")
    public void theOrderNoLongerContainsTheProduct(String orderId, String productId) {
        assertFalse(order.contains(productId), "The product should have been removed");
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed() {
        assertNull(error, "The validation should have succeeded");
        assertNotNull(confirmation, "An order confirmation should have been returned");
        assertEquals(order.getId(), confirmation.getOrderId());
        assertFalse(confirmation.getMessage().isBlank(), "The confirmation message should not be blank");
    }

    @Then("the order operation is rejected because the order does not exist")
    public void rejectedBecauseOrderDoesNotExist() {
        assertNotNull(error, "The operation should have been rejected");
        assertInstanceOf(OrderNotFoundException.class, error);
    }

    @Then("the order operation is rejected because the product is not in the order")
    public void rejectedBecauseProductNotInOrder() {
        assertNotNull(error, "The operation should have been rejected");
        assertInstanceOf(ProductNotInOrderException.class, error);
    }
}
