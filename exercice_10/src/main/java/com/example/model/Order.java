package com.example.model;

import com.example.exception.ProductNotInOrderException;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order {

    private final String id;
    private final Map<String, Integer> quantitiesByProduct = new LinkedHashMap<>();

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addProduct(String productId) {
        quantitiesByProduct.merge(productId, 1, Integer::sum);
    }

    public void removeProduct(String productId) {
        Integer quantity = quantitiesByProduct.get(productId);
        if (quantity == null) {
            throw new ProductNotInOrderException(
                    "Product " + productId + " is not in order " + id);
        }
        if (quantity > 1) {
            quantitiesByProduct.put(productId, quantity - 1);
        } else {
            quantitiesByProduct.remove(productId);
        }
    }

    public int getQuantity(String productId) {
        return quantitiesByProduct.getOrDefault(productId, 0);
    }

    public boolean contains(String productId) {
        return quantitiesByProduct.containsKey(productId);
    }
}
