package org.example.test;

public class PriceCalculator {
    double calculateTotalPrice(double unitPrice, int quantity) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        return unitPrice * quantity;
    }

    double applyDiscount(double price, double discountRate) {
        if (discountRate < 0) {
            throw new IllegalArgumentException("Discount rate cannot be negative.");
        }
        return price * (1 - discountRate);
    }

    double calculateVat(double price, double vatRate) {
        if(vatRate < 0) {
            throw new IllegalArgumentException("VAT rate cannot be negative.");
        }
        return price * vatRate;
    }

    double calculatePriceWithVat(double price, double vatRate) {
        return price + calculateVat(price, vatRate);
    }
}
