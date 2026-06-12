package com.example.service;

import com.example.exception.OrderNotFoundException;
import com.example.model.AddToOrderResult;
import com.example.model.Order;
import com.example.model.OrderConfirmation;
import com.example.repository.OrderRepository;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public AddToOrderResult addProduct(String orderId, String productId) {
        Order order = requireOrder(orderId);
        order.addProduct(productId);
        orderRepository.save(order);
        return new AddToOrderResult("Product " + productId + " added to order " + orderId);
    }

    public void removeProduct(String orderId, String productId) {
        Order order = requireOrder(orderId);
        order.removeProduct(productId);
        orderRepository.save(order);
    }

    public OrderConfirmation validate(String orderId) {
        Order order = requireOrder(orderId);
        return new OrderConfirmation(order.getId(), "Order " + order.getId() + " confirmed");
    }

    private Order requireOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Unknown order: " + orderId));
    }
}
