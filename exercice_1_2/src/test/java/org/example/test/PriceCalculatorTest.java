package org.example.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceCalculatorTest {

    @Test
    void assertCalculateTotalPriceIs30() {
        PriceCalculator calculator = new PriceCalculator();

        double totalPrice = calculator.calculateTotalPrice(10.0, 3);

        assertEquals(30.0, totalPrice);
    }

    @Test
    void shouldThrowExceptionWhenUnitPriceIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateTotalPrice(-10.0, 3);
        });

        assertEquals(
            "Unit price cannot be negative.",
            exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateTotalPrice(10.0, -3);
        });

        assertEquals(
            "Quantity cannot be negative.",
            exception.getMessage()
        );
    }

    @Test
    void assertApply20PercentDiscount() {
        PriceCalculator calculator = new PriceCalculator();

        double discountedPrice = calculator.applyDiscount(100.0, 0.20);

        assertEquals(80.0, discountedPrice);
    }

    @Test
    void shouldThrowExceptionWhenDiscountRateIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.applyDiscount(100.0, -0.20);
        });

        assertEquals(
            "Discount rate cannot be negative.",
            exception.getMessage()
        );
    }

    @Test
    void assertCalculateVatIs20() {
        PriceCalculator calculator = new PriceCalculator();

        double vat = calculator.calculateVat(100.0, 0.20);

        assertEquals(20.0, vat);
    }

    @Test
    void shouldThrowExceptionWhenVatRateIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateVat(100.0, -0.20);
        });

        assertEquals(
            "VAT rate cannot be negative.",
            exception.getMessage()
        );
    }

    @Test
    void assertCalculatePriceWithVatIs120() {
        PriceCalculator calculator = new PriceCalculator();
        double priceWithVat = calculator.calculatePriceWithVat(100.0, 0.20);
        assertEquals(120.0, priceWithVat);
    }

}
