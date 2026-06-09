Feature: Order refused when the stock is insufficient

  As the order module
  I want to refuse orders whose quantity exceeds the available stock
  So that I never sell more than I own

  Scenario: Assert order is refused when the stock is insufficient
    Given the catalogue contains the product "REF-001" named "Mechanical keyboard" priced at 50.00 euros with a stock of 3
    When the customer "erin@example.com" with profile "STANDARD" orders 5 units of product "REF-001"
    Then the order is refused because the stock is insufficient
