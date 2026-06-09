Feature: Order refused when the product is unknown

  As the order module
  I want to refuse orders for products that do not exist
  So that the catalogue stays consistent

  Scenario: Assert order is refused when the product is unknown
    Given the catalogue contains no product with reference "REF-404"
    When the customer "dave@example.com" with profile "STANDARD" orders 1 unit of product "REF-404"
    Then the order is refused because the product is unknown
