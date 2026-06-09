Feature: Order acceptance and discount per customer profile

  As the order module
  I want to accept orders and apply the right discount
  So that each customer profile is billed correctly

  Background:
    Given the catalogue contains the product "REF-001" named "Mechanical keyboard" priced at 50.00 euros with a stock of 10

  Scenario: Assert order is accepted for a STANDARD customer
    When the customer "alice@example.com" with profile "STANDARD" orders 2 units of product "REF-001"
    Then the order is accepted
    And the receipt shows the reference "REF-001"
    And the receipt shows a quantity of 2
    And the receipt shows a total amount of 100.00 euros
    And the receipt contains a confirmation message

  Scenario: Assert order is accepted for a PREMIUM customer
    When the customer "bob@example.com" with profile "PREMIUM" orders 2 units of product "REF-001"
    Then the order is accepted
    And the receipt shows the reference "REF-001"
    And the receipt shows a quantity of 2
    And the receipt shows a total amount of 90.00 euros
    And the receipt contains a confirmation message

  Scenario: Assert order is accepted for a VIP customer
    When the customer "carol@example.com" with profile "VIP" orders 2 units of product "REF-001"
    Then the order is accepted
    And the receipt shows the reference "REF-001"
    And the receipt shows a quantity of 2
    And the receipt shows a total amount of 80.00 euros
    And the receipt contains a confirmation message
